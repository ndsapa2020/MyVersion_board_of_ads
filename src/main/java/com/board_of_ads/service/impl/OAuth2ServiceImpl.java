
package com.board_of_ads.service.impl;

import com.board_of_ads.models.Image;
import com.board_of_ads.models.Role;
import com.board_of_ads.models.User;
import com.board_of_ads.service.interfaces.OAuth2Service;
import com.board_of_ads.service.interfaces.RoleService;
import com.board_of_ads.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OAuth2ServiceImpl implements OAuth2Service {

    private final UserService userService;
    private final RoleService roleService;

    @Override
    public void auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            OAuth2User oAuth2User = token.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            User user = userService.getUserByEmail((String) attributes.get("email"));
            if (user != null) {
                setAuthenticated(user);
                return;
            }
            user = new User();
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.getRoleByName("USER"));
            user.setRoles(roles);
            if (token.getAuthorizedClientRegistrationId().equals("google")) {
                user.setAvatar(new Image(null, (String) attributes.get("picture")));
                user.setEmail((String) attributes.get("email"));
                user.setFirsName((String) attributes.get("given_name"));
                user.setLastName((String) attributes.get("family_name"));
                user.setEnable(true);
                user.setPassword((String) attributes.get("email"));
                userService.saveUser(user);
            } else if (token.getAuthorizedClientRegistrationId().equals("facebook")) {
                user.setEmail((String) attributes.get("email"));
                user.setPassword((String) attributes.get("email"));
                String name = (String) attributes.get("name");
                String[] userData = name.split(" ");
                user.setFirsName(userData[0]);
                user.setLastName(userData[1]);
                user.setEnable(true);
                userService.saveUser(user);
            }
            setAuthenticated(user);
        }
    }

    @Override
    public void setAuthenticated(User user) {
        SecurityContextHolder.getContext().setAuthentication(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return user.getAuthorities();
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return user;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "CustomOAuth2Authentication";
            }
        });
    }
}