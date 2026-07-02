package com.retailflow.retailflow.service.implementation;

import com.retailflow.retailflow.dto.request.UserCreateReq;
import com.retailflow.retailflow.dto.request.UserLoginReq;
import com.retailflow.retailflow.dto.response.UserCreateResp;
import com.retailflow.retailflow.enums.UserRole;
import com.retailflow.retailflow.exceptions.DuplicateEmailException;
import com.retailflow.retailflow.exceptions.MapperNotWorkException;
import com.retailflow.retailflow.exceptions.UserNotFoundException;
import com.retailflow.retailflow.mapper.UserMapper;
import com.retailflow.retailflow.model.User;
import com.retailflow.retailflow.repositery.UserRepositery;
import com.retailflow.retailflow.service.interfaces.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepositery userRepositery;
    private final UserMapper userMapper;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepositery userRepositery, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepositery = userRepositery;
        this.userMapper = userMapper;
    }
    /**
     * register a new user
     * first check email is not already taken
     * then encode the password using bcrypt
     * by default every new user gets customer role
     */

    @Override
    public UserCreateResp register(UserCreateReq req) {
        if(userRepositery.existsByEmail(req.getEmail())){
            throw new DuplicateEmailException("user already exists");
        }
        User user=userMapper.toEntity(req);
        if(user==null){
            throw new MapperNotWorkException("mapper not detect properly req class");
        }
        String encodePassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setRole(UserRole.customer);// by default everyone who register status is customer
        com.retailflow.retailflow.model.User savedUser =userRepositery.save(user);
        return userMapper.fromEntity(savedUser);

    }


}
