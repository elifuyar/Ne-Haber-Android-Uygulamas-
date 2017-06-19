package com.example.user.nehaber.models;

/**
 * Created by User on 14.7.2015.
 */
public class Newses extends LimitedQueue<News> {

    public Newses(int limit) {
        super(limit);
    }
}
