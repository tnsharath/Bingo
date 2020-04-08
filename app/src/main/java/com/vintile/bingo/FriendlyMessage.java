package com.vintile.bingo;

/**
 * Created by Sharath on 2020/04/07
 **/
public class FriendlyMessage {

    private String id;
    private String text;
    private String name;
    private String photoUrl;
    private String imageUrl;

    public FriendlyMessage() {
    }

    public FriendlyMessage(String id, String text, String name, String photoUrl, String imageUrl) {
        this.id = id;
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getText() {
        return text;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
