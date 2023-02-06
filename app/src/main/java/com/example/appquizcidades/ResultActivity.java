package com.example.appquizcidades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class ResultActivity extends AppCompatActivity {
    HashMap<String, String> cidades;

    TextView textViewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewScore = findViewById(R.id.textViewScore);

        Bundle bundle = getIntent().getExtras();

        int score = bundle.getInt("score", 0);

        cidades = (HashMap<String, String>) bundle.getSerializable("cidades");

        textViewScore.setText(String.valueOf(score));
        if (score <= 50) {
            textViewScore.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        } else {
            textViewScore.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        }

    }

    public void restartGame(View view) {
        Intent it = new Intent(getApplicationContext(), GameActivity.class);
        it.putExtra("cidades", cidades);
        startActivity(it);
        finish();
    }
}