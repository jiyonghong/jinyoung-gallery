package com.redice_inc.jinyounggallery;

import com.google.gson.annotations.SerializedName;

/**
 * Created by redice on 2/10/17.
 */

public class Gallery {
    String name;
    String imageUrl;
    String originalImageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOriginalImageUrl() {
        return originalImageUrl;
    }

    public void setOriginalImageUrl(String originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }
}
