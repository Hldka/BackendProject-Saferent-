package com.saferent.mapper;

import com.saferent.domain.*;
import com.saferent.dto.*;
import org.mapstruct.*;

import java.util.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);

    List<UserDTO> map(List<User> userList);
}
