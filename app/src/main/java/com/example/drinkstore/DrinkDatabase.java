package com.example.drinkstore;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;

@Database(entities = {Drink.class},version = 1,exportSchema = false)
public abstract class DrinkDatabase extends RoomDatabase {

    public abstract DrinkDao drinkDao();
    //to make sure you don'get two instances of a database
    public static DrinkDatabase INSTANCE;
    private static RoomDatabase.Callback roomDbCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };




    static DrinkDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (DrinkDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DrinkDatabase.class,"drink_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomDbCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }




    //add databse callback on create, to populate if empty
    private final static class PopulateDbAsync extends AsyncTask<Void,Void,Void>{
        private final DrinkDao dao;
        String[] drinkNames = {"Margarita","Mojito","Sex on the Beach","Pinacolada","superDrink","random drink ","dave meister","the destroyer","big popper"};
        public PopulateDbAsync(DrinkDatabase db){this.dao = db.drinkDao();}


        @Override
        protected Void doInBackground(Void... voids) {
            if (dao.getAnyDrink().length == 0){
                for (int i=0;i<drinkNames.length; i++){
                    Drink d = new Drink(drinkNames[i],"tequila,8,cl,sangria,9,L,water,5,ml");
                    dao.insert(d);
                    }
                }
            return null;
            }
        }
}
