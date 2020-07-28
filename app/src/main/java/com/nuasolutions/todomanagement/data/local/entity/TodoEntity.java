package com.nuasolutions.todomanagement.data.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(primaryKeys = ("id"))
@TypeConverters(TodoItemTypeConverter.class)
public class TodoEntity implements Parcelable {
    @SerializedName("id")
    private Long id;

    @SerializedName("title")
    private String title;

    @SerializedName("created_by")
    private String created_by;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("items")
    @Expose
    @TypeConverters(TodoItemTypeConverter.class)
    private List<TodoItemEntity> itemList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List<TodoItemEntity> getItemList() {
        return itemList;
    }

    public void setItemList(List<TodoItemEntity> itemList) {
        this.itemList = itemList;
    }

    public TodoEntity() {
        this.itemList = new ArrayList<>();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.created_by);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeTypedList(this.itemList);
    }

    public TodoEntity(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.created_by = in.readString();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.itemList = in.createTypedArrayList(TodoItemEntity.CREATOR);
    }

    public static final Creator<TodoEntity> CREATOR = new Creator<TodoEntity>() {
        @Override
        public TodoEntity createFromParcel(Parcel in) {
            return new TodoEntity(in);
        }

        @Override
        public TodoEntity[] newArray(int size) {
            return new TodoEntity[size];
        }
    };

}
