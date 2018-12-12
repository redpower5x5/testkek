package com.practise.aionixx.oauthpractise.api.model;

public class GitHubRepo {
    private int id;
    private String name;
    private String login;

    public String getLogin() {
        return login;
    }

    public GitHubRepo() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
