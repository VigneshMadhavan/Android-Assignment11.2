package com.example.vimadhavan.assignment112;

/**
 * Created by vimadhavan on 5/1/2017.
 */

public class Item {
    private String title,desc,imageURL;
    private ItemViewHolder holder;
    private Boolean isReadyForLoad=false;

    public ItemViewHolder getHolder() {
        return holder;
    }

    public Boolean getReadyForLoad() {
        return isReadyForLoad;
    }

    public void setReadyForLoad(Boolean readyForLoad) {
        isReadyForLoad = readyForLoad;
    }

    public void setHolder(ItemViewHolder holder) {

        this.holder = holder;
    }

    public Item(String title, String desc, String imageURL) {
        this.title = title;
        this.desc = desc;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
