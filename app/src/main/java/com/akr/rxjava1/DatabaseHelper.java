package com.akr.rxjava1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.akr.rxjava1.model.City;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String UPAZILLA_TABLE = "upazilla_table";

    public static final String LOCAL_ID = "lid";
    public static final String MOBILE_ID = "mobile_id";
    public static final String SYNC_STATUS = "status";
    public static final String HOUSEHOLD_MASTER_ID = "household_master_id";
    public static final String ROUND_MASTER_ID = "round_master_id";
    public static final String MEMBER_MASTER_ID = "member_master_id";
    public static final String CODE_COLUMN = "code";
    public static final String NAME_COLUMN = "name";
    public static final String LOOKUP_MASTER_ID = "lookup_master_id";
    public static final String ACTIVE_COLUMN = "active";
    public static final String TRANSFER_COMPLETE = "transfer_complete";
    public static final String INSERT_BY = "inserted_by";
    public static final String INSERT_ON = "inserted_on";
    public static final String UPDATE_BY = "update_by";
    public static final String UPDATE_ON = "update_on";
    public static final String DISTRICT_ID_COLUMN = "district_id";

    private SQLiteDatabase db;

    public static final String CREATE_UPAZILLA_TABLE = "CREATE TABLE "
            + UPAZILLA_TABLE + "(" + LOCAL_ID + " INTEGER PRIMARY KEY, "
            + CODE_COLUMN + " TEXT, " + NAME_COLUMN + " TEXT, "
            + ACTIVE_COLUMN + " INTEGER ," + DISTRICT_ID_COLUMN + " TEXT, "
            + INSERT_BY + " INTEGER, " + INSERT_ON + " TEXT "
            + ")";

    public DatabaseHelper(Context context) {
        super(context, "RX_db", null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_UPAZILLA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertUpazillaData(City city) {


        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCAL_ID, city.getId());
        contentValues.put(CODE_COLUMN, city.getCode());
        contentValues.put(NAME_COLUMN, city.getName());
        contentValues.put(ACTIVE_COLUMN, city.getActive());
        contentValues.put(INSERT_ON, city.getInsertedOn());
        contentValues.put(INSERT_BY, city.getInsertedBy());
        contentValues.put(DISTRICT_ID_COLUMN, city.getDistrictID());
        long result = db.insert(UPAZILLA_TABLE, null, contentValues);
        return result;

    }

}
