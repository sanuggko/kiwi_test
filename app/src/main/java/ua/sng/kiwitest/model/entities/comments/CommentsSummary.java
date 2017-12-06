package ua.sng.kiwitest.model.entities.comments;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sanug on 05.12.2017.
 */

public class CommentsSummary implements Serializable {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("order")
    private String orderType;

    @SerializedName("can_comment")
    private boolean canComment;

    public int getTotalCount() {
        return totalCount;
    }

    public String getOrderType() {
        return orderType;
    }

    public boolean isCanComment() {
        return canComment;
    }
}
