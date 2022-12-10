package com.erenuylar.kenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity2 extends AppCompatActivity {

    TextView textTime;
    TextView textScore;
    TextView textDown;
    ImageView kenny;
    Handler handler;
    Runnable runnable;
    int score;
    int screenX;
    int screenY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        score = 0;

        textTime = findViewById(R.id.textTime);
        textScore = findViewById(R.id.textScore);
        textDown = findViewById(R.id.textDown);
        kenny = findViewById(R.id.imageKenny);
        kenny.setVisibility(View.INVISIBLE);
        textTime.setVisibility(View.INVISIBLE);
        textScore.setVisibility(View.INVISIBLE);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenX = size.x; //1080
        screenY = size.y; //1794

        CountDownTimer countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                textDown.setText(""+l/1000);
            }

            @Override
            public void onFinish() {
                textDown.setVisibility(View.INVISIBLE);
                textTime.setVisibility(View.VISIBLE);
                textScore.setVisibility(View.VISIBLE);
                gameStart();
                CountDownTimer countDownTimer1=new CountDownTimer(10000,1000) {
                    @Override
                    public void onTick(long l1) {
                        textTime.setText("Kalan Zaman: "+l1/1000);
                    }

                    @Override
                    public void onFinish() {
                        textTime.setVisibility(View.INVISIBLE);
                        textScore.setVisibility(View.INVISIBLE);
                        kenny.setVisibility(View.INVISIBLE);
                        handler.removeCallbacks(runnable);
                        Toast.makeText(MainActivity2.this, "Süre Doldu!", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity2.this);
                        alert.setTitle("Süreniz Doldu");
                        alert.setMessage("Skorunuz: "+score+"\n"+"Yeniden Oynamak İstermisiniz?");
                        alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=getIntent();
                                finish();
                                startActivity(intent);
                            }
                        });
                        alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent2= new Intent(MainActivity2.this,MainActivity.class);
                                startActivity(intent2);
                                overridePendingTransition(R.anim.sag,R.anim.sol);
                            }
                        });
                        alert.show();
                    }
                }.start();
            }
        }.start();
    }

    public void catchKenny(View view) {
        score++;
        textScore.setText("Skor: " + score);
    }

    public void gameStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                kenny.setVisibility(View.VISIBLE);
                float rndmX = new Random().nextInt(screenX - kenny.getLayoutParams().width);
                float rndmY = ThreadLocalRandom.current().nextInt(140, screenY - (2 * kenny.getLayoutParams().height + 100));
                kenny.setX(rndmX);
                kenny.setY(rndmY);
                handler.postDelayed(this, 700);
            }
        };
        handler.post(runnable);
    }
}