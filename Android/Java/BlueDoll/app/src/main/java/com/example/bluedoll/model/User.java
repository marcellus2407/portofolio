package com.example.bluedoll.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    int gender; // 0 = male, 1 = female
    String id,name, password, role,email;
    public User(String id, String name, String password, String email, int gender, String role){
        this.id = id;
        this.name=name;
        this.password=password;
        this.email=email;
        this.gender=gender;
        this.role=role;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    protected User(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        password = in.readString();
        role = in.readString();
        gender = in.readInt();
    }
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(role);
        parcel.writeInt(gender);
    }
    public String getId() {
        return id;
    }

    public int getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }
}
