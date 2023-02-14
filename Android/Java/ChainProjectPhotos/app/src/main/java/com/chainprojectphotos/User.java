package com.chainprojectphotos;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    public String id;
    public String email;
    public String name;
    public String password;
    public String address;
    public String phone;
    public String gender;
    public String bod;
    public User(String id,String email, String name, String password, String gender, String address, String phone, String bod) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.bod = bod;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    protected User(Parcel in) {
        id = in.readString();
        email = in.readString();
        name = in.readString();
        password = in.readString();
        phone = in.readString();
        gender = in.readString();
        bod = in.readString();
        address = in.readString();
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(password);
        parcel.writeString(phone);
        parcel.writeString(gender);
        parcel.writeString(bod);
        parcel.writeString(address);
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBod() {
        return bod;
    }

    public void setBod(String bod) {
        this.bod = bod;
    }
}

