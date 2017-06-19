package com.example.user.nehaber.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by User on 14.7.2015.
 */
public class News {

    private UUID Id;
    private UUID categoryId;
    private String title;
    private String link;
    private String image;
    private String description;
    private String search;

    public void setSearch(String search) {
        this.search = search;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSearch() {
        return search;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    private String pubDate;

    public void setId(UUID id) {
        Id = id;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public UUID getId() {
        return Id;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public String getDate() {
        String datelast = "";
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        DateFormat dateFormat1 = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        try {
            Date date = dateFormat.parse(getPubDate());
            datelast = dateFormat1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datelast;

    }
}
