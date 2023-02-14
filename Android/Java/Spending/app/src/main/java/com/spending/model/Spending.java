package com.spending.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Spending implements Parcelable{
    public String title;
    public int category, nominal;
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeInt(nominal);
        parcel.writeInt(category);
    }
    public Spending(){}
    protected Spending(Parcel in) {
        this.title=in.readString();
        this.nominal=in.readInt();
        this.category=in.readInt();
    }
    public static final Creator<Spending> CREATOR = new Creator<Spending>() {
        @Override
        public Spending createFromParcel(Parcel in) {
            return new Spending(in);
        }

        @Override
        public Spending[] newArray(int size) {
            return new Spending[size];
        }
    };
}
