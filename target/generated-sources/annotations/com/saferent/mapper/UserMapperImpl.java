package com.saferent.mapper;

import com.saferent.domain.Role;
import com.saferent.domain.User;
import com.saferent.dto.UserDTO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-08T09:58:39+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        Set<Role> set = user.getRoles();
        if ( set != null ) {
            userDTO.setRoles( new LinkedHashSet<Role>( set ) );
        }
        userDTO.setId( user.getId() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setLastName( user.getLastName() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setPassword( user.getPassword() );
        userDTO.setPhoneNumber( user.getPhoneNumber() );
        userDTO.setAddress( user.getAddress() );
        userDTO.setZipCode( user.getZipCode() );
        userDTO.setBuiltIn( user.getBuiltIn() );

        return userDTO;
    }

    @Override
    public List<UserDTO> map(List<User> userList) {
        if ( userList == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( userList.size() );
        for ( User user : userList ) {
            list.add( userToUserDTO( user ) );
        }

        return list;
    }
}
