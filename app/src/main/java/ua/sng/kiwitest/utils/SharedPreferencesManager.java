package ua.sng.kiwitest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPreferencesManager {

    private static final String SHARED_PREF_FILE_NAME = "kiwi_preferences";

    private static final String IS_LOGGED_IN = "is_logged_in";
    private static final String SESSION_TOKEN = "session";

    private Context context;
    private SharedPreferences sharedPreferences;

    @Inject
    public SharedPreferencesManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

//    public void saveSessionToken(AccessToken accessToken){
//        if(accessToken != null) {
//            String tokenJson = new Gson().toJson(accessToken);
//
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString(SESSION_TOKEN, tokenJson);
//            editor.apply();
//        }
//    }
//
//    public AccessToken getAccessToken(){
//        String accessTokenJson = sharedPreferences.getString(SESSION_TOKEN, "");
//
//        if(!accessTokenJson.isEmpty()){
//            return new Gson().fromJson(accessTokenJson, AccessToken.class);
//        } else {
//            return null;
//        }
//    }

//    public boolean isLoggedIn(){
//        return !sharedPreferences.getString(SESSION_TOKEN, "").isEmpty();
//    }

//    public void setIsLoggedIn(boolean isLoggedIn){
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
//
//        if(!isLoggedIn){
//            editor.putString(SESSION_TOKEN, "");
//        }
//
//        editor.apply();
//    }


}
