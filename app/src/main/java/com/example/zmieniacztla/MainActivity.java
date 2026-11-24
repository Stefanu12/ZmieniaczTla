
package com.example.zmieniacztla;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity {

    TextView tvText;
    ImageView bgImage;

    Button btnChangeText, btnChangeColor, btnChangeBg;

    SwitchCompat switchNight;

    String[] texts;

    int[] colors = {
            R.color.text_color,
            R.color.text_opt2,
            R.color.text_opt3
    };

    int[] backgrounds = {
            R.drawable.tlo1,
            R.drawable.tlo2
    };

    int textIndex = 0;
    int colorIndex = 0;
    int backgroundIndex = 0;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        preferences = getSharedPreferences("ustawienia", MODE_PRIVATE);

        boolean nightMode = preferences.getBoolean("nightMode", false);

        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
            );
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
            );
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tvText = findViewById(R.id.tvText);
        bgImage = findViewById(R.id.bgImage);

        btnChangeText = findViewById(R.id.btnChangeText);
        btnChangeColor = findViewById(R.id.btnChangeColor);
        btnChangeBg = findViewById(R.id.btnChangeBg);

        switchNight = findViewById(R.id.switchNight);


        texts = getResources().getStringArray(R.array.texts);

        tvText.setText(texts[0]);


        colorIndex = preferences.getInt("colorIndex", 0);

        if (colorIndex < 0 || colorIndex >= colors.length) {
            colorIndex = 0;
        }

        tvText.setTextColor(getColor(colors[colorIndex]));


        backgroundIndex = preferences.getInt("backgroundIndex", 0);

        if (backgroundIndex < 0 || backgroundIndex >= backgrounds.length) {
            backgroundIndex = 0;
        }

        bgImage.setImageResource(backgrounds[backgroundIndex]);


        switchNight.setChecked(nightMode);


        btnChangeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textIndex = (textIndex + 1) % texts.length;

                tvText.setText(texts[textIndex]);

            }
        });


        btnChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                colorIndex = (colorIndex + 1) % colors.length;

                tvText.setTextColor(getColor(colors[colorIndex]));

                preferences.edit()
                        .putInt("colorIndex", colorIndex)
                        .apply();

            }
        });


        btnChangeBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backgroundIndex = (backgroundIndex + 1) % backgrounds.length;

                bgImage.setImageResource(backgrounds[backgroundIndex]);

                preferences.edit()
                        .putInt("backgroundIndex", backgroundIndex)
                        .apply();

            }
        });


        switchNight.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(
                            CompoundButton buttonView,
                            boolean isChecked) {

                        preferences.edit()
                                .putBoolean("nightMode", isChecked)
                                .apply();

                        if (isChecked) {

                            AppCompatDelegate.setDefaultNightMode(
                                    AppCompatDelegate.MODE_NIGHT_YES
                            );

                        } else {

                            AppCompatDelegate.setDefaultNightMode(
                                    AppCompatDelegate.MODE_NIGHT_NO
                            );

                        }

                    }
                }
        );

    }
}
