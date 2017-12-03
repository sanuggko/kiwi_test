package ua.sng.kiwitest.model.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileModel implements Serializable{

    @SerializedName("id")
    private String profileId;

    @SerializedName("name")
    private String fullName;

    @SerializedName("birthday")
    private String birthDay;

    @SerializedName("gender")
    private String gender;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getFullName() {
        return fullName != null ? fullName : "";
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDay() {
        return birthDay != null ? birthDay : "";
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
