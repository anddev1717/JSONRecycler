package com.adham.jsonrecycler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// HTTP connection , JSON & image parsing , AsyncTask & Thread , ProgressDialog .

// Connecting to HTTP site and Converting input stream to String then send the string with the intent to the recycler activity to parse the JSON String
// to array list of contacts to shown in the recycler view
//Using Progress dialogue in Async threads during remote connection

public class MainActivity extends AppCompatActivity {
    String response;
    ProgressDialog pd;
    Bitmap bitmap;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.iv);
    }

// AsyncTask Can access UI through onPreExec , onPostExec and  onProgressUpdate through publishProgress from doInBackground
//Thread cant access UI
// AsyncTask run with execute() and Thread run with start()

    public void connect(View view) {
        new Connect().execute();
        new MyThread().start();
    }

    public void open(View view) {
        new GetImage().execute();
    }


    class Connect extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Connecting");
            pd.setMessage("Connecting to remote site to get data ...");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://api.androidhive.info/contacts/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                response = convertToString(inputStream);
                   Log.d("Response", response);
            } catch (IOException e) {
                   Log.d("Response",e.getMessage());
            }
            return null;
            }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent in = new Intent(MainActivity.this ,ContactsRecyclerActivity.class);
            in.putExtra("con",response);
            startActivity(in);
            pd.dismiss();
        }
        }

        private String convertToString(InputStream inputStream) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        }

    class GetImage extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Please wait");
            pd.setMessage("Getting Image from remote web site");
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setProgress(0);
            pd.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://lh3.googleusercontent.com/jN9tX6dCJ6_XL9E4K1KCO2Tuwe9_rYUbwv723eu6XGI0PWGLcPs0259VscOu249PPKKcU5AOXrq6JnleEaoK6K_JvZ2PY9lw3pMApzOpTQ=s660");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }

            int i = 0;
            while (true) {
                i++;
                i++;
                try {
                    Thread.sleep(1000);
                    pd.setProgress(i * 10);
                    publishProgress(i);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
                Log.d("Count", i + " Async");
                if (i == 10) {
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            Toast.makeText(MainActivity.this, "" + (values[0] * 10), Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            imageView.setImageBitmap(bitmap);
        }

    }

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            int i = 0;
            while (true) {
                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("Count", i + " Thread");
                if (i == 10) {
                    break;

                }
            }
        }
    }



}





