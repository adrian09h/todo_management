package com.nuasolutions.todomanagement.data.local.entity;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(primaryKeys = ("id"))
public class TodoItemEntity implements Parcelable{
    @SerializedName("id")
    public Long id;

    @SerializedName("name")
    public String name;

    @SerializedName("created_at")
    public String created_at;

    @SerializedName("updated_at")
    public String updated_at;

    @SerializedName("todo_id")
    public long todo_id;

    @SerializedName("done")
    public Boolean done;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public long getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(long todo_id) {
        this.todo_id = todo_id;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeByte((byte) (this.done ? 1 : 0));
        dest.writeLong(this.todo_id);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
    }

    public TodoItemEntity() {

    }

    public TodoItemEntity(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.done = in.readByte() != 0;
        this.todo_id = in.readLong();
        this.created_at = in.readString();
        this.updated_at = in.readString();

    }

    public static final Parcelable.Creator<TodoItemEntity> CREATOR = new Parcelable.Creator<TodoItemEntity>() {
        @Override
        public TodoItemEntity createFromParcel(Parcel in) {
            return new TodoItemEntity(in);
        }

        @Override
        public TodoItemEntity[] newArray(int size) {
            return new TodoItemEntity[size];
        }
    };


}
