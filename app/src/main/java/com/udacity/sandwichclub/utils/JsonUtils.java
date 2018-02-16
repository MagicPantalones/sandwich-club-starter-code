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



        // Got the idea to use an ArrayList from this StackOverflow question:
        // https://stackoverflow.com/questions/29559443/how-to-parse-json-array-into-an-android-list
        // used some insperation from the JSON Utility class in the Sunshine excersize 3.01

        ArrayList<String> alsoKnownAs = new ArrayList<>();
        ArrayList<String> ingredients = new ArrayList<>();
        String mainName = "";
        String placeOfOrigin = "";
        String description = "";
        String image = "";


        try {
            JSONObject sandwichJSON = new JSONObject(json);

            if (sandwichJSON.getJSONObject(NAME).has(ALSO_KNOWN_AS)) {
                JSONArray alsoKnownAsArray = sandwichJSON.getJSONObject(NAME).getJSONArray(ALSO_KNOWN_AS);
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    alsoKnownAs.add(alsoKnownAsArray.getString(i));
                }
            }
            if (sandwichJSON.has(INGREDIENTS)) {
                JSONArray ingredientsArray = sandwichJSON.getJSONArray(INGREDIENTS);
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    ingredients.add(ingredientsArray.optString(i));
                }
            }

            if (sandwichJSON.getJSONObject(NAME).has(MAIN_NAME)){mainName = sandwichJSON.getJSONObject(NAME).optString(MAIN_NAME);}
            if (sandwichJSON.has(PLACE_OF_ORIGIN)){placeOfOrigin = sandwichJSON.optString(PLACE_OF_ORIGIN);}
            if (sandwichJSON.has(DESCRIPTION)){description = sandwichJSON.optString(DESCRIPTION);}
            if (sandwichJSON.has(IMAGE)){image = sandwichJSON.optString(IMAGE);}

        } catch (JSONException e){
            Log.e(TAG, "Invalid or empty JSONObject: " + json, e);
            return null;
        }
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
