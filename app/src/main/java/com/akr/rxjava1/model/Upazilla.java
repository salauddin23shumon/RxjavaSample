package com.akr.rxjava1.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Upazilla implements Parcelable {
    private int id;
    private String code;
    private String name;
    private int active;
    private int insertedBy;
    private String insertedOn;
    private String districtID;


    protected Upazilla(Parcel in) {
        id = in.readInt();
        code = in.readString();
        name = in.readString();
        active = in.readInt();
        insertedBy = in.readInt();
        insertedOn = in.readString();
        districtID = in.readString();
    }

    public static final Creator<Upazilla> CREATOR = new Creator<Upazilla>() {
        @Override
        public Upazilla createFromParcel(Parcel in) {
            return new Upazilla(in);
        }

        @Override
        public Upazilla[] newArray(int size) {
            return new Upazilla[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(int insertedBy) {
        this.insertedBy = insertedBy;
    }

    public String getInsertedOn() {
        return insertedOn;
    }

    public void setInsertedOn(String insertedOn) {
        this.insertedOn = insertedOn;
    }

    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(code);
        dest.writeString(name);
        dest.writeInt(active);
        dest.writeInt(insertedBy);
        dest.writeString(insertedOn);
        dest.writeString(districtID);
    }
}
