package ua.sng.kiwitest.presenters;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import javax.inject.Inject;

import ua.sng.kiwitest.view.activities.views.AuthorizationView;

public class AuthorizationPresenter extends BasePresenter<AuthorizationView> {

    @Inject
    public AuthorizationPresenter( ){
    }

    public void startLoginViaFacebook(CallbackManager callbackManager){
        LoginManager
                .getInstance()
                .registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                if(getView() != null){
                                    getView().onLoginSuccess();
                                }
                            }

                            @Override
                            public void onCancel() {

                            }

                            @Override
                            public void onError(FacebookException error) {
                                if(getView() != null){
                                    getView().showErrorMessage(error.getLocalizedMessage());
                                }
                            }
                        });
    }

    @Override
    public void cancel() {

    }

    @Override
    public void destroy() {
    }
}
