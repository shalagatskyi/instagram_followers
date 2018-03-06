package com.instagram.intagramfollowers.service;

import java.io.IOException;
import java.util.Collection;

public interface InstagramService {
    Collection<String> getSubscribers(String user) throws IOException;
}
