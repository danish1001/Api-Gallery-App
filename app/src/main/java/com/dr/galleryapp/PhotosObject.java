package com.dr.galleryapp;

import com.google.gson.annotations.SerializedName;

public class PhotosObject {
    private int page;
    private int pages;

    @SerializedName("perpage")
    private int perPage;

    private int total;

    @SerializedName("photo")
    private PhotosArray[] photosArrays;

    public PhotosObject(int page, int pages, int perPage, int total, PhotosArray[] photosArrays) {
        this.page = page;
        this.pages = pages;
        this.perPage = perPage;
        this.total = total;
        this.photosArrays = photosArrays;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public PhotosArray[] getPhotosArrays() {
        return photosArrays;
    }

    public void setPhotosArrays(PhotosArray[] photosArrays) {
        this.photosArrays = photosArrays;
    }
}
