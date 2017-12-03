package ua.sng.kiwitest.view.fragments;


import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

import butterknife.BindView;
import ua.sng.kiwitest.R;
import ua.sng.kiwitest.model.entities.ProfileModel;
import ua.sng.kiwitest.presenters.ProfilePresenter;
import ua.sng.kiwitest.utils.Layout;
import ua.sng.kiwitest.utils.viewutils.KiwiViewUtils;
import ua.sng.kiwitest.view.activities.BaseActivity;
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

    @Inject
    ProfilePresenter presenter;

    private ProfileModel profileModel;
    private MaterialDialog progressDialog;

    @Override
    protected void setupInOnCreateView() {
        setupDefaultValues();
    }

    private void setupDefaultValues() {
        progressDialog = KiwiViewUtils.generateProgressDialog(getActivity());
        presenter.setView(this);

        presenter.loadUserInfo();
    }

    private void fillUserInfo() {
        if (profileModel != null) {

            RequestOptions options = new RequestOptions();
            options.circleCrop();

            Glide.with(this)
                    .load(getString(R.string.avatar_url_template, profileModel.getProfileId()))
                    .apply(options)
                    .into(avatarImg);

            fullName.setText(profileModel.getFullName());
            birthdayTxt.setText(profileModel.getBirthDay());
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
        if(progressDialog != null && !progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if(progressDialog != null){
            progressDialog.dismiss();
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
    public void showNoConnectionMessage() {

    }
}
