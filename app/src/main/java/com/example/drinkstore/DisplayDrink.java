package com.example.drinkstore;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import org.w3c.dom.Text;

        import java.util.ArrayList;
        import java.util.List;

public class DisplayDrink extends AppCompatActivity {

    private TextView drinkNameView;
    private TextView drinkDescriptionView;
    private LinearLayout ingredientsLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_display_drink);


            drinkNameView =  findViewById(R.id.drink_name_header);
            drinkDescriptionView = findViewById(R.id.drink_description);
            ingredientsLayout = findViewById(R.id.ingredients_drink_view);

            //get drink intent data
            Intent sentIntent = getIntent();
            String drinkName = sentIntent.getStringExtra("drinkName");
            String drinkDescription = sentIntent.getStringExtra("drinkDescription");
            String drinkIngredients = sentIntent.getStringExtra("drinkIngredients");

            List<String> ingredientsList= new ArrayList<>();
            String[] splitString = drinkIngredients.split(",");
            String buffer = "";
            int count = 1;
            for (int i=0;i<splitString.length;i++){
            //if third item on the list
                if (count%3==0){
                    buffer = buffer + splitString[i];
                    ingredientsList.add(buffer);
                    buffer = "";
                } else {

                    buffer = buffer + splitString[i] + " ";
                }
                count++;
            }

            drinkNameView.setText(drinkName);
            if (drinkDescription == null){
                drinkDescriptionView.setVisibility(View.INVISIBLE);
                Log.d("here","here");
            }else {
                drinkDescriptionView.setText(drinkDescription);
            }
            for (String ingredient:ingredientsList){
                TextView tx = new TextView(this);
                tx.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tx.setText(ingredient);
                ingredientsLayout.addView(tx);
            }

        }
}