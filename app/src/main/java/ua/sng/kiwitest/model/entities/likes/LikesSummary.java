package ua.sng.kiwitest.model.entities.likes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sanug on 05.12.2017.
 */

public class LikesSummary implements Serializable {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("can_like")
    private boolean canLike;

    @SerializedName("has_liked")
    private boolean hasLiked;

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isCanLike() {
        return canLike;
    }

    public boolean isHasLiked() {
        return hasLiked;
    }
}
