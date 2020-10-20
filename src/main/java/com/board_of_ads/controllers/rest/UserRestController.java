package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.Image;
import com.board_of_ads.models.Role;
import com.board_of_ads.models.User;
import com.board_of_ads.service.interfaces.RoleService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import com.board_of_ads.util.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user/")
@Slf4j
public class UserRestController {

    private final UserService userService;
    private final RoleService roleService;


    @GetMapping
    public Response<Principal> getUser(Principal user) {
        log.info("Use this default logger");
        return user != null
                ? new SuccessResponse<>(user)
                :  new ErrorResponse<>(new Error(401, "No auth user"));
    }

    @PostMapping (value = "/modal-reg")
    public ModelAndView Action(String eemail) {
        if (userService.getUserByEmail(eemail) == null) {
            User uuser = new User();
            uuser.setEmail(eemail);
            uuser.setPassword(eemail);
            uuser.setAvatar(new Image(null, "https://example.com/user.jpg"));
            Set<Role> roleUuser = new HashSet<>();
            roleUuser.add(roleService.getRoleByName("USER"));
            uuser.setRoles(roleUuser);
            userService.saveUser(uuser);
        }
        ModelAndView mv = new ModelAndView("redirect:/");
        //mv.addObject("eemail", eemail);
        return mv;
    }

}
