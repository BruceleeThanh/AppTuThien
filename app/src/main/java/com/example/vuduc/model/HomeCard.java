package com.example.vuduc.model;


public class HomeCard {
    private int anhChucNang;
    private String tenChucNang;

    public HomeCard(int anhChucNang, String tenChucNang) {
        this.anhChucNang = anhChucNang;
        this.tenChucNang = tenChucNang;
    }

    public HomeCard() {
    }

    public int getAnhChucNang() {
        return anhChucNang;
    }

    public void setAnhChucNang(int anhChucNang) {
        this.anhChucNang = anhChucNang;
    }

    public String getTenChucNang() {
        return tenChucNang;
    }

    public void setTenChucNang(String tenChucNang) {
        this.tenChucNang = tenChucNang;
    }
}
