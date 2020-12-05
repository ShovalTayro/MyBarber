package com.example.mybarber.Objects;

import android.media.Image;

public class hairCut extends Object {

    //variables
    String hairCutName;
    String price;
    //Image img ;

    //constructors
    public hairCut() {
    }
    public hairCut(String hairCutName , String price ){
      this.hairCutName = hairCutName;
      this.price = price;
  }

    //getters & setters
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

