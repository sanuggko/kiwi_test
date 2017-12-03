package ua.sng.kiwitest.model.network;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ua.sng.kiwitest.model.entities.ProfileModel;

public interface ApiRequestService {

    @GET(NetworkUrls.USER_PROFILE_URL)
    Observable<Response<ProfileModel>> getUserProfile(@Query("fields") String fields);

}
