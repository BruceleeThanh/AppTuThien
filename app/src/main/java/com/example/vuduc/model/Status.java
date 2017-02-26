package com.example.vuduc.model;

/**
 * Created by Brucelee Thanh on 26/02/2017.
 */

public class Status {
    private String id;
    private String title;
    private String content;
    private String label;

    public Status(String id, String title, String content, String label) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.label = label;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
