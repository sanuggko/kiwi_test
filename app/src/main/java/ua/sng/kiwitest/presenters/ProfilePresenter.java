package ua.sng.kiwitest.presenters;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import ua.sng.kiwitest.model.entities.ProfileModel;
import ua.sng.kiwitest.model.network.ApiRequestService;
import ua.sng.kiwitest.view.fragments.views.ProfileView;

/**
 * Created by sanug on 03.12.2017.
 */

public class ProfilePresenter extends BasePresenter<ProfileView> {

    @Inject
    ApiRequestService apiRequestService;

    private CompositeDisposable compositeDisposable;

    @Inject
    public ProfilePresenter() {
        this.compositeDisposable = new CompositeDisposable();
    }

    public void loadUserInfo() {
        getView().showLoading();

        apiRequestService
                .getUserProfile("id,name,email,birthday,gender")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ProfileModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if(compositeDisposable != null){
                            compositeDisposable.add(d);
                        }
                    }

                    @Override
                    public void onNext(Response<ProfileModel> profileModelResponse) {
                        if(getView() != null){
                            if(profileModelResponse.isSuccessful()){
                                getView().onProfileLoaded(profileModelResponse.body());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(getView() != null){
                            getView().hideLoading();
                            getView().showErrorMessage(e.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        if(getView() != null){
                            getView().hideLoading();
                        }
                    }
                });

//        GraphRequest request = GraphRequest.newMeRequest(
//                AccessToken.getCurrentAccessToken(),
//                (object, response) -> {
//                    if(getView() != null) {
//                        getView().hideLoading();
//
//                        if (response.getError() != null) {
//                            getView().showErrorMessage(response.getError().getErrorMessage());
//                        } else {
//                            String profileJson = response.getJSONObject().toString();
//                            ProfileModel profileModel = new Gson().fromJson(profileJson, ProfileModel.class);
//
//                            if(profileModel != null){
//                                getView().onProfileLoaded(profileModel);
//                            }
//                        }
//                    }
//                });
//
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,email,birthday,gender");
//
//        request.setParameters(parameters);
//        request.executeAsync();
    }

    public void getPhotosByAlbum(String albums){

        //for get albums (curl -i -X GET \
        //"https://graph.facebook.com/v2.11/me?fields=albums.fields(cover_photo.fields(source))"



//        GraphRequest request = GraphRequest.newGraphPathRequest(
//                accessToken,
//                "/957339981085278/photos",
//                new GraphRequest.Callback() {
//                    @Override
//                    public void onCompleted(GraphResponse response) {
//                        // Insert your code here
//                    }
//                });
//
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "source,name,place,created_time,picture,likes.fields(name,created_time)");
//        request.setParameters(parameters);
//        request.executeAsync();
    }

    @Override
    protected void cancel() {
        if(compositeDisposable != null
                && !compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }

    @Override
    protected void destroy() {
        compositeDisposable = null;
        apiRequestService = null;
    }
}
