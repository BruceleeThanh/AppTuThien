package com.example.vuduc.model;

import android.graphics.Bitmap;

/**
 * Created by Brucelee Thanh on 16/01/2017.
 */

public class Image {
    private Bitmap img;

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public Image(Bitmap img) {
        this.img = img;
    }

    public Image() {
    }
}
