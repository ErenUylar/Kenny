package com.erenuylar.kenny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Start extends AppCompatActivity {

    TextView Highscore;
    SharedPreferences sharedPreferences;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Highscore = findViewById(R.id.textHighscore);
        sharedPreferences = this.getSharedPreferences("com.erenuylar.kenny", Context.MODE_PRIVATE);

        int Hscore = sharedPreferences.getInt("HighScore", 0);
        if (Hscore == 0) {
            Highscore.setText("Skor: " + 0);
        } else {
            Highscore.setText("Skor: " + Hscore);
        }
    }

    public void StartGame(View view) {

        int gameLevel = sharedPreferences.getInt("Game", 1);
        switch (gameLevel) {
            case 1:
                intent = new Intent(this, Game1.class);
                break;
            case 2:
                intent = new Intent(this, Game2.class);
                break;
            case 3:
                intent = new Intent(this, Game3.class);
                break;
            default:
                intent = new Intent(this, Gamefinish.class);
                break;
        }

        startActivity(intent);
        overridePendingTransition(R.anim.sag, R.anim.sol);
    }
}