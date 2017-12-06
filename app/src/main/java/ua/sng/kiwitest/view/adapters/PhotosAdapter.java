package ua.sng.kiwitest.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.sng.kiwitest.R;
import ua.sng.kiwitest.listeners.OnItemClickedListener;
import ua.sng.kiwitest.model.entities.album.PhotoModel;
import ua.sng.kiwitest.utils.DpPxUtils;
import ua.sng.kiwitest.utils.GlideApp;

/**
 * Created by sanug on 04.12.2017.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoItemHolder> {

    private Context context;
    private ArrayList<PhotoModel> photoModels;
    private OnItemClickedListener itemClickListener;

    @Inject
    public PhotosAdapter(Context context) {
        this.context = context;
        this.photoModels = new ArrayList<>();
    }

    public void setupData(ArrayList<PhotoModel> photoModels) {
        this.photoModels.clear();

        if (photoModels != null && photoModels.size() > 0) {
            this.photoModels.addAll(photoModels);
            notifyDataSetChanged();
        }
    }

    public void setItemClickListener(OnItemClickedListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public PhotoItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new PhotoItemHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoItemHolder holder, int position) {

        PhotoModel photoModel = photoModels.get(position);

        if (!photoModel.getPhotoUrl().isEmpty()) {

            GlideApp.with(context)
                    .load(photoModel.getPhotoUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .override(holder.coverImgSize)
                    .into(holder.image);
        }

        if (photoModel.getLikesDataModel() != null
                && photoModel.getLikesDataModel().getLikesSummary() != null) {

            holder.likesCount.setText(
                    String.valueOf(
                            photoModel
                                    .getLikesDataModel()
                                    .getLikesSummary()
                                    .getTotalCount()));
        }

        if (photoModel.getCommentsDataModel() != null
                && photoModel.getCommentsDataModel().getCommentsSummary() != null) {

            holder.commentsCount.setText(
                    String.valueOf(
                            photoModel
                                    .getCommentsDataModel()
                                    .getCommentsSummary()
                                    .getTotalCount()));
        }

        holder.mainItemContainer.setOnClickListener(view ->
                itemClickListener.onItemClicked(position));
    }

    @Override
    public int getItemCount() {
        return photoModels.size();
    }

    class PhotoItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.photo_item_container)
        FrameLayout mainItemContainer;

        @BindView(R.id.photo_item_img)
        ImageView image;

        @BindView(R.id.photo_item_likes_count)
        TextView likesCount;

        @BindView(R.id.photo_item_comments_count)
        TextView commentsCount;

        int coverImgSize;

        public PhotoItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            int screenWidth = itemView.getContext().getResources().getDisplayMetrics().widthPixels;
            int dividersTotalSize = DpPxUtils.dpToPx(itemView.getContext(), 20);

            coverImgSize = (screenWidth - dividersTotalSize) / 2;

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) image.getLayoutParams();
            params.width = coverImgSize;
            params.height = coverImgSize;
            image.setLayoutParams(params);
        }
    }

}
