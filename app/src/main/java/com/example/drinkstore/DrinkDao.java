package com.example.drinkstore;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DrinkDao {

    @Insert
    void insert(Drink drink);

    @Query("DELETE FROM drink_table")
    void nukeDb();

    @Query("SELECT * FROM drink_table")
    LiveData<List<Drink>> getAllDrinks();

    @Delete
    void delete(Drink drink);

    @Query("SELECT * FROM drink_table LIMIT 1 ")
    Drink[] getAnyDrink();



}
