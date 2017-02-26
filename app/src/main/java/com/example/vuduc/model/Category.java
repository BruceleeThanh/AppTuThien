package com.example.vuduc.model;

/**
 * Created by Brucelee Thanh on 26/02/2017.
 */

public class Category {
    private String id;
    private String title;
    private String content;
    private String parent;

    public Category(String id, String title, String content, String parent) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
