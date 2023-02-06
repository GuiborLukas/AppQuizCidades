package com.example.appquizcidades;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import java.io.InputStream;
import java.net.URL;

public class BackgroundTask extends AsyncTask<String, Void, Bitmap> {
    ImageView output;
    ProgressDialog progress;

    public BackgroundTask(ImageView output, ProgressDialog progress) {
        this.output = output;
        this.progress = progress;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progress.setMessage("Buscando cidade...");
        this.progress.setCancelable(false);
        this.progress.show();
    }

    @Override
    protected void onPostExecute(Bitmap img) {
        super.onPostExecute(img);
        this.progress.dismiss();
        this.output.setImageBitmap(img);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String stringURL = strings[0];
        Bitmap img = null;
        try {
            URL url = new URL(stringURL);
            InputStream inputStream = url.openStream();
            img = BitmapFactory.decodeStream(inputStream);
            Log.println(1, "IMG content", img.toString());
        } catch (Exception e) {
            Log.e("GET", "doInBackground: GET IMAGEM - " + e.getMessage(), e);
            e.printStackTrace();
        }
        return img;
    }
}