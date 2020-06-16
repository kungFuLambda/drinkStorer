package com.example.drinkstore;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class AddDrinkActivity extends AppCompatActivity {

    List<Integer> ingredientIds;
    EditText drinkName;
    EditText drinkDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);

        drinkName = findViewById(R.id.drink_name_input);
        drinkDescription = findViewById(R.id.drink_description_input);
        ingredientIds = new ArrayList<>();

        final LinearLayout parentLayout = findViewById(R.id.add_ingedients_scroll);


        //button to add ingredients, and all the initiating for the creation of the ingredient stuff.
        final FloatingActionButton add_ingredient = findViewById(R.id.add_ingredient_button);
        final FloatingActionButton add_drink = findViewById(R.id.commit_drink_button);



        //add ingredient listener
        add_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initiate Constraint Layout, with padding
                ConstraintLayout new_ingredient = new ConstraintLayout(getApplicationContext());
                int newIngredientId = View.generateViewId();
                new_ingredient.setId(newIngredientId);
                ingredientIds.add(newIngredientId);
                new_ingredient.setPadding(10,10,10,10);


                //initiate parameters for height and width
                ViewGroup.MarginLayoutParams params = new ConstraintLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0,0,0,30);
                new_ingredient.setLayoutParams(params);


                int width = new_ingredient.getWidth();
                new_ingredient.setBackgroundResource(R.drawable.border);

                //ingredient name
                EditText ingredientName = new EditText(getApplicationContext());
                ingredientName.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,width));
                ingredientName.setId(View.generateViewId());
                ingredientName.setHint("Sauce name");
                ingredientName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                ingredientName.setHintTextColor(getResources().getColor(R.color.textColour));
                ingredientName.setTextColor(getResources().getColor(R.color.textColour));


                //ingredient amount
                EditText ingredientAmount = new EditText(getApplicationContext());
                ingredientAmount.setId(View.generateViewId());
                ingredientAmount.setTextSize(15);
                ingredientAmount.setHint("quantity");
                ingredientAmount.setHintTextColor(getResources().getColor(R.color.textColour));
                ingredientAmount.setTextColor(getResources().getColor(R.color.textColour));


                //ingredient units
                Spinner units = new Spinner(getApplicationContext());
                int spinnerId = View.generateViewId();
                units.setId(spinnerId);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.units_array,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                if (units != null){
                    units.setAdapter(adapter);
                }
                units.setSelection(0);


                //add created views to Layout
                new_ingredient.addView(ingredientName);
                new_ingredient.addView(ingredientAmount);
                new_ingredient.addView(units);

                //add constraint sets
                ConstraintSet set = new ConstraintSet();
                set.clone(new_ingredient);
                //ingredient start to start of parent
                set.connect(ingredientName.getId(),ConstraintSet.START,new_ingredient.getId(),ConstraintSet.START);
                //ingredient amount to end of name
                set.connect(ingredientAmount.getId(),ConstraintSet.TOP,ingredientName.getId(),ConstraintSet.BOTTOM,0);
                //ingredient units to end of amount
                set.connect(units.getId(),ConstraintSet.TOP,ingredientName.getId(),ConstraintSet.BOTTOM,30);
                set.connect(units.getId(),ConstraintSet.START,ingredientAmount.getId(),ConstraintSet.END,50);
                set.applyTo(new_ingredient);





                parentLayout.addView(new_ingredient,0);





            }
        });
        add_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredients = "";
                for (int i:ingredientIds){
                    ConstraintLayout l = findViewById(i);
                    int childCount = l.getChildCount();
                    EditText ing = (EditText) l.getChildAt(0);
                    EditText qty = (EditText) l.getChildAt(1);
                    Spinner s = (Spinner) l.getChildAt(2);
                    Log.d("here",ing.getText().toString() + " " + qty.getText().toString());
                    if (ing.getText().toString().length() != 0){
                        ingredients = ingredients + ing.getText().toString() + "," + qty.getText().toString() + "," + s.getSelectedItem().toString() + ",";
                    }


                }
                Log.d("here",ingredients);
                if (drinkName.getText().toString() == ""){
                    Toast.makeText(getApplicationContext(),"Sorry you didn't enter a name",Toast.LENGTH_SHORT).show();
                } else if(ingredients == ""){
                    Toast.makeText(getApplicationContext(),"Sorry you didn't enter any ingredients",Toast.LENGTH_SHORT).show();
                } else {
                    Intent returnHome = new Intent();
                    returnHome.putExtra("drinkName", drinkName.getText().toString());
                    returnHome.putExtra("drinkIngredients", ingredients);
                    returnHome.putExtra("drinkDescription",drinkDescription.getText().toString());
                    setResult(RESULT_OK, returnHome);
                    finish();
                }
            }


        });



    }
}