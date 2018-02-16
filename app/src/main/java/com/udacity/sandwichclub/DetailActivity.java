package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView originTv;
    TextView originTitleTv;
    TextView akaTv;
    TextView akaTitleTv;
    TextView ingredientsTv;
    TextView descriptionTv;
    TextView descriptionTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        akaTv = findViewById(R.id.also_known_tv);
        akaTitleTv = findViewById(R.id.aka_title_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        descriptionTv = findViewById(R.id.description_tv);
        originTitleTv = findViewById(R.id.origin_title_tv);
        descriptionTitleTv = findViewById(R.id.description_title_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        List<String> ingredientsArray = sandwich.getIngredients();
        List<String> akaArray = sandwich.getAlsoKnownAs();
        String origin = sandwich.getPlaceOfOrigin();
        String description = sandwich.getDescription();
        int ingredientLineBreaks = 0;
        int akaLinebreaks = 0;
        for (String ingredient : ingredientsArray){
            ingredientsTv.append("-" + ingredient + "\n");
            ingredientLineBreaks += 1;
        }
        if (akaArray.isEmpty()){
            akaTitleTv.setVisibility(View.INVISIBLE);
            akaTv.setVisibility(View.INVISIBLE);
        } else {
            for (String aka : akaArray) {
                akaTv.append(aka + "\n");
                akaLinebreaks += 1;
            }
        }
        if (TextUtils.isEmpty(origin)){
            originTitleTv.setVisibility(View.INVISIBLE);
            originTv.setVisibility(View.INVISIBLE);
        }else {
            originTv.setText(origin);
            akaLinebreaks += 1;
        }
        descriptionTv.setText(description);
        if (akaLinebreaks > ingredientLineBreaks){
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) descriptionTitleTv.getLayoutParams();
            params.topToBottom = R.id.also_known_tv;
        }
    }
}
