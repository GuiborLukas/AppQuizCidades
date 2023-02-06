package com.example.appquizcidades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View view) {
        HashMap<String, String> cidades = new HashMap<>();
        cidades.put("Barcelona", "01_barcelona.jpg");
        cidades.put("Brasília", "02_brasilia.jpg");
        cidades.put("Curitiba", "03_curitiba.jpg");
        cidades.put("Las Vegas", "04_lasvegas.jpg");
        cidades.put("Montreal", "05_montreal.jpg");
        cidades.put("Paris", "06_paris.jpg");
        cidades.put("Rio de Janeiro", "07_riodejaneiro.jpg");
        cidades.put("Salvador", "08_salvador.jpg");
        cidades.put("São Paulo", "09_saopaulo.jpg");
        cidades.put("Tóquio", "10_toquio.jpg");
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra("cidades", cidades);
        startActivity(intent);
    }

}