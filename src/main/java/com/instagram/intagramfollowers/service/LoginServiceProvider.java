package com.instagram.intagramfollowers.service;

import org.brunocvcunha.instagram4j.Instagram4j;

import java.io.IOException;
import java.util.Optional;

public interface LoginServiceProvider {
    void login(String username, String password) throws IOException;

    Optional<Instagram4j> getLoggedUser();
}
