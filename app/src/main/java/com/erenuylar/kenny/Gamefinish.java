package com.erenuylar.kenny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Gamefinish extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamefinish);
        sharedPreferences=this.getSharedPreferences("com.erenuylar.kenny", Context.MODE_PRIVATE);
    }

    public void GameRemove(View view){
        sharedPreferences.edit().remove("HighScore").apply();
        sharedPreferences.edit().remove("Game").apply();
        Intent intent=new Intent(this,Start.class);
        startActivity(intent);
        overridePendingTransition(R.anim.sag,R.anim.sol);
    }
}