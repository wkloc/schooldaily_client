package com.pgs.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mmalek on 2/16/2017.
 */
@RestController
public class AccessController {

    @Value("${schooldaily.server.url}")
    private String serverUrl;

    @Autowired
    private OAuth2RestOperations restTemplate;

    @RequestMapping(value = "/user")
    public JsonNode getProtectedResource() {
        JsonNode forObject = restTemplate.getForObject(serverUrl + "/secure/user", JsonNode.class);
        return forObject;
    }

    @RequestMapping(value = "/user2")
    public JsonNode getProtectedResource2() {
        JsonNode forObject = restTemplate.getForObject(serverUrl + "/secure/user2", JsonNode.class);
        return forObject;
    }

    @RequestMapping(value = "/kraj", method = RequestMethod.GET)
    @ResponseBody
    public String kraj() {
        return restTemplate.getForObject(serverUrl + "/secure/country", String.class);
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public String facebookString() {
        return restTemplate.getForObject(serverUrl + "/secure/facebook", String.class);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "Hello from unprotected endpoint!";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        String ret = restTemplate.getForObject(serverUrl + "/oauth/logout", String.class);
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return ret;
    }
}

