package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    private JsonUtils(){}

    public static Sandwich parseSandwichJson(String json) {
        final String NAME = "name";
        final String MAIN_NAME = "mainName";
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String PLACE_OF_ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS = "ingredients";

        String mainName;

        // Got the idea to use an ArrayList from this StackOverflow question:
        // https://stackoverflow.com/questions/29559443/how-to-parse-json-array-into-an-android-list
        // used some insperation from the JSON Utility class in the Sunshine excersize 3.01

        ArrayList<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;

        ArrayList<String> ingredients = new ArrayList<>();

        try {
            JSONObject sandwichJSON = new JSONObject(json);
            JSONArray alsoKnownAsArray = sandwichJSON.getJSONObject(NAME).getJSONArray(ALSO_KNOWN_AS);
            JSONArray ingredientsArray = sandwichJSON.getJSONArray(INGREDIENTS);
            for (int i = 0; i < alsoKnownAsArray.length(); i++){
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }
            for (int i = 0; i < ingredientsArray.length(); i++){
                ingredients.add(ingredientsArray.getString(i));
            }
            mainName = sandwichJSON.getJSONObject(NAME).getString(MAIN_NAME);
            placeOfOrigin = sandwichJSON.getString(PLACE_OF_ORIGIN);
            description = sandwichJSON.getString(DESCRIPTION);
            image = sandwichJSON.getString(IMAGE);

        } catch (JSONException e){
            Log.e(TAG, "Invalid or empty JSONObject: " + json, e);
            return null;
        }
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
