package com.instagram.intagramfollowers.service;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class LoginServiceProviderImpl implements LoginServiceProvider {
    private Instagram4j loggedUser;

    @Override
    public void login(String username, String password) throws IOException{
        Instagram4j loggedUser = Instagram4j
                .builder()
                .username(username)
                .password(password)
                .build();
        loggedUser.setup();
        loggedUser.login();
        this.loggedUser = loggedUser;
    }

    @Override
    public Optional<Instagram4j> getLoggedUser() {
        return Optional.ofNullable(loggedUser);
    }
}
