package com.example.duyongkang.retrofittest;

/**
 * Created by duyongkang on 2017/4/27.
 */

public class GitHubRepo {
    private int id;
    private String name;

    public GitHubRepo() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "GitHubRepo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
