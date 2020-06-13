package com.pc.ks.List;

public class SecondRecyclerViewFragment {
    private String name;
    private int imageId;
    private String flag;
    public SecondRecyclerViewFragment(String name, int imageId, String num) {
        this.name = name;
        this.imageId = imageId;
        this.flag = num;
    }
    public String getName() {
        return name;
    }
    public String getFlag(){return flag;}
    public int getImageId() {
        return imageId;
    }
}
