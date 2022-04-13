package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enuns.UserStatus;
import com.ead.authuser.enuns.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Log4j2
@RestController
@CrossOrigin (origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    // usando a anotação @Log4j2 do lombok não precisamos instanciar a variavel abaixo
    //Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    UserService service;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody
                                                   @Validated(UserDto.UserView.RegistrarionPost.class)
                                                   @JsonView(UserDto.UserView.RegistrarionPost.class) UserDto userDto){

        log.debug("POST registerUser userDto received {}",userDto.toString());
        if(service.existsByUsername(userDto.getUsername())){
            log.warn("Username {} is Already Taken ", userDto.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: Username is Already Taken!");
        }
        if(service.existsByEmail(userDto.getEmail())){
            log.warn("Email {} is Already Taken ", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: Username is Already Taken!");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        service.save(userModel);
        log.debug("POST registerUser userModel received {}",userModel.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }
}
