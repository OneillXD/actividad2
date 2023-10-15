package com.example.actividad2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



    public class MainActivity extends Activity {

        private ImageView mImageView;
        private Button downloadButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mImageView = findViewById(R.id.imageView);
            downloadButton = findViewById(R.id.downloadButton);

            downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Inicia la tarea asincrónica para descargar y mostrar la imagen
                    new DownloadImageTask().execute("data:image/png;base64,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");
                }
            });
        }

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            @Override
            protected Bitmap doInBackground(String... urls) {
                String imageUrl = urls[0];
                Bitmap bitmap = null;

                try {
                    URL url = new URL(imageUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();

                    InputStream input = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(input);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                if (result != null) {
                    // Actualiza la ImageView con la imagen descargada
                    mImageView.setImageBitmap(result);
                } else {
                    // En caso de error, puedes manejarlo apropiadamente
                    // mImageView.setImageResource(R.drawable.error_image); // Esto es solo un ejemplo
                }
            }
        }
    }