package com.tecxpert.myeduapplication.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.FirebaseDatabase;
import com.tecxpert.myeduapplication.R;
import com.tecxpert.myeduapplication.model.Report;
import com.tecxpert.myeduapplication.utill.PrefManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VedioActivity extends YouTubeBaseActivity {

    String api_key = "AIzaSyD96JrcD_0IZNWMqU2iI02m5vbgm8mdxF0";
    long startTime;
    long endTime;
    PrefManager prefManager;
    FirebaseDatabase firebaseDatabase;
    String subject_name,subject_key,topic_name,topic_url,topic_key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio);
        prefManager=PrefManager.getInstance(this);
        subject_key=getIntent().getStringExtra("subject_key");
        subject_name=getIntent().getStringExtra("subject_name");
        topic_name=getIntent().getStringExtra("topic_name");
        topic_url=getIntent().getStringExtra("topic_url");
        topic_key=getIntent().getStringExtra("topic_key");

        startTime = System.currentTimeMillis();
        firebaseDatabase = FirebaseDatabase.getInstance();
        YouTubePlayerView ytPlayer = (YouTubePlayerView)findViewById(R.id.ytplayer);
          ytPlayer.initialize(
                api_key,
                new YouTubePlayer.OnInitializedListener() {
                    // Implement two methods by clicking on red
                    // error bulb inside onInitializationSuccess
                    // method add the video link or the playlist
                    // link that you want to play In here we
                    // also handle the play and pause
                    // functionality
                    @Override
                    public void onInitializationSuccess(
                            YouTubePlayer.Provider provider,
                            YouTubePlayer youTubePlayer, boolean b)
                    {
                        youTubePlayer.loadVideo(topic_url);
                        youTubePlayer.play();
                    }

                    // Inside onInitializationFailure
                    // implement the failure functionality
                    // Here we will show toast
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult
                                                                youTubeInitializationResult)
                    {
                        Toast.makeText(getApplicationContext(), "Video player Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        endTime = System.currentTimeMillis();
        long timeSpend = endTime - startTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd G 'at' HH mm ss z");
        String currentDateandTime = sdf.format(new Date());
        Report report=new Report(topic_name,subject_name,"vedio",timeSpend);
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
}