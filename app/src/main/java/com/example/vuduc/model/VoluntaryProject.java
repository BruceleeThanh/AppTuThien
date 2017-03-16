package com.example.vuduc.model;

import java.util.Date;
import java.util.List;


public class VoluntaryProject {
    private String _id;
    private String title;
    private String content;
    private User creator;
    private String location;
    private Date time;
    private String money;
    private String id_category;
    private String id_status;
    private List<String> images;
    private String videos;
    private Date created_at;
    private Date modified_at;

    public VoluntaryProject(String _id, String title, String content, List<String> images) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public VoluntaryProject(String _id, Date created_at, String title, String content, List<String> images, String location, 
                            String id_category, String id_status, String  money, User creator) {
        this._id = _id;
        this.created_at = created_at;
        this.title = title;
        this.content = content;
        this.images = images;
        this.location = location;
        this.id_category = id_category;
        this.id_status = id_status;
        this.money = money;
        this.creator = creator;
    }

    public VoluntaryProject(String _id, String title, String content, User creator, String location, Date time, String  money, String id_category, String id_status, List<String> images, String videos, Date created_at, Date modified_at) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.creator = creator;
        this.location = location;
        this.time = time;
        this.money = money;
        this.id_category = id_category;
        this.id_status = id_status;
        this.images = images;
        this.videos = videos;
        this.created_at = created_at;
        this.modified_at = modified_at;
    }

    public VoluntaryProject(String _id, String content, Date created_at, User creator, String id_category, String id_status, List<String> images, String location, Date modified_at, String money, Date time, String title, String videos) {
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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
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
