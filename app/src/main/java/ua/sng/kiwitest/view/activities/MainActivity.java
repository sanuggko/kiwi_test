package ua.sng.kiwitest.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import javax.inject.Inject;

import butterknife.BindView;
import ua.sng.kiwitest.R;
import ua.sng.kiwitest.utils.Layout;
import ua.sng.kiwitest.utils.SharedPreferencesManager;
import ua.sng.kiwitest.view.fragments.BaseFragment;
import ua.sng.kiwitest.view.fragments.ProfileFragment;

@Layout(id = R.layout.activity_main)
public class MainActivity extends BaseActivity implements FragmentManager.OnBackStackChangedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        if(AccessToken.getCurrentAccessToken() == null){
            startAuthActivity();
            return;
        }

        if(savedInstanceState == null) {
            setupDefaultValues();
        }
    }

    private void setupDefaultValues(){
        fragmentManager.addOnBackStackChangedListener(this);
        showFragment(R.id.main_fragment_container, getDefaultFragment(), false, true);
    }

    private void startAuthActivity(){
        Intent intent = new Intent(this, AuthorizationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setToolbarTitle(String title){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }
    public void logout(){
        new MaterialDialog.Builder(this)
                .title(R.string.log_out_str)
                .content(R.string.are_you_sure)
                .positiveText(R.string.ok_str)
                .negativeText(R.string.no_str)
                .positiveColorRes(R.color.colorPrimaryDark)
                .negativeColorRes(R.color.colorPrimaryDark)
                .onAny((dialog, which) -> {
                    if(which == DialogAction.POSITIVE){
                        if(AccessToken.getCurrentAccessToken() != null){
                            LoginManager.getInstance().logOut();
                            startAuthActivity();
                        }
                    }
                })
                .show();
    }


    @Override
    protected void inject() {
        getApplicationComponent().inject(this);
    }

    @Override
    protected BaseFragment getDefaultFragment() {
        return ProfileFragment.newInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_log_out:
                logout();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        currentFragment = (BaseFragment) fragmentManager.findFragmentById(R.id.main_fragment_container);

        if (currentFragment != null) {
            if (fragmentManager.getBackStackEntryCount() == 1) {
                finish();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onBackStackChanged() {
        currentFragment = (BaseFragment) fragmentManager.findFragmentById(R.id.main_fragment_container);
        setToolbarTitle(currentFragment.getTitle());

        if(getSupportActionBar() != null){
            getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(fragmentManager.getBackStackEntryCount() > 1);
        }

    }
}
