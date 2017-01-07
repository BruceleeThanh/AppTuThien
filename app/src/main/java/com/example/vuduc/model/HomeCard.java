package com.example.vuduc.model;

/**
 * Created by VuDuc on 1/7/2017.
 */

public class HomeCard {
    private int anh;
    private String tile;

    public int getAnh() {
        return anh;
    }

    public void setAnh(int anh) {
        this.anh = anh;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public HomeCard(int anh, String tile) {
        this.anh = anh;
        this.tile = tile;
    }

    public HomeCard() {
    }
}
