package com.example.drinkstore;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {


    private LayoutInflater inflater;
    private List<Drink> drinks;
    private Context context;


    public DrinkAdapter(Context context,List<Drink> drinks){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.drinks = drinks;
    }


    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recycler_view_drink,parent,false);
        return new DrinkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkAdapter.DrinkViewHolder holder, int position) {
        if(drinks != null){
        Drink currentDrink = drinks.get(position);
        holder.bindTo(currentDrink);
        }else{
            holder.drinkName.setText("no data");
        }
    }


    public void setDrinks(List<Drink> drinks){
        this.drinks = drinks;
        notifyDataSetChanged();
    }

    public void swap(int from,int to){
        Collections.swap(drinks,from,to);
    }

    public Drink getDrinkAtPosition(int pos){
        return drinks.get(pos);
    }
    @Override
    public int getItemCount() {
        if (drinks != null){
            return drinks.size();
        } else {return 0;}
    }


    class DrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView drinkName;
        private TextView ingredientNumber;
        private ImageView cocktailImage;


        public DrinkViewHolder(View itemView) {
            super(itemView);
            drinkName = itemView.findViewById(R.id.drink_name);
            ingredientNumber = itemView.findViewById(R.id.amount_of_ingredients);
            cocktailImage = itemView.findViewById(R.id.cocktailImage);
            itemView.setOnClickListener(this);
        }
        void bindTo(Drink currentDrink){
            drinkName.setText(currentDrink.getName());
            int ingredN = (currentDrink.getIngredients().split(",").length)/3;
            ingredientNumber.setText(Integer.toString(ingredN));


        }

        @Override
        public void onClick(View v) {
            Drink d = drinks.get(getAdapterPosition());
            Log.d("adapter","touched");
            Intent showDrink = new Intent(context,DisplayDrink.class);
            showDrink.putExtra("drinkName",d.getName());
            showDrink.putExtra("drinkIngredients",d.getIngredients());
            showDrink.putExtra("drinkDescription",d.getDescription());
            context.startActivity(showDrink);

        }
    }
}
