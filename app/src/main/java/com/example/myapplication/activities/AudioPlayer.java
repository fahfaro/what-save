package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data.DBHelper;
import com.example.myapplication.helper.Constants;
import com.example.myapplication.models.AudioModel;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class AudioPlayer extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private ImageView play, stopIcon, backIcon;
    private SeekBar mSeekBarTime;
    private TextView songName;
    private DBHelper dbHelper;
    private List<AudioModel> audioModels;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        init();
        dbHelper = new DBHelper(this);
        audioModels = dbHelper.getAudioDataSql();
        Intent intent = getIntent();

        position = intent.getIntExtra("position", -1);
        initializeMusicPlayer(position);

        play.setOnClickListener(v -> play());
        stopIcon.setOnClickListener(v -> {
            songName.setText("");
            play.setImageResource(R.drawable.play);
            mediaPlayer.release();
            mediaPlayer = null;
        });
        backIcon.setOnClickListener(v -> onBackPressed());
    }

    private void initializeMusicPlayer(int position) {

        // if mediaplayer is not null and playing reset it at the launch of activity

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }

        // getting out the song name
        String title;
        long idfordelete = audioModels.get(position).getId();
        title = dbHelper.getAudioName(idfordelete);

//        set song name
        songName.setText(title);

        // accessing the songs on storage

        File path = getDir(Constants.DIR_NAME_AUDIO, MODE_PRIVATE);
        File file = new File(path, title);

        // creating a mediaplayer
        // passing the uri

        mediaPlayer = MediaPlayer.create(this, Uri.fromFile(file));

        // SETTING ON PREPARED MEDIAPLAYER

        mediaPlayer.setOnPreparedListener(mp -> {

            // seekbar
            mSeekBarTime.setMax(mediaPlayer.getDuration());

            // while mediaplayer is playing the play button should display pause
            play.setImageResource(R.drawable.pause);
            // start the mediaplayer
            mediaPlayer.start();
        });

        // setting the oncompletion listener
        // after song finishes what should happen // for now we will just set the pause button to play

        mediaPlayer.setOnCompletionListener(mp -> play.setImageResource(R.drawable.play));


//         if you want the the mediaplayer to go to next song after its finished playing one song its optional
        /*mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play.setImageResource(R.drawable.play);

                int currentPosition = position;
                if (currentPosition < musicList.size() -1) {
                    currentPosition++;
                } else {
                    currentPosition = 0;
                }
                initializeMusicPlayer(currentPosition);

            }
        });*/


        // working on seekbar

//        mSeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                // IF USER TOUCHES AND MESSES WITH SEEEKBAR
//                if (fromUser) {
//                    mSeekBarTime.setProgress(progress);
//                    mediaPlayer.seekTo(progress);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        // till here seekbar wont change on its own

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(mediaPlayer!=null) {
//                    try {
//                        if (mediaPlayer.isPlaying()) {
//                            Message message = new Message();
//                            message.what = mediaPlayer.getCurrentPosition();
//                            handler.sendMessage(message);
//                            Thread.sleep(1000);
//
//                        }
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();

    }
//
//    @SuppressLint("HandlerLeak")
//    private Handler handler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            mSeekBarTime.setProgress(msg.what);
//        }
//    };

    // lastly create a method for play

    private void play() {
        // if mediaplayer is not null and playing and if play button is pressed pause it

        if (mediaPlayer != null){
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                // change the image of playpause button to play when we pause it
                play.setImageResource(R.drawable.play);
            } else {
                mediaPlayer.start();
                // if mediaplayer is playing // the image of play button should display pause
                play.setImageResource(R.drawable.pause);

            }
        }else {
            initializeMusicPlayer(position);
        }
    }

    private void init() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("WhatsSave");
        mSeekBarTime = findViewById(R.id.mSeekBarTime);
        songName = findViewById(R.id.songName);
        play = findViewById(R.id.play);
        stopIcon = findViewById(R.id.stop);
        backIcon = findViewById(R.id.back);
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.release();
        super.onDestroy();
    }
}
