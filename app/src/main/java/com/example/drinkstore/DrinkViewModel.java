package com.example.drinkstore;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DrinkViewModel extends AndroidViewModel {

    private DrinkRepository repository;
    private LiveData<List<Drink>> drinks;


    public DrinkViewModel(@NonNull Application application) {
        super(application);
        this.repository = new DrinkRepository(application);
        this.drinks = repository.getAllDrinks();

    }



    public LiveData<List<Drink>> getAllDrinks(){return drinks;}
    public void insertDrink(Drink drink){repository.insert(drink);}
    public void deleteDrink(Drink drink){repository.deleteDrink(drink);}
    public void deleteAllDrinks(){repository.deleteAll();}
}
