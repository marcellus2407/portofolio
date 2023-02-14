package com.example.bluedoll.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Doll implements Parcelable {
    private String name,description, creator, emailcreator;
    private int image;

    public Doll(String name,int image,String desc,String creator, String emailcreator){
        this.name=name;
        this.image=image;
        this.description=desc;
        this.creator=creator;
        this.emailcreator=emailcreator;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    protected Doll(Parcel in) {
        this.name=in.readString();
        this.image=in.readInt();
        this.description=in.readString();
        this.creator=in.readString();
        this.emailcreator=in.readString();
    }

    public static final Creator<Doll> CREATOR = new Creator<Doll>() {
        @Override
        public Doll createFromParcel(Parcel in) {
            return new Doll(in);
        }

        @Override
        public Doll[] newArray(int size) {
            return new Doll[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(image);
        parcel.writeString(description);
        parcel.writeString(creator);
        parcel.writeString(emailcreator);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public String getCreator() {
        return creator;
    }

    public String getEmailcreator() {
        return emailcreator;
    }
}

