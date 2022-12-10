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

public class Game1 extends AppCompatActivity {

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
        setContentView(R.layout.activity_game1);

        sharedPreferences = this.getSharedPreferences("com.erenuylar.kenny", Context.MODE_PRIVATE);
        Toast.makeText(this, "1.Bölüm", Toast.LENGTH_SHORT).show();

        Timetext = findViewById(R.id.textTime);
        Scoretext = findViewById(R.id.textScore);
        Downtext = findViewById(R.id.textDown);
        kenny = findViewById(R.id.imageKenny);

        kenny.setVisibility(View.INVISIBLE);
        Timetext.setVisibility(View.INVISIBLE);
        Scoretext.setVisibility(View.INVISIBLE);

        score = 0;

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        screenX = point.x;
        screenY = point.y;

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Bölüm Kuralları");
        alert.setMessage("Kenny'yi En Az 20 Defa Yakalayın");
        alert.setPositiveButton("Oyunu Başlat", new DialogInterface.OnClickListener() {
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

                                if (score >= 20) {
                                    sharedPreferences.edit().putInt("Game", 2).apply();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Game1.this);
                                    builder.setTitle("Bölüm Geçildi");
                                    builder.setMessage("Skorunuz: " + score);
                                    builder.setPositiveButton("Sonraki Bölüm", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(Game1.this, Game2.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.sag, R.anim.sol);
                                        }
                                    });
                                    builder.setNegativeButton("Ana Sayfa", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(Game1.this, Start.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.sag, R.anim.sol);
                                        }
                                    });
                                    builder.show();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Game1.this);
                                    builder.setTitle("Bölüm Geçilemedi");
                                    builder.setMessage("Skorunuz: " + score);
                                    builder.setPositiveButton("Yeniden Dene", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = getIntent();
                                            finish();
                                            startActivity(intent);
                                        }
                                    });
                                    builder.setNegativeButton("Ana Sayfa", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(Game1.this, Start.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.sag, R.anim.sol);
                                        }
                                    });
                                    builder.show();
                                }
                            }
                        }.start();
                    }
                }.start();
            }
        });
        alert.setNegativeButton("Ana Sayfa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Game1.this, Start.class);
                startActivity(intent);
                overridePendingTransition(R.anim.sag, R.anim.sol);
            }
        });
        alert.show();
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
                Timetext.setVisibility(View.VISIBLE);
                Scoretext.setVisibility(View.VISIBLE);

                float randX = new Random().nextInt(screenX - kenny.getLayoutParams().width);
                float randY = ThreadLocalRandom.current().nextInt(150, screenY - (2 * kenny.getLayoutParams().height + 100));

                kenny.setX(randX);
                kenny.setY(randY);

                handler.postDelayed(this, 550);
            }
        };
        handler.post(runnable);
    }
}