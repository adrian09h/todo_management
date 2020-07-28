package com.nuasolutions.todomanagement.data.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;
@Entity(primaryKeys = ("id"))
public class AccessTokenEntity implements Parcelable {
    @SerializedName("id")
    private Long id;

    @SerializedName("auth_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accessToken);
        dest.writeLong(this.id);
    }

    public AccessTokenEntity() {

    }

    @Ignore
    public AccessTokenEntity(String accessToken) {
        this.id = 1L;
        this.accessToken = accessToken;
    }

    public AccessTokenEntity(Parcel in) {
        this.accessToken = in.readString();
        this.id = in.readLong();
    }

    public static final Creator<AccessTokenEntity> CREATOR = new Creator<AccessTokenEntity>() {
        @Override
        public AccessTokenEntity createFromParcel(Parcel in) {
            return new AccessTokenEntity(in);
        }

        @Override
        public AccessTokenEntity[] newArray(int size) {
            return new AccessTokenEntity[size];
        }
    };

}
