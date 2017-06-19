package com.example.user.nehaber.models;

import java.util.UUID;

/**
 * Created by User on 14.7.2015.
 */
public class Category {
    private UUID id;
    private  String name;
    private String url;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UUID getId() {
        return id;
    }

    public String  getName() {
        return name;
    }

    public String  getUrl() {
        return url;
    }


}
