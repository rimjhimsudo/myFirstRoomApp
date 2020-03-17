package com.example.myroomsampleapp.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myroomsampleapp.model.Person;

@Database(entities = {Person.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    //why tag?
    private  static final  String LOG_TAG=AppDatabase.class.getSimpleName();
    private  static final String DATABASE_NAME="personlist";
    //why lock?
    private  static final Object LOCK= new Object();



    private  static AppDatabase sInstance;
    public static  AppDatabase getInstance(Context context){
        if(sInstance== null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating new instanvce of Database");
                sInstance= Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME).build();
            }
        }
        Log.d(LOG_TAG, "gettting started with db instance");
        return sInstance;
    }

    public  abstract PersonDao personDao();
}
