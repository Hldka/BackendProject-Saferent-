package com.saferent.service;

import com.saferent.domain.*;
import com.saferent.domain.enums.*;
import com.saferent.exception.*;
import com.saferent.exception.message.*;
import com.saferent.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByType(RoleType roleType){
        Role role = roleRepository.findByType(roleType).orElseThrow(()->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.ROLE_NOT_FOUND_EXCEPTION, roleType.name())));
        return role;
    }

}
