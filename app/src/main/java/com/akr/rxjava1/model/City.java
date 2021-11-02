package com.akr.rxjava1.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "city_tbl")
public class City implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "code")
    private String code;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "active")
    private int active;
    @ColumnInfo(name = "insertby")
    private int insertedBy;
    @ColumnInfo(name = "updateby")
    private String insertedOn;
    @ColumnInfo(name = "districtid")
    private String districtID;


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

}
