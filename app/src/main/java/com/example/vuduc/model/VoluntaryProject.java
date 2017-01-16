package com.example.vuduc.model;

import java.util.Date;


public class VoluntaryProject {
    private String _id;
    private String title;
    private String content;
    private String creator;
    private String location;
    private Date time;
    private float money;
    private String id_category;
    private String id_status;
    private String images;
    private String videos;
    private Date created_at;
    private Date modified_at;

    public VoluntaryProject(String _id, String content, Date created_at, String creator, String id_category, String id_status, String images, String location, Date modified_at, float money, Date time, String title, String videos) {
        this._id = _id;
        this.content = content;
        this.created_at = created_at;
        this.creator = creator;
        this.id_category = id_category;
        this.id_status = id_status;
        this.images = images;
        this.location = location;
        this.modified_at = modified_at;
        this.money = money;
        this.time = time;
        this.title = title;
        this.videos = videos;
    }

    public VoluntaryProject() {
    }

    public VoluntaryProject(String title) {
        this.title = title;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getId_status() {
        return id_status;
    }

    public void setId_status(String id_status) {
        this.id_status = id_status;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getModified_at() {
        return modified_at;
    }

    public void setModified_at(Date modified_at) {
        this.modified_at = modified_at;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }
}
