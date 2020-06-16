package com.example.drinkstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Drink> drinks;
    public RecyclerView rv;
    public DrinkAdapter adapter;
    private DrinkViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        //get view model as middle man between adapter and database
        viewModel = ViewModelProviders.of(this).get(DrinkViewModel.class);
        viewModel.getAllDrinks().observe(this, new Observer<List<Drink>>() {
            @Override
            public void onChanged(List<Drink> drinks) {
                adapter.setDrinks(drinks);
            }
        });


        //inititae button and onclick listener
        FloatingActionButton fb = findViewById(R.id.add_drink_button);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_drink = new Intent(MainActivity.this,AddDrinkActivity.class);
                startActivityForResult(add_drink,69);
            }
        });


        //initiate recycler view, set manager, adapter
        rv = findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DrinkAdapter(this,drinks);
        rv.setAdapter(adapter);


        //initiate sqipe dirs
        int swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN |ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT | ItemTouchHelper.UP,swipeDirs) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                adapter.swap(from,to);
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                final Drink d = adapter.getDrinkAtPosition(position);
                AlertDialog.Builder checkIfDelete = new AlertDialog.Builder(MainActivity.this);
                checkIfDelete.setTitle("Are you sure you want to delete "+d.getName());
                checkIfDelete.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.deleteDrink(d);
                    }
                });
                checkIfDelete.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyDataSetChanged();
                    }
                });
                checkIfDelete.create().show();
            }
        });
        helper.attachToRecyclerView(rv);
    }




    //methods
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 69){
            String drinkName = data.getStringExtra("drinkName");
            String drinkIngredients = data.getStringExtra("drinkIngredients");
            String drinkDescription = data.getStringExtra("drinkDescription");
            Drink newDrink = new Drink(drinkName,drinkIngredients);
            newDrink.setDescription(drinkDescription);
            viewModel.insertDrink(newDrink);


        }
    }

    //initialize menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    //menu click listener
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.delete_all){
            AlertDialog.Builder checkWithUsers = new AlertDialog.Builder(MainActivity.this);
            checkWithUsers.setTitle("Are you sure you want to delete all drinks");
            checkWithUsers.setMessage("if you do this none of your drinks can be retrieved");
            checkWithUsers.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    viewModel.deleteAllDrinks();
                }
            });
            checkWithUsers.setNegativeButton("nope", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            checkWithUsers.create().show();
        }


        return super.onOptionsItemSelected(item);
    }

}