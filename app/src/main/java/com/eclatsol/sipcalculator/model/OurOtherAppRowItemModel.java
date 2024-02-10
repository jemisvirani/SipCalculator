package com.eclatsol.sipcalculator.model;


public class OurOtherAppRowItemModel {
    private String desc;
    private int imageId;
    private String title;

    public OurOtherAppRowItemModel(int i, String str) {
        imageId = i;
        title = str;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int i) {
        imageId = i;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String str) {
        title = str;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String str) {
        desc = str;
    }

    public String toString() {
        return "OurOtherAppRowItemModel{imageId=" + imageId + ", title='" + this.title + "', desc='" + this.desc + "'}";
    }
}
