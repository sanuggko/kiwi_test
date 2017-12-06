package ua.sng.kiwitest.model.entities.comments;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sanug on 05.12.2017.
 */

public class CommentsDataModel {

    @SerializedName("summary")
    private CommentsSummary commentsSummary;

    public CommentsSummary getCommentsSummary() {
        return commentsSummary;
    }
}
