package com.instagram.intagramfollowers.service;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.brunocvcunha.inutils4j.MyNumberUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InstagramServceImpl implements InstagramService{

    private final LoginServiceProvider loginServiceProvider;

    public InstagramServceImpl(LoginServiceProvider loginServiceProvider) {
        this.loginServiceProvider = loginServiceProvider;
    }

    @Override
    public Collection<String> getSubscribers(String userToFind) throws IOException {
        Set<String> followers = new HashSet<>();

        Optional<Instagram4j> loggedUser = loginServiceProvider.getLoggedUser();
        if (!loggedUser.isPresent()) {
            return followers;
        }

        InstagramSearchUsernameResult userResult = loggedUser.get()
                .sendRequest(new InstagramSearchUsernameRequest(userToFind));

        InstagramUser user = userResult.getUser();

        String nextMaxId = "";
        try {
            while (followers.size() < user.getFollower_count()) {
                Thread.sleep(MyNumberUtils.randomLongBetween(250, 500));// simulate user activity

                InstagramGetUserFollowersResult githubFollowers = loggedUser.get()
                        .sendRequest(new InstagramGetUserFollowersRequest(user.getPk(), nextMaxId));
                nextMaxId = githubFollowers.next_max_id;

                Set<String> newElements = githubFollowers
                        .getUsers()
                        .stream()
                        .map(InstagramUserSummary::getUsername)
                        .collect(Collectors.toSet());

                //stop if returned users are already in the fetched data
                // useful also when totalCount of users has changed during fetching
                if (followers.containsAll(newElements)) {
                    return followers;
                }

                followers.addAll(newElements);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return followers;
    }

}
