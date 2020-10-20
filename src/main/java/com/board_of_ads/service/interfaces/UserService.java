package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    User getUserByEmail(String email);

    User saveUser(User user);

    List<User> getAllUsers();

    void deleteUser(Long id);
}
