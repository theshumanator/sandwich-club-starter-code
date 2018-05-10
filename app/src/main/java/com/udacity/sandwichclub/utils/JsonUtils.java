package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        final String NAME="name";
        final String MAIN_NAME="mainName";
        final String ALSO_KNOWN_AS="alsoKnownAs";
        final String PLACE_OF_ORIGIN="placeOfOrigin";
        final String DESCRIPTION="description";
        final String IMAGE="image";
        final String INGREDIENTS="ingredients";
        Sandwich sandwich=new Sandwich();


        JSONObject sandwichInfo=new JSONObject(json);
        //aka
        JSONObject sandwichNames=sandwichInfo.getJSONObject(NAME);
        sandwich.setMainName(sandwichNames.getString(MAIN_NAME));
        JSONArray otherNames=sandwichNames.getJSONArray(ALSO_KNOWN_AS);
        List<String> otherSandwichNames=new ArrayList<String>();
        for (int i=0; i<otherNames.length(); i++) {
            otherSandwichNames.add(otherNames.getString(i));
        }
        sandwich.setAlsoKnownAs(otherSandwichNames);

        sandwich.setPlaceOfOrigin(sandwichInfo.getString(PLACE_OF_ORIGIN));
        sandwich.setDescription(sandwichInfo.getString(DESCRIPTION));
        sandwich.setImage(sandwichInfo.getString(IMAGE));

        JSONArray sandwichIngredients = sandwichInfo.getJSONArray(INGREDIENTS);
        List<String>ingredients=new ArrayList<String>();
        for (int i=0; i<sandwichIngredients.length();i++) {
            ingredients.add(sandwichIngredients.getString(i));
        }
        sandwich.setIngredients(ingredients);

        return sandwich;
    }
}