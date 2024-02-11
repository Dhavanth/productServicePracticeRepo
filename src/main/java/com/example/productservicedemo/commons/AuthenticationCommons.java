package com.example.productservicedemo.commons;

import com.example.productservicedemo.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationCommons {

    private RestTemplate restTemplate;

    @Autowired
    public AuthenticationCommons(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public UserDto validateToken(String token){
        // SEND THE TOKEN TO USER_SERVICE TO VALIDATE THE TOKEN
        ResponseEntity<UserDto> userDtoResponse = restTemplate.postForEntity(
                "http://localhost:8181/users/validate"+token,
                null,
                UserDto.class
        );

        if(userDtoResponse.getBody() == null){
            return null;
        }

        return userDtoResponse.getBody();

    }
}
