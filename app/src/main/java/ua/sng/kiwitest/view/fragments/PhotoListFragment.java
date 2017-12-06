package ua.sng.kiwitest.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import ua.sng.kiwitest.R;
import ua.sng.kiwitest.model.entities.album.PhotoModel;
import ua.sng.kiwitest.presenters.PhotosPresenter;
import ua.sng.kiwitest.utils.DpPxUtils;
import ua.sng.kiwitest.utils.Layout;
import ua.sng.kiwitest.utils.viewutils.ItemDecorationAlbumColumns;
import ua.sng.kiwitest.view.activities.BaseActivity;
import ua.sng.kiwitest.view.adapters.PhotosAdapter;
import ua.sng.kiwitest.view.fragments.views.PhotosView;

/**
 * Created by sanug on 05.12.2017.
 */

@Layout(id = R.layout.fragment_photo_list)
public class PhotoListFragment extends BaseFragment implements PhotosView {

    public static final String ALBUM_ID_KEY = "album_id_key";
    public static final String ALBUM_TITLE_KEY = "album_title_key";

    public static PhotoListFragment newInstance(String albumId, String albumTitle) {
        PhotoListFragment photoListFragment = new PhotoListFragment();

        Bundle arguments = new Bundle();
        arguments.putString(ALBUM_ID_KEY, albumId);
        arguments.putString(ALBUM_TITLE_KEY, albumTitle);

        photoListFragment.setArguments(arguments);

        return photoListFragment;
    }

    @BindView(R.id.photos_rv)
    RecyclerView photosRV;

    @BindString(R.string.no_internet_connection)
    String noInternetConnectionMessage;

    @Inject
    PhotosPresenter presenter;

    @Inject
    PhotosAdapter photosAdapter;

    private String fragmentTitle;
    private String albumId;
    private ArrayList<PhotoModel> photoModels;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            fragmentTitle = getArguments().getString(ALBUM_TITLE_KEY);
            albumId = getArguments().getString(ALBUM_ID_KEY);
        }
    }

    @Override
    protected void setupInOnCreateView() {
        setupDefaultValues();
    }

    private void setupDefaultValues(){
        photoModels = new ArrayList<>();
        setupPhotosRV();

        presenter.setView(this);
        presenter.getPhotosFromAlbum(albumId);
    }

    private void setupPhotosRV(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        photosRV.setLayoutManager(gridLayoutManager);
        photosRV.addItemDecoration(new ItemDecorationAlbumColumns(DpPxUtils.dpToPx(getActivity(), 10), 2));
        photosRV.setAdapter(photosAdapter);

        photosAdapter.setItemClickListener(this::showDetailPhotoPager);
    }

    private void showDetailPhotoPager(int currentPosition){
        if(getActivity() != null){
            PhotoDetailPagerFragment photoDetailPagerFragment = PhotoDetailPagerFragment
                    .newInstance(currentPosition, photoModels);

            ((BaseActivity) getActivity())
                    .showFragment(R.id.main_fragment_container, photoDetailPagerFragment, false, false);
        }
    }

    @Override
    public String getTitle() {
        return fragmentTitle != null && !fragmentTitle.isEmpty()
                ? fragmentTitle
                : getString(R.string.photos_title);
    }

    @Override
    protected void inject() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity())
                    .getApplicationComponent()
                    .inject(this);
        }
    }


    @Override
    public void showLoading() {
        if(!getProgressDialog().isShowing()){
            getProgressDialog().show();
        }
    }

    @Override
    public void hideLoading() {
        if(getProgressDialog() != null){
            getProgressDialog().dismiss();
        }
    }

    @Override
    public void showErrorMessage(String error) {
        showToast(error);
    }

    @Override
    public void showNoConnectionMessage() {
        showToast(noInternetConnectionMessage);
    }

    @Override
    public void onPhotosListLoaded(ArrayList<PhotoModel> photoModels) {
        if(photoModels != null){
            this.photoModels.clear();
            this.photoModels.addAll(photoModels);

            if(photosAdapter != null){
                photosAdapter.setupData(this.photoModels);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(presenter != null){
            presenter.cancel();
            presenter.destroy();
        }
    }
}
