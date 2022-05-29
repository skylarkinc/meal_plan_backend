package com.example.manageserver.controller;


import com.example.manageserver.common.config.UserValidator;
import com.example.manageserver.common.exception.UsernameAlreadyExistsException;
import com.example.manageserver.common.payload.JWTLoginSuccessResponse;
import com.example.manageserver.common.payload.LoginRequest;
import com.example.manageserver.common.security.JwtTokenProvider;
import com.example.manageserver.common.security.SecurityConstants;
import com.example.manageserver.model.User;
import com.example.manageserver.service.validationService.MapValidationErrorService;
import com.example.manageserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;


    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {


            userValidator.validate(user, result);

            ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
            if (errMap != null) return errMap;
            try {
            User newUser = userService.saveUser(user);
            return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

        } catch (UsernameAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.ALREADY_REPORTED);
        }
            catch (Exception e) {
                return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }


}
