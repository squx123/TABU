package com.example.tabu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tabu.Model.Tabu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Team2Screen extends AppCompatActivity {


    private int takim1Skor = 0;
    private int takim2Skor = 0;
    private TextView txtSure, txtKelime, txtYasakliKelime, txtTakimTag;
    private ImageButton btnDogru, btnPas, btnTabu;
    private Handler handler;
    private long startTime;
    private boolean isRunning = false;
    private Intent intent;
    private FirebaseFirestore store;
    private CollectionReference kelimeler;
    private final List<Tabu> tabuList = new ArrayList<>();
    private Tabu tabu;
    private int randomIndex;

    public void init() {
        txtSure = findViewById(R.id.sure);
        txtKelime = findViewById(R.id.kelime);
        txtYasakliKelime = findViewById(R.id.yasakKelimeler);
        txtTakimTag = findViewById(R.id.takimTag);
        btnDogru = findViewById(R.id.btnDogru);
        btnPas = findViewById(R.id.btnPas);
        btnTabu = findViewById(R.id.btnTabu);
        handler = new Handler();
        startTime = SystemClock.elapsedRealtime();
        store = FirebaseFirestore.getInstance();
        kelimeler = store.collection("kelimeler");
        randomIndex = 0;

        startTimer();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        intent = getIntent();
        takim1Skor = intent.getIntExtra("TAKIM1SKOR", 0);

        txtTakimTag.setText("TAKIM 2: ");

        getWordAndBannedHints();

        btnTabuOnclick();
        btnPasOnclick();
        btnDogruOnclick();
    }

    private void getWordAndBannedHints() {
        kelimeler.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                tabu = document.toObject(Tabu.class);
                                tabuList.add(tabu);
                            }
                            getNextWord();
                            startTimer();
                        } else {
                        }
                    }
                });
    }

    private void btnTabuOnclick() {
        btnTabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takim2Skor -= 1;
                txtTakimTag.setText("TAKIM 2: " + takim2Skor);
                getNextWord();
            }
        });
    }

    private void btnPasOnclick() {
        btnPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNextWord();
            }
        });
    }

    private void btnDogruOnclick() {
        btnDogru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takim2Skor += 1;
                txtTakimTag.setText("TAKIM 2: " + takim2Skor);
                Intent intent = new Intent(Team2Screen.this, AraActivity.class);
                intent.putExtra("TAKIM2SKOR", takim2Skor);

                getNextWord();
            }
        });
    }

    private void getNextWord() {
        if (randomIndex >= tabuList.size()) {
            randomIndex = 0;
        }

        txtKelime.setText(tabuList.get(randomIndex).getKelime());
        txtYasakliKelime.setText(tabuList.get(randomIndex).getYasakli());
        randomIndex++;
    }

    private void startTimer() {
        isRunning = true;
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (isRunning) {
                    long currentTime = SystemClock.elapsedRealtime();
                    long elapsedTime = currentTime - startTime;
                    long remainingTime = 10000 - elapsedTime;

                    if (remainingTime <= 0) {
                        stopTimer();
                        intent = new Intent(getApplicationContext(), AraActivity.class);
                        intent.putExtra("TAKIM2SKOR", takim2Skor);
                        intent.putExtra("TAKIM1SKOR", takim1Skor);
                        intent.putExtra("BUTTON", "BİTİR");
                        startActivity(intent);
                    } else {
                        int seconds = (int) (remainingTime / 1000);

                        String time = String.format("%02d", seconds);
                        txtSure.setText(time);

                        handler.postDelayed(this, 1000);
                    }
                }
            }
        }, 0);
    }

    private void stopTimer() {
        isRunning = false;
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();


    }
}