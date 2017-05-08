package com.example.duyongkang.retrofittest.bean;

/**
 * Created by duyongkang on 2017/4/28.
 */

public class Task {
    private long id;
    private String text;

    public Task(long id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}

