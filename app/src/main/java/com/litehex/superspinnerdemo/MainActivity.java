package com.litehex.superspinnerdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.litehex.superspinner.SuperSpinnerView;

public class MainActivity extends AppCompatActivity {

    String[] colors = {"red", "orange", "yellow", "green", "blue", "purple"};

    String[] arrCountryNames = {"Canada", "China", "France", "Germany", "South Korea", "Spain"};
    int[] arrCountryFlags = {R.drawable.canada, R.drawable.china, R.drawable.france,
            R.drawable.germany, R.drawable.southkorea, R.drawable.spain};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SuperSpinnerView ssvSpinnerCustom = findViewById(R.id.ssvSpinnerCustom);
        ssvSpinnerCustom.setup(colors);
        ssvSpinnerCustom.setOnItemSelectedListener(position -> ((TextView) findViewById(R.id.ssvSpinnerCustom_value)).setText(colors[position]));

        maxCustomization();

    }

    private void maxCustomization() {

        SuperSpinnerView ssvSpinner = findViewById(R.id.ssvSpinnerMaxCustom);

        ssvSpinner.setup(R.layout.layout_country_spinner_rows, arrCountryNames.length, (holder, position) -> {

            /* Setting names for per row */
            TextView txtName = holder.findViewById(R.id.country_name);
            txtName.setText(arrCountryNames[position]);

            /* Setting flags for per row */
            ImageView imvFlag = holder.findViewById(R.id.country_flag);
            imvFlag.setImageResource(arrCountryFlags[position]);

            /* Defining the divider view */
            View swv = holder.findViewById(com.litehex.superspinner.R.id.item_divider);

            /* Hiding last divider */
            if (position == arrCountryNames.length - 1) {
                swv.setVisibility(View.GONE);
            }

        });

        ssvSpinner.setOnItemSelectedListener(position -> {

            /* update chosen item in main view */
            TextView txtName = findViewById(R.id.ssvSpinnerMaxCustom_name);
            txtName.setText(arrCountryNames[position]);

            ImageView imvFlag = findViewById(R.id.ssvSpinnerMaxCustom_flag);
            imvFlag.setImageResource(arrCountryFlags[position]);

        });

        ssvSpinner.setOnShowingListener(() -> {

            /* making an animation when bottom sheet opening */
            ImageView imvArrow = findViewById(R.id.ssvSpinnerMaxCustom_arrow);
            ssvSpinner.makeAnimatedRotation(imvArrow, 500, 0.0f, -180.0f);

        });

        ssvSpinner.setOnDismissListener(() -> {

            /* making an animation when bottom sheet collapsing */
            ImageView imvArrow = findViewById(R.id.ssvSpinnerMaxCustom_arrow);
            ssvSpinner.makeAnimatedRotation(imvArrow, 500, -180.0f, 0.0f);

        });

    }

}