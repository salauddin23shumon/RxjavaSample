package com.akr.rxjava1.model.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.akr.rxjava1.model.City;

@Database(entities = {City.class}, version = 1, exportSchema = false)
public abstract class AppDb  extends RoomDatabase {
    
    public static AppDb appDb;
    public static String Database_Name = "app_db";

    public static synchronized AppDb getInstance(Context context) {
        if (appDb == null) {
            appDb = Room.databaseBuilder(context.getApplicationContext(),
                    AppDb.class, Database_Name)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return appDb;
    }

    public abstract CityDao dao();      //abstract method of type SensorDao

}
