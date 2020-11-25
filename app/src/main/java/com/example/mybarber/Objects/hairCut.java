package com.example.mybarber.Objects;

public class hairCut
{
    String hairCutName;
    int price;
//    Image img ;

    public hairCut()
    {

    }

    public hairCut(String hairCutName , int price ){
      this.hairCutName = hairCutName;
      this.price = price;
  }

    public String getHairCutName() {
        return hairCutName;
    }

    public int getPrice() {
        return price;
    }

    public void setHairCutName(String hairCutName) {
        this.hairCutName = hairCutName;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

