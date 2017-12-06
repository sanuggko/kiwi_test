package ua.sng.kiwitest.view.activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.OnClick;
import ua.sng.kiwitest.R;
import ua.sng.kiwitest.presenters.AuthorizationPresenter;
import ua.sng.kiwitest.utils.Layout;
import ua.sng.kiwitest.utils.viewutils.KiwiViewUtils;
import ua.sng.kiwitest.view.activities.views.AuthorizationView;
import ua.sng.kiwitest.view.fragments.BaseFragment;

/**
 * Created by sanug on 02.12.2017.
 */

@Layout(id = R.layout.activity_auth)
public class AuthorizationActivity extends BaseActivity implements AuthorizationView {

    @Inject
    AuthorizationPresenter presenter;

    private MaterialDialog progressDialog;
    private List<String> permissionNeeds= Arrays.asList("email", "user_birthday", "user_friends", "user_photos");

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDefaultValues();

        copyToClipBoardTestEmail();
    }

    private void setupDefaultValues(){
        callbackManager = CallbackManager.Factory.create();
        progressDialog = KiwiViewUtils.generateProgressDialog(this);
        presenter.setView(this);
    }

    private void copyToClipBoardTestEmail(){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Test email", "open_nadmqks_user@tfbnw.net");

        if(clipboard != null) {
            clipboard.setPrimaryClip(clip);
            showToast("Test email copied to clipboard, you can paste it. Password: Kiwi2017");
        }

    }

    @OnClick(R.id.auth_login_action_layout)
    public void onLoginClicked(){
        LoginManager
                .getInstance()
                .logInWithReadPermissions(this, permissionNeeds);

        presenter.startLoginViaFacebook(callbackManager);
    }

    @Override
    protected void inject() {
        getApplicationComponent().inject(this);
    }

    @Override
    protected BaseFragment getDefaultFragment() {
        return null;
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
    public void showNoConnectionMessage() {

    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
