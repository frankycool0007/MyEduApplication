package com.tecxpert.myeduapplication.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.tecxpert.myeduapplication.R;
import com.tecxpert.myeduapplication.model.Report;
import com.tecxpert.myeduapplication.utill.PrefManager;

import android.os.AsyncTask;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class BookActivity extends AppCompatActivity {

    PDFView pdfview;
    String subject_name,subject_key,topic_name,topic_url,topic_key;
    long startTime;
    long endTime;
    PrefManager prefManager;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        prefManager=PrefManager.getInstance(this);
       pdfview =(PDFView) findViewById(R.id.pdfView);
        subject_key=getIntent().getStringExtra("subject_key");
        subject_name=getIntent().getStringExtra("subject_name");
        topic_name=getIntent().getStringExtra("topic_name");
        topic_url=getIntent().getStringExtra("topic_url");
        topic_key=getIntent().getStringExtra("topic_key");
        startTime = System.currentTimeMillis();
        firebaseDatabase = FirebaseDatabase.getInstance();
        new RetrivePDFfromUrl().execute(topic_url);



    }

    @Override
    protected void onDestroy() {
        endTime = System.currentTimeMillis();
        long timeSpend = endTime - startTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd G 'at' HH mm ss z");
        String currentDateandTime = sdf.format(new Date());
        Report report=new Report(topic_name,subject_name,"Book",timeSpend);
        firebaseDatabase.getReference("report").child(prefManager.getuser().getEmail().substring(0,prefManager.getuser().getEmail().indexOf("@"))).child(currentDateandTime).setValue(report).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        super.onDestroy();
    }
    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch ( IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdfview.fromStream(inputStream).load();
        }
    }
}
