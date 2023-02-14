package com.chainprojectphotos;

import android.os.Parcel;
import android.os.Parcelable;

public class Tempat implements Parcelable {
    String imageurl, name;
    double lat,lng;
    Tempat(String imageurl, String name, Double lat, Double lng){
        this.imageurl = imageurl;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    protected Tempat(Parcel in) {
        imageurl = in.readString();
        name = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imageurl);
        parcel.writeString(name);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
    }
    public static final Creator<Tempat> CREATOR = new Creator<Tempat>() {
        @Override
        public Tempat createFromParcel(Parcel in) {
            return new Tempat(in);
        }

        @Override
        public Tempat[] newArray(int size) {
            return new Tempat[size];
        }
    };

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
