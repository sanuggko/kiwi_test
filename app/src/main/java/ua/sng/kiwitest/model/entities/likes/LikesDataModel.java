package ua.sng.kiwitest.model.entities.likes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sanug on 05.12.2017.
 */

public class LikesDataModel {

    @SerializedName("summary")
    private LikesSummary likesSummary;

    public LikesSummary getLikesSummary() {
        return likesSummary;
    }
}
