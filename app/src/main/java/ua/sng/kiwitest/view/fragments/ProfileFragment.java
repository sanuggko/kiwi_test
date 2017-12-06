package ua.sng.kiwitest.view.fragments;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import ua.sng.kiwitest.R;
import ua.sng.kiwitest.model.entities.album.AlbumModel;
import ua.sng.kiwitest.model.entities.profile.ProfileModel;
import ua.sng.kiwitest.presenters.ProfilePresenter;
import ua.sng.kiwitest.utils.DpPxUtils;
import ua.sng.kiwitest.utils.GlideApp;
import ua.sng.kiwitest.utils.Layout;
import ua.sng.kiwitest.utils.viewutils.ItemDecorationAlbumColumns;
import ua.sng.kiwitest.view.activities.BaseActivity;
import ua.sng.kiwitest.view.adapters.AlbumsAdapter;
import ua.sng.kiwitest.view.fragments.views.ProfileView;

@Layout(id = R.layout.fragment_profile)
public class ProfileFragment extends BaseFragment implements ProfileView {

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @BindView(R.id.profile_avatar_img)
    ImageView avatarImg;

    @BindView(R.id.profile_full_name_txt)
    TextView fullName;

    @BindView(R.id.profile_birthday_txt)
    TextView birthdayTxt;

    @BindView(R.id.profile_albums_rv)
    RecyclerView albumsRV;

    @Inject
    ProfilePresenter presenter;

    @Inject
    AlbumsAdapter albumsAdapter;

    private ProfileModel profileModel;

    @Override
    protected void setupInOnCreateView() {
        setupDefaultValues();
        setupAlbumsRecyclerView();
    }

    private void setupDefaultValues() {
        presenter.setView(this);

        presenter.loadUserInfo();
        presenter.getUserAlbumList();
    }

    private void setupAlbumsRecyclerView(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        albumsRV.setLayoutManager(gridLayoutManager);
        albumsRV.addItemDecoration(new ItemDecorationAlbumColumns(DpPxUtils.dpToPx(getActivity(), 10), 2));
        albumsRV.setAdapter(albumsAdapter);

        albumsAdapter.setItemClickListener(albumModel -> {
            openAlbumPhotoList(albumModel.getAlbumId(), albumModel.getName());
        });
    }

    private void fillUserInfo() {
        if (profileModel != null) {

            GlideApp.with(this)
                    .load(getString(R.string.avatar_url_template, profileModel.getProfileId()))
                    .circleCrop()
                    .into(avatarImg);

            fullName.setText(profileModel.getFullName());
            birthdayTxt.setText(profileModel.getBirthDay());
        }
    }

    private void openAlbumPhotoList(String albumId, String albumTitle){
        if(getActivity() != null){
            PhotoListFragment photoListFragment = PhotoListFragment.newInstance(albumId, albumTitle);

            ((BaseActivity) getActivity())
                    .showFragment(R.id.main_fragment_container, photoListFragment, false);
        }

    }

    @Override
    public String getTitle() {
        return getString(R.string.profile_title);
    }

    @Override
    protected void inject() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).getApplicationComponent()
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
    public void onProfileLoaded(ProfileModel profileModel) {
        this.profileModel = profileModel;
        fillUserInfo();
    }

    @Override
    public void onAlbumsLoaded(ArrayList<AlbumModel> albumModels) {
        if(albumsAdapter != null){
            albumsAdapter.setupData(albumModels);
        }
    }

    @Override
    public void showNoConnectionMessage() {

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
