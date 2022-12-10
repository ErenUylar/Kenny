package com.erenuylar.kenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Game3 extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView Timetext;
    TextView Scoretext;
    TextView Downtext;
    ImageView kenny;
    Runnable runnable;
    Handler handler;
    int score;
    int screenX;
    int screenY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game3);

        sharedPreferences = this.getSharedPreferences("com.erenuylar.kenny", Context.MODE_PRIVATE);
        Toast.makeText(this, "3.Bölüm", Toast.LENGTH_SHORT).show();

        Timetext = findViewById(R.id.textTime);
        Scoretext = findViewById(R.id.textScore);
        Downtext = findViewById(R.id.textDown);
        kenny = findViewById(R.id.imageKenny);

        kenny.setVisibility(View.INVISIBLE);
        Scoretext.setVisibility(View.INVISIBLE);
        Timetext.setVisibility(View.INVISIBLE);

        score = 0;

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        screenX = point.x;
        screenY = point.y;

        AlertDialog.Builder builder = new AlertDialog.Builder(Game3.this);
        builder.setTitle("Bölüm Kuralları");
        builder.setMessage("Kenny'yi En Az 30 Defa Yakalayın");
        builder.setPositiveButton("Oyunu Başlat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CountDownTimer countDownTimer = new CountDownTimer(4000, 1000) {
                    @Override
                    public void onTick(long l) {
                        Downtext.setText("" + l / 1000);
                    }

                    @Override
                    public void onFinish() {
                        Downtext.setVisibility(View.INVISIBLE);
                        Startgames();
                        CountDownTimer countDownTimer1 = new CountDownTimer(25000, 1000) {
                            @Override
                            public void onTick(long l) {
                                Timetext.setText("Kalan Zaman: " + l / 1000);
                            }

                            @Override
                            public void onFinish() {
                                Timetext.setVisibility(View.INVISIBLE);
                                Scoretext.setVisibility(View.INVISIBLE);
                                kenny.setVisibility(View.INVISIBLE);
                                handler.removeCallbacks(runnable);

                                int Highscore = sharedPreferences.getInt("HighScore", 0);
                                if (Highscore < score) {
                                    sharedPreferences.edit().putInt("HighScore", score).apply();
                                }

                                if (score >= 30) {
                                    sharedPreferences.edit().putInt("Game", 4).apply();
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Game3.this);
                                    builder1.setTitle("Bölüm Geçildi");
                                    builder1.setMessage("Skorunuz: " + score);
                                    builder1.setPositiveButton("Sonraki Bölüm", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(Game3.this, Gamefinish.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.sag, R.anim.sol);
                                        }
                                    });
                                    builder1.setNegativeButton("Ana Sayfa", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(Game3.this, Start.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.sag, R.anim.sol);
                                        }
                                    });
                                    builder1.show();
                                } else {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(Game3.this);
                                    alert.setTitle("Bölüm Geçilemedi");
                                    alert.setMessage("Skorunuz: " + score);
                                    alert.setPositiveButton("Yeniden Dene", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = getIntent();
                                            finish();
                                            startActivity(intent);
                                        }
                                    });
                                    alert.setNegativeButton("Ana Sayfa", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(Game3.this, Start.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.sag, R.anim.sol);
                                        }
                                    });
                                    alert.show();
                                }
                            }
                        }.start();
                    }
                }.start();
            }
        });
        builder.setNegativeButton("Ana Sayfa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Game3.this, Start.class);
                startActivity(intent);
                overridePendingTransition(R.anim.sag, R.anim.sol);
            }
        });
        builder.show();
    }

    public void catchKenny(View view) {
        score++;
        Scoretext.setText("Skor: " + score);
    }

    public void Startgames() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                kenny.setVisibility(View.VISIBLE);
                Scoretext.setVisibility(View.VISIBLE);
                Timetext.setVisibility(View.VISIBLE);

                float randX = new Random().nextInt(screenX - kenny.getLayoutParams().width);
                float randY = ThreadLocalRandom.current().nextInt(150, screenY - (2 * kenny.getLayoutParams().height + 100));

                kenny.setX(randX);
                kenny.setY(randY);

                handler.postDelayed(this, 470);
            }
        };
        handler.post(runnable);
    }
}