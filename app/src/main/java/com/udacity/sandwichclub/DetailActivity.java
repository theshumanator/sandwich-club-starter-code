package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        /*shumanator: I was having problems accessing the shawarma img (problem on the remote server?
            so I added the .error to make up for that and used the image from mipmap.
        */
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.ic_launcher_round)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView originTextView=findViewById(R.id.origin_tv);
        originTextView.setText(sandwich.getPlaceOfOrigin());

        TextView alsoKnownTextView=findViewById(R.id.also_known_tv);
        String alsoKnownAs="";
        List<String> alsoKnownAsList=sandwich.getAlsoKnownAs();
        for (int i=0; i<alsoKnownAsList.size(); i++) {
            alsoKnownAs+=alsoKnownAsList.get(i);
            if (i<sandwich.getAlsoKnownAs().size()-1) {
                alsoKnownAs+=",";
            }
        }
        alsoKnownTextView.setText(alsoKnownAs);

        TextView ingredientsTextView=findViewById(R.id.ingredients_tv);
        String ingredients="";
        List<String> ingredientsList=sandwich.getIngredients();
        for (int i=0; i<ingredientsList.size(); i++) {
            ingredients+=ingredientsList.get(i);
            if (i<sandwich.getIngredients().size()-1) {
                ingredients+=",";
            }
        }
        ingredientsTextView.setText(ingredients);

        TextView descriptionTextView=findViewById(R.id.description_tv);
        descriptionTextView.setText(sandwich.getDescription());
    }
}
