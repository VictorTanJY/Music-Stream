package com.example.musicstream;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicstream.util.AppUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlaySongActivity extends AppCompatActivity {

    // Instance variables to store the details of the song
    private String songId = "";
    private String title = "";
    private String artiste = "";
    private String fileLink = "";
    private String coverArt = "";
    private String url = "";
    private MediaPlayer player = null;
    private static final String BASE_URL = "https://p.scdn.co/mp3-preview/";
    private Button btnPlayPause = null;
    private int musicPosition = 0;
    SongCollection songCollection = new SongCollection();
    SongCollection originalSongCollection = new SongCollection();

    List <Song> shuffleList = Arrays.asList(songCollection.songArray);

    Button repeatBtn;
    Boolean repeatFlag = false;
    Button shuffleBtn;
    Boolean shuffleFlag = false;




    SeekBar seekBar;
    Handler handler = new Handler();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        retrieveData();
        displaySong(title, artiste, coverArt);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (player != null && player.isPlaying()) {
                    player.seekTo(seekBar.getProgress());

                }

            }
        });

        repeatBtn = findViewById(R.id.repeatBtn);
        shuffleBtn = findViewById(R.id.shuffleBtn);

    }

    Runnable p_bar = new Runnable() {
        @Override
        public void run() {
            if (player != null && player.isPlaying()) {
                seekBar.setProgress(player.getCurrentPosition());

            }
            handler.postDelayed(this, 1000);
        }
    };





    private void retrieveData() {
        // Get the Intent that started this activity
        Intent intent = getIntent();

        // Extract the Song data from the intent object using the
        // specific keys and put these values in the instance variables
        songId = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        artiste = intent.getStringExtra("artiste");
        fileLink = intent.getStringExtra("fileLink");
        coverArt = intent.getStringExtra("coverArt");

        //Prepare the URL link to play the song
        url = BASE_URL + fileLink;

    }

    private void displaySong(String title, String artiste, String coverArt) {


        // Retrieve the layout's song title TextView and set the string as its text
        TextView txtTitle = findViewById(R.id.txtSongTitle);
        txtTitle.setText(title);


        // Retrieve the layout's artiste TextView and set the string as its text
        TextView txtArtiste = findViewById(R.id.txtArtiste);
        txtArtiste.setText(artiste);


        // Get the id of the cover art from the drawable folder
        int imageId = AppUtil.getImageIdFromDrawable(this, coverArt);

        //Retrieve the layout's cover art ImageView
        ImageView ivCoverArt = findViewById(R.id.imgCoverArt);
        // Set the selected cover art image to the ImageView in the layout
        ivCoverArt.setImageResource(imageId);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void preparePlayer() {
        //1. Create a new media player
        player = new MediaPlayer();
        //The try-catch code is required by the prepare() method
        //It is to catch and handle any error that may occur.
        //The code shown simply print the error to the console
        //Using the printStackTrace()method.
        try {
            //2. Set the content type of the Audio attributes to music
            player.setAudioAttributes(new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());
            //3. Set the source of the music
            player.setDataSource(url);
            //4. Prepare the player for playback
            player.prepare(); //might take long !(for buffering, etc)

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void playOrPauseMusic(View view) {
        //1. If there is no MediaPlayer object , call
        // the preparePlayer method to create it.
        if (player == null) {
            preparePlayer();
        }
        //2. If player is NOT playing,
        if (!player.isPlaying()) {
            // If the position of the music is greater than 0
            if (musicPosition > 0) {
                //Get the player to go to the music position
                player.seekTo(musicPosition);
            }
            // Start the player
            player.start();
            seekBar.setMax(player.getDuration());
            handler.removeCallbacks(p_bar);
            handler.postDelayed(p_bar,1000);

            //Set the text of the play button to PAUSE
            btnPlayPause.setText("PAUSE");

            //Set the top bar title to the app to the music that is
            //currently playing
            setTitle("Now playing: " + title + " - " + artiste);

            //When the music ends , stop the player
            gracefullyStopsWhenMusicEnds();
        }
        else{
            //pause the music
            pauseMusic();
        }

    }

    private void pauseMusic (){
        //1. Pause the player.
        player.pause();
        //2. Get the current position of the music that is playing
        musicPosition = player.getCurrentPosition();

        //3. Set the text of the play view to PLAY.
        btnPlayPause.setText("PLAY");
    }

    private void gracefullyStopsWhenMusicEnds () {
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if ( repeatFlag)
                {
                    playOrPauseMusic(null);
                }else {
                    btnPlayPause.setText("PLAY");
                }
                //Add code here if you want something to happen to happen when the music ends

            }
        });
    }
    private void stopActivities (){
        if (player != null) {
            btnPlayPause.setText("PLAY");
            musicPosition = 0;
            setTitle("");
            player.stop();
            player.release();
            player = null;
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void playNext (View view){
        Song nextSong = songCollection.getNextSong(songId);
        if (nextSong != null) {
            songId = nextSong.getId();
            title = nextSong.getTitle();
            artiste = nextSong.getArtiste();
            fileLink = nextSong.getFileLink();
            coverArt = nextSong.getCoverArt();
            url = BASE_URL + fileLink;
            displaySong(title,artiste,coverArt);
            stopActivities();
            playOrPauseMusic(view);



        }

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void playPrev (View view ){
        Song prevSong = songCollection.getPrevSong(songId);
        if ( prevSong != null) {
            songId = prevSong.getId();
            title = prevSong.getTitle();
            artiste = prevSong.getArtiste();
            fileLink = prevSong.getFileLink();
            coverArt = prevSong.getCoverArt();
            url = BASE_URL + fileLink;
            displaySong(title, artiste, coverArt);
            stopActivities();
        }   playOrPauseMusic(view);
    }
    public void repeatSong (View view) {
        if (repeatFlag){
            repeatBtn.setBackgroundResource(R.drawable.repeat_off);
        }else
        {
            repeatBtn.setBackgroundResource(R.drawable.repeat_on);
        }
        repeatFlag = !repeatFlag;
    }
    public void shuffleSong (View view) {
        if (shuffleFlag){
           shuffleBtn.setBackgroundResource(R.drawable.shuffle_off);
           songCollection = new SongCollection();

        }else
        {
            shuffleBtn.setBackgroundResource(R.drawable.shuffle_on);
            Collections.shuffle(shuffleList);
            shuffleList.toArray(songCollection.songArray);



        }
        shuffleFlag = !shuffleFlag;
    }







}
