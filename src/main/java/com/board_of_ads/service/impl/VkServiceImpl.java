package com.board_of_ads.service.impl;

import com.board_of_ads.models.Image;
import com.board_of_ads.models.Role;
import com.board_of_ads.models.User;
import com.board_of_ads.service.interfaces.RoleService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.service.interfaces.VkService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix="spring.security.auth-vk")
public class VkServiceImpl implements VkService {

    private final UserService userService;
    private final RoleService roleService;


    private String clientId;
    private String clientSecret;
    private String responseType;
    private String redirectURL;
    private String authURL;
    private String tokenURL;
    private String usersGetURL;
    private String scope;
    private String display;
    private String fields;
    private String version;


    public void auth(String code) {
        String response = getAuthResponseURL(code);
        Map<String, String> userData = getUserData(response);
        userData = getUserData(userData);
        User user = init(userData);
        login(user);
    }
    /**
     * Метод для кнопки авторизации
     * @return ссылку для авторизации при переходе по которой, получим ссылку с
     *          аргументом code необходимым для метода getAuthResponse(String code).
     */
    @Override
    public String getAuthURL() {
        return authURL + "?"
                + "client_id=" + clientId
                + "&redirect_uri=" + redirectURL
                + "&scope=" + scope
                + "&display=" + display
                + "&response_type=" + responseType;
    }

    /**
     * @param code получаем из ссылки возвращаемой методом getAuthURL()
     * @return ссылку, для получения access_token, и данные пользователя VK
     */
    @Override
    public String getAuthResponseURL(String code) {
        return tokenURL + "?"
                + "client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&redirect_uri=" + redirectURL
                + "&code=" + code;
    }

    /**
     * @param authResponseUrl возвращается методом getAuthResponse(String code)
     * @return Map с access_token, user_id, и email пользователя.
     */
    @Override
    public Map<String, String> getUserData(String authResponseUrl) {
        Map<String, String> userData = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(authResponseUrl, String.class);
        Object obj = null;
        try {
            obj = new JSONParser().parse(responseEntity.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        userData.put("access_token", (String) jsonObject.get("access_token"));
        userData.put("user_id", jsonObject.get("user_id").toString());
        userData.put("email", (String) jsonObject.get("email"));
        return userData;
    }

    /**
     * @param userData возвращается методом getUserData(String authResponseUrl)
     * @return Map с именем, фамилией и ссылкой на аватар пользователя(100x100px)
     */
    @Override
    public Map<String, String> getUserData(Map<String, String> userData) {
        String request = usersGetURL + "?"
                + "users_ids=" + userData.get("user_id")
                + "&fields=" + fields
                + "&v=" + version
                + "&access_token=" + userData.get("access_token");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(request, String.class);
        JSONObject jo = null;
        try {
            jo = (JSONObject) JSONValue.parseWithException(responseEntity.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = (JSONArray) jo.get("response");
        JSONObject dataArray = (JSONObject) jsonArray.get(0);
        userData.put("first_name", (String) dataArray.get("first_name"));
        userData.put("last_name", (String) dataArray.get("last_name"));
        userData.put("avatar_link", (String) dataArray.get("photo_100"));
        return userData;
    }

    /**
     * Метод для получения сессии пользователя
     */
    @Override
    public void login(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Метод инициализации пользователя.
     * Если такой пользователь есть в базе данных, то он вернет его.
     * Если пользователя не существует, то он его создаст, добавит в БД и вернет.
     *
     * @param userData возвращается методом getUserData
     */
    @Override
    public User init(Map<String, String> userData) {
        User user = userService.getUserByEmail(userData.get("email"));
        if (user != null) {
            return user;
        }
        user = new User();
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("USER"));
        user.setRoles(roles);
        user.setEnable(true);
        user.setDataRegistration(LocalDateTime.now());
        user.setEmail(userData.get("email"));
        user.setFirsName(userData.get("first_name"));
        user.setLastName(userData.get("last_name"));
        user.setAvatar(new Image(null, userData.get("avatar_link")));
        user.setPassword(userData.get("email")); //todo create set password page (and phone)
        userService.saveUser(user);
        return user;
    }
}

