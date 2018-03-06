package com.instagram.intagramfollowers.controller;

import com.instagram.intagramfollowers.service.InstagramService;
import com.instagram.intagramfollowers.service.LoginServiceProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;

@RestController
public class InstagramController {

    private final InstagramService service;
    private final LoginServiceProvider loginServiceProvider;

    public InstagramController(InstagramService service, LoginServiceProvider loginServiceProvider) {
        this.service = service;
        this.loginServiceProvider = loginServiceProvider;
    }

    @RequestMapping(value = "/getSubscribers")
    public String test(@RequestParam("myLogin") String myLogin,
                       @RequestParam("myPassword") String myPassword,
                       @RequestParam("userToFindLogin") String userToFindLogin) {
        StringBuilder sb = new StringBuilder();
        try {
            loginServiceProvider.login(myLogin, myPassword);
            Collection<String> users = service.getSubscribers(userToFindLogin);
            sb.append("Total count of fetched users: ").append(users.size()).append("<br>");
            users.forEach(newFollower -> sb.append(newFollower).append("<br>"));
        } catch (IOException e ) {
            return "Something went wrong, however there is some data <br>" + sb.toString();
        }
        return sb.toString();
    }
}
