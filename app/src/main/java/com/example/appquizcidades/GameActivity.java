package com.example.appquizcidades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.HashMap;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;

public class GameActivity extends AppCompatActivity {
    ImageView imageViewCity;
    TextView textViewFeedback, textViewQuestion;
    EditText editTextAnswer;
    Button buttonSend, buttonNext;
    HashMap<String, String> cidades;
    String cidade;
    int cont, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        imageViewCity = findViewById(R.id.imageViewCity);
        textViewFeedback = findViewById(R.id.textViewFeedback);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        buttonSend = findViewById(R.id.buttonSend);
        buttonNext = findViewById(R.id.buttonNext);
        editTextAnswer = findViewById(R.id.editTextAnswer);

        cont = 0;
        score = 0;
        Intent i = getIntent();
        cidades = (HashMap<String, String>) i.getSerializableExtra("cidades");
        if (cidades == null) {
            Toast.makeText(this, "Falha na busca de cidades", Toast.LENGTH_SHORT).show();
            finish();
        }
        cidade = randomCity(cidades);
    }

    public String randomCity(@NonNull HashMap<String, String> cidades) {
        Object[] arrayKeys = cidades.keySet().toArray();
        Object key = arrayKeys[new Random().nextInt(arrayKeys.length)];
        String cidade = key.toString();
        String imagem = cidades.get(key.toString());
        ProgressDialog progressDialog = new ProgressDialog(this);
        String url = "http://31.220.51.31/ufpr/cidades/" + imagem;
        BackgroundTask task = new BackgroundTask(imageViewCity, progressDialog);
        task.execute(url);
        cont++;
        Log.i("CIDADE", "randomCity: " + cidade);
        return cidade;
    }

    public void checkAnswer(View view) {
        String mensagem = "";
        int msgColor;

        if (editTextAnswer.length() == 0) {
            Toast.makeText(this, "Digite antes de enviar", Toast.LENGTH_SHORT).show();
            return;
        }

        String answer = editTextAnswer.getText().toString();

        if (normalizeAndCompare(answer, cidade)) {
            score += 25;
            mensagem = "Resposta Correta!";
            msgColor = R.color.green;
        } else {
            mensagem = "Resposta Errada!\nA resposta correta é: " + cidade;
            msgColor = R.color.red;
        }
        textViewQuestion.setVisibility(View.INVISIBLE);
        textViewFeedback.setText(mensagem);
        textViewFeedback.setTextColor(ContextCompat.getColor(getApplicationContext(), msgColor));
        textViewFeedback.setVisibility(View.VISIBLE);
        editTextAnswer.setEnabled(false);
        editTextAnswer.setVisibility(View.INVISIBLE);
        buttonSend.setEnabled(false);
        buttonSend.setVisibility(View.INVISIBLE);
        buttonNext.setEnabled(true);
        buttonNext.setVisibility(View.VISIBLE);
    }


    public void nextQuestion(View view) {
        if (cont == 3) {
            buttonNext.setText("Finalizar");
        }
        if (cont >= 4) {
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("score", score);
            bundle.putSerializable("cidades", cidades);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else {
            buttonNext.setEnabled(false);
            buttonNext.setVisibility(View.INVISIBLE);
            editTextAnswer.setEnabled(true);
            editTextAnswer.setText("");
            editTextAnswer.setVisibility(View.VISIBLE);
            textViewFeedback.setText("");
            textViewFeedback.setVisibility(View.INVISIBLE);
            textViewQuestion.setVisibility(View.VISIBLE);
            buttonSend.setEnabled(true);
            buttonSend.setVisibility(View.VISIBLE);
            cidade = randomCity(cidades);
        }

    }

    public boolean normalizeAndCompare(String answer, String base){
        answer = StringUtils.stripAccents(answer);
        base = StringUtils.stripAccents(base);
        if(answer.equalsIgnoreCase(base)){
            return true;
        }
        return false;
    }
}