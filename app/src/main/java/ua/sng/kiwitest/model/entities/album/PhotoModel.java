package ua.sng.kiwitest.model.entities.album;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ua.sng.kiwitest.model.entities.comments.CommentsDataModel;
import ua.sng.kiwitest.model.entities.likes.LikesDataModel;

/**
 * Created by sanug on 04.12.2017.
 */

public class PhotoModel implements Serializable{

    @SerializedName("created_time")
    private String createdTime;

    @SerializedName("id")
    private String photoId;

    @SerializedName("source")
    private String photoUrl;

    @SerializedName("picture")
    private String thumbnail;

    @SerializedName("likes")
    private LikesDataModel likesDataModel;

    @SerializedName("comments")
    private CommentsDataModel commentsDataModel;

    public CommentsDataModel getCommentsDataModel() {
        return commentsDataModel;
    }

    public LikesDataModel getLikesDataModel() {
        return likesDataModel;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getPhotoUrl() {
        return photoUrl != null ? photoUrl : "";
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
