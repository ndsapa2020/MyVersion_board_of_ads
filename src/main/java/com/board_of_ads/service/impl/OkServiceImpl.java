package com.board_of_ads.service.impl;

import com.board_of_ads.models.Image;
import com.board_of_ads.models.User;
import com.board_of_ads.service.interfaces.OkService;
import com.board_of_ads.service.interfaces.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.security.auth-ok")
public class OkServiceImpl implements OkService {

    private final UserService userService;

    private String clientId;
    private String clientSecret;
    private String responseType;
    private String redirectURL;
    private String authURL;
    private String tokenURL;
    private String userInfoURL;
    private String scope;
    private String grantType;
    private String method;
    private String clientPublic;
    private String format;

    @Override
    public void auth(String code) {
        String token = getToken(getRequestBody(code));
        String linkUserDataBody = getLinkUserDataBody(token, md5Custom(getAuthURLWithSign(md5Custom(token + clientSecret))));
        Map<String, String> userData = getUserData(linkUserDataBody);
        User user = init(userData);
        login(user);
    }

    @Override
    public String getAuthURLWithSign(String sign) {
        return "application_key=" + clientPublic
                + "format=" + format
                + "method=" + method
                + sign;
    }

    @Override
    public String getAuthURL() {
        return authURL + "?"
                + "client_id=" + clientId
                + "&scope=" + scope
                + "&response_type=" + responseType
                + "&redirect_uri=" + redirectURL;
    }

    @Override
    public String getRequestBody(String code) {
        return tokenURL + "?"
                + "code=" + code
                + "&client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&redirect_uri=" + redirectURL
                + "&grant_type=" + grantType;
    }

    @Override
    public String getToken(String body) {
        HttpEntity<String> httpEntity = new HttpEntity<>(body);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entity = restTemplate.exchange(body, HttpMethod.POST, httpEntity, String.class);
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

    public static String md5Custom(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);
        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }

    @Override
    public String getLinkUserDataBody(String token, String sig) {
        return userInfoURL + "?"
                + "application_key=" + clientPublic
                + "&format=" + format
                + "&method=" + method
                + "&sig=" + sig
                + "&access_token=" + token;
    }

    @Override
    public Map<String, String> getUserData(String token) {
        HttpEntity<String> httpEntity = new HttpEntity<>(token);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entity = restTemplate.exchange(token, HttpMethod.GET, httpEntity, String.class);
        Object obj = null;
        try {
            obj = new JSONParser().parse(entity.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        Map<String, String> userData = new HashMap<>();
        userData.put("first_name", (String) jsonObject.get("first_name"));
        userData.put("last_name", (String) jsonObject.get("last_name"));
        userData.put("email", (String) jsonObject.get("email"));
        userData.put("avatar_link", "https://ok.ru/profile/"
                + jsonObject.get("uid") + "/pphotos/" + jsonObject.get("photo_id"));
        return userData;
    }

    @Override
    public void login(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public User init(Map<String, String> userData) {
        User user = userService.getUserByEmail(userData.get("email"));
        if (user != null) {
            return user;
        }
        user = new User();
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
