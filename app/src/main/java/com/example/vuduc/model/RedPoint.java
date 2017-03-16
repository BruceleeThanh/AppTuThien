package com.example.vuduc.model;

import java.util.Date;
import java.util.List;


public class RedPoint {
    private String _id;
    private String title;
    private String content;
    private User creator;
    private String location;
    private float money;
    private String id_category;
    private String id_status;
    private List<String> images;
    private String videos;
    private Date happened_at;
    private Date created_at;
    private Date modified_at;
    private boolean checked;

    public RedPoint() {
    }

    public RedPoint(String _id, String title, String content, User creator, String location, float money,
                    String id_category, String id_status, List<String> images, String videos, Date happened_at, Date created_at,
                    Date modified_at, boolean checked) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.creator = creator;
        this.location = location;
        this.money = money;
        this.id_category = id_category;
        this.id_status = id_status;
        this.images = images;
        this.videos = videos;
        this.happened_at = happened_at;
        this.created_at = created_at;
        this.modified_at = modified_at;
        this.checked = checked;
    }

    public RedPoint(String _id, String title, String content, List<String> images) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public RedPoint(String title) {
        this.title = title;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getHappened_at() {
        return happened_at;
    }

    public void setHappened_at(Date happened_at) {
        this.happened_at = happened_at;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
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
