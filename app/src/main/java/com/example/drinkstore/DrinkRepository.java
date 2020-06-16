package com.example.drinkstore;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DrinkRepository {
    private DrinkDao drinkDao;
    private LiveData<List<Drink>> drinks;

    public DrinkRepository(Application application){
        DrinkDatabase db = DrinkDatabase.getDatabase(application);
        drinkDao = db.drinkDao();
        drinks = drinkDao.getAllDrinks();
    }

    public LiveData<List<Drink>> getAllDrinks(){return drinks;}
    public void insert(Drink drink){new insertAsync(drinkDao).execute(drink);}
    public void deleteAll(){new deleteAllAsync(drinkDao).execute();}
    public void deleteDrink(Drink drink){new deleteDrinkAsync(drinkDao).execute(drink);}



    private static class insertAsync extends AsyncTask<Drink,Void,Void>{

        private DrinkDao dao;

        public insertAsync(DrinkDao dao){this.dao = dao;}

        @Override
        protected Void doInBackground(Drink... drinks) {
            dao.insert(drinks[0]);
            return null;
        }
    }
    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private DrinkDao  dao;

        public deleteAllAsync(DrinkDao dao){this.dao =dao;}

        @Override
        protected Void doInBackground(Void... voids) {
            dao.nukeDb();
            return null;
        }
    }
    private static class deleteDrinkAsync extends AsyncTask<Drink,Void,Void>{
        private DrinkDao dao;

        public deleteDrinkAsync(DrinkDao dao){this.dao = dao;}

        @Override
        protected Void doInBackground(Drink... drinks) {
            dao.delete(drinks[0]);
            return null;
        }
    }

}
