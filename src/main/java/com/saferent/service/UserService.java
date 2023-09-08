package com.saferent.service;

import com.saferent.domain.*;
import com.saferent.domain.Role;
import com.saferent.domain.enums.*;
import com.saferent.dto.*;
import com.saferent.dto.request.*;
import com.saferent.exception.*;
import com.saferent.exception.message.*;
import com.saferent.mapper.*;
import com.saferent.repository.*;
import com.saferent.security.*;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class UserService {


    private final UserRepository userRepository;


    private final RoleService roleService;


    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final ReservationService reservationService;

    public UserService(UserRepository userRepository, RoleService roleService, @Lazy PasswordEncoder passwordEncoder, UserMapper userMapper, ReservationService reservationService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.reservationService = reservationService;
    }

    public User getUserByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException(
                        String.format(ErrorMessage.USER_NOT_FOUND_EXCEPTION, email)));
        return user;

    }

    public void saveUser(RegisterRequest registerRequest) {
        //!!! DTO dan gelen email sistemde daha önce var mı ???
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw  new ConflictException(
                    String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE,
                            registerRequest.getEmail())
            );
        }

        // !!! yeni kullanıcın rol bilgisini default olarak customer atıyorum
        Role role = roleService.findByType(RoleType.ROLE_CUSTOMER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        //!!! Db ye gitmeden önce şifre encode edilecek
        String encodedPassword= passwordEncoder.encode(registerRequest.getPassword());

        //!!! yeni kullanıcının gerekli bilgilerini setleyip DB ye gönderiyoruz
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setAddress(registerRequest.getAddress());
        user.setZipCode(registerRequest.getZipCode());
        user.setRoles(roles);

        userRepository.save(user);

    }

    public List<UserDTO> getAllUsers() {
        List<User> users =  userRepository.findAll();
        List<UserDTO> userDTOs = userMapper.map(users);
        return userDTOs;
    }

    public UserDTO getPrincipal() {
        User user =  getCurrentUser();
        UserDTO userDTO =  userMapper.userToUserDTO(user);
        return userDTO;

    }

    public User getCurrentUser(){
        String email =  SecurityUtils.getCurrentUserLogin().orElseThrow(()->
                new ResourceNotFoundException(ErrorMessage.PRINCIPAL_FOUND_MESSAGE));
        User user =  getUserByEmail(email);

        return user;

    }

    public Page<UserDTO> getUserPage(Pageable pageable) {

        Page<User> userPage = userRepository.findAll(pageable);

        return getUserDTOPage(userPage);

    }

    private Page<UserDTO> getUserDTOPage(Page<User> userPage) {
        return userPage.map(
                user -> userMapper.userToUserDTO(user));
    }

    public UserDTO getUserById(Long id) {

       User user = userRepository.findById(id).orElseThrow(()->
               new ResourceNotFoundException(
                       String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));

       return userMapper.userToUserDTO(user);
    }

    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {

         User user = getCurrentUser();

         // !!! builtIn ???
        if(user.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        // !!! Forma girilen OldPassword doğru mu
        if(!passwordEncoder.matches(updatePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorMessage.PASSWORD_NOT_MATCHED_MESSAGE);
        }

        // !!! yeni gelen şifreyi encode edilecek
        String hashedPassword =passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    @Transactional
    public void updateUser(UserUpdateRequest userUpdateRequest) {

        User user = getCurrentUser();
        // !!! builtIn ???
        if(user.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        // !!! email kontrol
        boolean emailExist = userRepository.existsByEmail(userUpdateRequest.getEmail());

        if(emailExist && !userUpdateRequest.getEmail().equals(user.getEmail())) {
            throw new ConflictException(
               String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE,userUpdateRequest.getEmail()));
        }

        userRepository.update(user.getId(),
                userUpdateRequest.getFirstName(),
                userUpdateRequest.getLastName(),
                userUpdateRequest.getPhoneNumber(),
                userUpdateRequest.getEmail(),
                userUpdateRequest.getAddress(),
                userUpdateRequest.getZipCode());

    }

    public void updateUserAuth(Long id, AdminUserUpdateRequest adminUserUpdateRequest) {
        User user = getById(id);
        //!!!built-in
        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        //!!! email kontrolu
        boolean emailExist = userRepository.existsByEmail(adminUserUpdateRequest.getEmail());

        if (emailExist && !adminUserUpdateRequest.getEmail().equals(user.getEmail())) {
            throw new ConflictException(
                    String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, adminUserUpdateRequest.getEmail()));
        }
        //!!! passsword kontrol
        String encodedPassword = null;
        if (adminUserUpdateRequest.getPassword() == null) {
            adminUserUpdateRequest.setPassword(user.getPassword());
        } else {
            encodedPassword = passwordEncoder.encode(adminUserUpdateRequest.getPassword());
            adminUserUpdateRequest.setPassword(encodedPassword);
        }
        //!!! Role
        Set<String> userStrRoles = adminUserUpdateRequest.getRoles();

        Set<Role> roles = convertRoles(userStrRoles);

        user.setFirstName(adminUserUpdateRequest.getFirstName());
        user.setLastName(adminUserUpdateRequest.getLastName());
        user.setEmail(adminUserUpdateRequest.getEmail());
        user.setPassword(adminUserUpdateRequest.getPassword());
        user.setPhoneNumber(adminUserUpdateRequest.getPhoneNumber());
        user.setAddress(adminUserUpdateRequest.getAddress());
        user.setZipCode(adminUserUpdateRequest.getZipCode());
        user.setBuiltIn(adminUserUpdateRequest.getBuiltIn());
        user.setRoles(roles);

        userRepository.save(user);


    }

    private Set<Role> convertRoles(Set<String> pRoles){ // pRoles={"Customer","Administrator"}
        Set<Role> roles = new HashSet<>();

        if(pRoles==null){
            Role userRole = roleService.findByType(RoleType.ROLE_CUSTOMER);
            roles.add(userRole);
        } else {
            pRoles.forEach(roleStr->{
                if(roleStr.equals(RoleType.ROLE_ADMIN.getName())){ // Administrator
                    Role adminRole = roleService.findByType(RoleType.ROLE_ADMIN);
                    roles.add(adminRole); //ROLE_ADMIN
                } else {
                    Role userRole = roleService.findByType(RoleType.ROLE_CUSTOMER);// Customer
                    roles.add(userRole);//ROLE_CUSTOMER
                }
            });
        }
        return roles;
    }

    public User getById(Long id) {
        User user = userRepository.findUserById(id).orElseThrow(()->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION,id)));
        return user;

    }

    public void removeUserById(Long id) {
        User user = getById(id);

        //!!!built-in
        if(user.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        // !!! reservasyon kontrol
        boolean exist =  reservationService.existByUser(user);
        if(exist) {
            throw  new BadRequestException(ErrorMessage.USER_CANT_BE_DELETED_MESSAGE);
        }

        userRepository.deleteById(id);

    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}



































