package ua.sng.kiwitest.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import butterknife.BindView;
import ua.sng.kiwitest.R;
import ua.sng.kiwitest.model.entities.album.PhotoModel;
import ua.sng.kiwitest.utils.GlideApp;
import ua.sng.kiwitest.utils.KiwiHelper;
import ua.sng.kiwitest.utils.Layout;
import ua.sng.kiwitest.view.activities.BaseActivity;

/**
 * Created by sanug on 06.12.2017.
 */

@Layout(id = R.layout.fragment_photo_detail)
public class PhotoDetailFragment extends BaseFragment {

    public static final String PHOTO_MODEL_KEY = "photo_model";

    public static PhotoDetailFragment newInstance(PhotoModel photoModel) {
        PhotoDetailFragment photoDetailFragment = new PhotoDetailFragment();

        Bundle arguments = new Bundle();
        arguments.putString(PHOTO_MODEL_KEY, new Gson().toJson(photoModel));
        photoDetailFragment.setArguments(arguments);

        return photoDetailFragment;
    }

    @BindView(R.id.photo_detail_img)
    ImageView photoImage;

    @BindView(R.id.photo_description)
    TextView photoDescriptionTxt;

    @BindView(R.id.photo_detail_date)
    TextView photoCreateDateTxt;

    @BindView(R.id.photo_item_likes_count)
    TextView likesCount;

    @BindView(R.id.photo_item_comments_count)
    TextView commentsCount;

    private PhotoModel photoModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String photoModelJson = getArguments().getString(PHOTO_MODEL_KEY);

            if (photoModelJson != null && !photoModelJson.isEmpty()) {
                photoModel = new Gson().fromJson(photoModelJson, PhotoModel.class);
            }
        }
    }

    @Override
    protected void setupInOnCreateView() {
        fillPhotoInfo();
    }

    private void fillPhotoInfo() {
        if (photoModel != null) {

            if (!photoModel.getPhotoUrl().isEmpty()) {

                GlideApp.with(getActivity())
                        .load(photoModel.getPhotoUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                        .into(photoImage);
            }

            photoCreateDateTxt.setText(KiwiHelper.parseDate(photoModel.getCreatedTime()));

            if (photoModel.getLikesDataModel() != null
                    && photoModel.getLikesDataModel().getLikesSummary() != null) {

                likesCount.setText(
                        String.valueOf(
                                photoModel
                                        .getLikesDataModel()
                                        .getLikesSummary()
                                        .getTotalCount()));
            }

            if (photoModel.getCommentsDataModel() != null
                    && photoModel.getCommentsDataModel().getCommentsSummary() != null) {

                commentsCount.setText(
                        String.valueOf(
                                photoModel
                                        .getCommentsDataModel()
                                        .getCommentsSummary()
                                        .getTotalCount()));
            }
        }
    }

    @Override
    public String getTitle() {
        return getString(R.string.photos_title);
    }

    @Override
    protected void inject() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity())
                    .getApplicationComponent()
                    .inject(this);
        }
    }

}
