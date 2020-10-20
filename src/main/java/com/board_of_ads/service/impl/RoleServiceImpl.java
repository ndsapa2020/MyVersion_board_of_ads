package com.board_of_ads.service.impl;


import com.board_of_ads.models.Role;
import com.board_of_ads.repository.RoleRepository;
import com.board_of_ads.service.interfaces.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }


    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
