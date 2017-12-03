package ua.sng.kiwitest.model.network;

import com.facebook.AccessToken;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Oleksandr on 14.11.2017.
 */

public class AuthenticationInterceptor implements Interceptor {

    @Inject
    public AuthenticationInterceptor() {  //NO SONAR this is method for DI
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Request request = null;

        if(accessToken != null && !accessToken.getToken().isEmpty()){
            request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + accessToken.getToken())
                    .build();
        } else {
            request = chain.request().newBuilder()
                    .build();
        }

        return chain.proceed(request);
    }
}
