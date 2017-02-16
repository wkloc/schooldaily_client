package com.pgs.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mmalek on 2/16/2017.
 */
@RestController
public class AccessController {

    @Autowired
    private OAuth2RestOperations restTemplate;

    @RequestMapping(value = "/user")
    public JsonNode getProtectedResource() {
        JsonNode forObject = restTemplate.getForObject("https://localhost:8443/secure/user", JsonNode.class);
        return forObject;
    }

    @RequestMapping(value = "/kraj", method = RequestMethod.GET)
    @ResponseBody
    public String kraj() {
        return restTemplate.getForObject("https://localhost:8443/secure/country", String.class);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "Hello from unprotected endpoint!";
    }
}

