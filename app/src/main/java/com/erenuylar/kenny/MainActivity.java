package com.erenuylar.kenny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gameStart(View view){
        Intent intent=new Intent(this,MainActivity2.class);
        startActivity(intent);
        overridePendingTransition(R.anim.sag,R.anim.sol);
    }
}