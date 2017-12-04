package ua.sng.kiwitest.model.entities.album;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sanug on 04.12.2017.
 */

public class AlbumModel {

    @SerializedName("cover_photo")
    private PhotoModel photoModel;

    @SerializedName("created_time")
    private String createTime;

    @SerializedName("name")
    private String name;

    @SerializedName("count")
    private int photoCount;

    @SerializedName("id")
    private String albumId;

    public PhotoModel getPhotoModel() {
        return photoModel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getName() {
        return name;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public String getAlbumId() {
        return albumId;
    }
}
