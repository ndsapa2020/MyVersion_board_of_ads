package com.board_of_ads.service.impl;

import com.board_of_ads.models.Image;
import com.board_of_ads.models.User;
import com.board_of_ads.service.interfaces.MailService;
import com.board_of_ads.service.interfaces.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.security.auth-mail")

public class MailServiceImpl implements MailService {

    private final UserService userService;

    private String clientId;
    private String clientSecret;
    private String responseType;
    private String redirectURL;
    private String auth;
    private String tokenUrl;
    private String userInfoUrl;

    @Override
    public void auth(String code) {
        String requestBody = getAuthResponseURL(code);
        String token = getToken(requestBody);
        String getsig = getSign(token);
        String sig = md5Apache(getsig);
        Map<String, String> userData = getUserData(token, sig);
        User user = init(userData);
        login(user);
    }


    /**
     * Метод который сгенерирует ссылку для запроса на авторизацию в mail.ru
     *
     * @return ссылку для авторизации с аргументом code, который нужен для метода getAuthResponseURL(String code)
     */
    @Override
    public String getAuthURL() {
        return auth + "?"
                + "client_id=" + clientId
                + "&response_type=" + responseType
                + "&redirect_uri=" + redirectURL;
    }

    /**
     * Метод, который генерирует ссылку для того, чтобы получить параментр: access_token
     *
     * @param code который получили в методе getAuthURL()
     * @return ссылку для POST запроса, в котором можно будет получить access_token
     */
    @Override
    public String getAuthResponseURL(String code) {
        return "client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&grant_type=authorization_code"
                + "&code=" + code
                + "&redirect_uri=" + redirectURL;
    }

    /**
     * Метод, который создает POST запрос, отправляет ссылку сгенерированную в методе getAuthResponseURL,
     * получает JSON объект, в котором, из всей информции получает только access_token
     *
     * @param body ссылка сгенерированная в методе getAuthResponseURL
     * @return access_token, который необходим для запросов к api mail.ru и создания подписи (sign)
     */
    @Override
    public String getToken(String body) {
        HttpEntity<String> httpEntity = new HttpEntity<>(body);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entity = restTemplate.exchange(tokenUrl, HttpMethod.POST, httpEntity, String.class);
        Object obj = null;
        try {
            obj = new JSONParser().parse(entity.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        String token = (String) jsonObject.get("access_token");
        return token;
    }

    /**
     * Метод, создающий String, который будет в последствии защифрован и будет являться подписью
     *
     * @param token из метода getToken(String body)
     * @return String для того, чтобы сделать подпись для запросов к api
     */
    public String getSign(String token) {
        return "app_id=" + clientId
                + "method=users.getInfosecure=1session_key=" + token
                + clientSecret;
    }

    /**
     * Метод, который делает из ранее сгенерированной строки метода getSign(String token)
     * буквенно-числовой шифр (метод шифрования md5)
     *
     * @param st Строку из метода getSign(String token)
     * @return String буквенно-числовой шифр: sign
     */
    public static String md5Apache(String st) {
        String md5Hex = DigestUtils.md5Hex(st);
        return md5Hex;
    }

    /**
     * Метод, который создает ссылку, отправляет GET запрос со всеми ранее полученными данными
     * и получает JSON объект, из которого в созданный Map помещает параметры: first_name, last_name, email, pic
     *
     * @param token из метода getToken(String body)
     * @param sig   из метода md5Apache(String st)
     * @return
     */
    @Override
    public Map<String, String> getUserData(String token, String sig) {

        String request = userInfoUrl + "?"
                + "method=users.getInfo"
                + "&app_id=" + clientId
                + "&session_key=" + token
                + "&secure=1&sig=" + sig;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(request, String.class);
        Object obj = null;
        try {
            obj = new JSONParser().parse(responseEntity.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = (JSONArray) obj;
        JSONObject jsonObject = null;
        jsonObject = (JSONObject) jsonArray.get(0);
        Map<String, String> userData = new HashMap<>();
        userData.put("first_name", (String) jsonObject.get("first_name"));
        userData.put("last_name", (String) jsonObject.get("last_name"));
        userData.put("email", (String) jsonObject.get("email"));
        userData.put("pic", (String) jsonObject.get("pic"));
        return userData;
    }

    /**
     * Здесь происходит создание юзера и создание записи в БД
     *
     * @param userData из метода getUserData(String token, String sig)
     * @return user
     */
    @Override
    public User init(Map<String, String> userData) {
        User user = userService.getUserByEmail(userData.get("email"));
        if (user != null) {
            return user;
        }
        user = new User();
        user.setEnable(true);
        user.setDataRegistration(LocalDateTime.now());
        user.setFirsName(userData.get("first_name"));
        user.setLastName(userData.get("last_name"));
        user.setAvatar(new Image(null, userData.get("pic")));
        user.setPassword(userData.get("email"));
        userService.saveUser(user);
        return user;
    }

    /**
     * Метод для получения сессии пользователя
     */
    @Override
    public void login(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
