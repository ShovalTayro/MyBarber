package com.example.mybarber.Objects;

import android.media.Image;

public class hairCut extends Object
{
    String hairCutName;
    String price;
    //Image img ;

    public hairCut()
    {

    }

    public hairCut(String hairCutName , String price ){
      this.hairCutName = hairCutName;
      this.price = price;
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
}

