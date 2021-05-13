package com.dr.galleryapp;

public class Post {

    private PhotosObject photos;
    private String stat;

    public Post(PhotosObject photos, String stat) {
        this.photos = photos;
        this.stat = stat;
    }

    public PhotosObject getPhotos() {
        return photos;
    }

    public void setPhotos(PhotosObject photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
