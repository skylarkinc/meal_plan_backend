package com.example.manageserver.service;

import com.example.manageserver.common.exception.UsernameAlreadyExistsException;
import com.example.manageserver.model.User;
import com.example.manageserver.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.sql.SQLException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger logger = LogManager.getLogger(UserService.class);

    public User saveUser(User newUser){

        if(isPresent(newUser).equals(null)){
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            return userRepository.save(newUser);
        }
        else{
            throw new  UsernameAlreadyExistsException("Already Exist");
        }

    }

    public String isPresent(User newUser){
        String name = null;
        try{
           name =  userRepository.findByUsername(newUser.getUsername()).getUsername();
        }catch (Exception e){
           logger.info("NOT DUPLICATE RECORD");
        }
        return name;
    }
}

