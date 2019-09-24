package com.oneworld.spring.cloud.hystrix.api.resource;

import java.util.Collections;
import java.util.List;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.oneworld.spring.cloud.hystrix.api.dto.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/users")
public class UserResource {

@Autowired
RestTemplate restTemplate;

private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    private String url ="http://localhost:8081/findAllUsers";

   @HystrixCommand(commandKey = "fallback", groupKey = "fallback", fallbackMethod = "hystrixFallBack")
    @GetMapping(value = "/hystrix")
    List<UserResponse> getAllUsers() {

        LOGGER.info("Before calling the server getAllUsers");
        List<UserResponse> userResponses = restTemplate.getForObject(url, List.class);
        LOGGER.info("After calling the server getAllUsers");

        return  userResponses;
    }

   @HystrixCommand(commandKey = "fallbackYT", groupKey = "fallbackYT", fallbackMethod = "hystrixFallBack")
    @GetMapping(value = "/hystrixYT")
    List<UserResponse> getAllUsersYT() {

        LOGGER.info("Before calling the server getAllUsersYT");
        List<UserResponse> userResponses = restTemplate.getForObject(url, List.class);
        LOGGER.info("After calling the server getAllUsersYT");

        return  userResponses;
    }

    @GetMapping(value = "")
    List<UserResponse> general() {

        return restTemplate.getForObject(url, List.class);
    }

    public List<UserResponse> hystrixFallBack() {
        LOGGER.info("hystrixFallBack");
        return  Collections.emptyList();
    }
}
