package com.akr.rxjava1.model;

import java.io.Serializable;

public class District implements Serializable {
    private int id;
    private String code;
    private String name;
    private int active;
    private int insertedBy;
    private String insertedOn;
    private String divisionID;

    public District(int id, String code, String name, int active, int insertedBy, String insertedOn,String divisionID) {
        super();
        this.id = id;
        this.code = code;
        this.name = name;
        this.active = active;
        this.insertedBy = insertedBy;
        this.insertedOn = insertedOn;
        this.divisionID = divisionID;
    }

    public District() {
        super();
    }

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

    public String getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(String divisionID) {
        this.divisionID = divisionID;
    }
}
