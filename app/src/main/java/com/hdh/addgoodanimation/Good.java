package com.hdh.addgoodanimation;

import android.graphics.Bitmap;

/**
 * 商品类
 * Created by huangdianhua on 2016/8/1 11:15.
 */
public class Good {
    private String name;
    private Bitmap bitmap;
    private double price;

    public Good(String name, Bitmap bitmap, double price) {
        this.name = name;
        this.bitmap = bitmap;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
