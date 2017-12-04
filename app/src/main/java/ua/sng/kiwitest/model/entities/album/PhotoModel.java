package ua.sng.kiwitest.model.entities.album;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sanug on 04.12.2017.
 */

public class PhotoModel {

    @SerializedName("created_time")
    private String createdTime;

    @SerializedName("id")
    private String photoId;

    @SerializedName("source")
    private String photoUrl;


    public String getCreatedTime() {
        return createdTime;
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
