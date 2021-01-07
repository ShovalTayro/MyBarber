package com.example.mybarber.Objects;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

public class hairCut extends Object {

    //variables
    String hairCutName;
    String price;
    String img ;
    //constructors
    public hairCut()
    {
    }
    public hairCut(String hairCutName , String price , String img)
    {
        this.hairCutName = hairCutName;
        this.price = price;
        this.img = img;
    }
    //getters & setters
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getHairCutName() {
        return this.hairCutName;
    }
    public String getPrice() {
        return price;
    }
    public void setHairCutName(String hairCutName) {
        this.hairCutName = hairCutName;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return  hairCutName + ", price = " +price;
    }
}

