package com.example.tabu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tabu.R;


public class AraActivity extends AppCompatActivity {

    private TextView btnAraBaslatBitir;
    private TextView takim1Puan;
    private TextView takim2Puan;

    private Intent intent;

    private ImageButton btnTakimBasla;
    private void init() {
        btnTakimBasla = findViewById(R.id.btnTakimBasla);
        takim1Puan = findViewById(R.id.takim1Puan);
        takim2Puan = findViewById(R.id.takim2Puan);
        btnAraBaslatBitir = findViewById(R.id.btnAraBaslatBitir);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ara);

        init();

        intent = getIntent();
        int takim1Skor = intent.getIntExtra("TAKIM1SKOR", 0);
        takim1Puan.setText(String.valueOf(takim1Skor));

        int takim2Skor = intent.getIntExtra("TAKIM2SKOR", 0);
        takim2Puan.setText(String.valueOf(takim2Skor));

        if(intent.hasExtra("BUTTON")){
            btnAraBaslatBitir.setText(intent.getStringExtra("BUTTON"));
        }
        else{

        }
        //start takım2 oynadıysa splashscreene oynamadıysa team2screene yönlendir
        if(intent.hasExtra("TAKIM2SKOR")){
            btnTakimBasla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{;
            btnTakimBasla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Team2Screen.class);
                    intent.putExtra("TAKIM1SKOR", takim1Skor);
                    startActivity(intent);
                }
            });
        }
        //stop

    }
}