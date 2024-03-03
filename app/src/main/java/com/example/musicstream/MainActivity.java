package com.example.musicstream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.musicstream.util.AppUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
SongCollection  songCollection =new SongCollection();
static ArrayList<Song> favList = new ArrayList<Song>();
 SharedPreferences sharedPreferences;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("playList", MODE_PRIVATE);
        String albums = sharedPreferences.getString("list", "");
        if (!albums.equals(""))
        {
            TypeToken<ArrayList<Song>> token = new TypeToken<ArrayList<Song>>(){};
            Gson gson = new Gson();
            favList = gson.fromJson(albums,token.getType());
        }

    }
    // Called when the user clicks on the image button
    public void handleSelection (View view ){
        //Do something in response to button

        //Get the id of the selected view
        String resourceId = AppUtil.getResourceId(this, view);

        // Retrieve the song object with this id
        Song  selectedSong = songCollection.searchById(resourceId);

        //Pop up a message on the screen to show the title of the song
        AppUtil.popMessage(this, "Streaming song: " +  selectedSong.getTitle());

        //Create a new Intent and specify the source and activity to start
        Intent intent = new Intent (this ,PlaySongActivity.class);

        //Store the Song information into the Intent object
        // to be sent over to the next activity/screen
        intent.putExtra("id", selectedSong.getId());
        intent.putExtra("title", selectedSong.getTitle());
        intent.putExtra("artiste", selectedSong.getArtiste());
        intent.putExtra("fileLink", selectedSong.getFileLink());
        intent.putExtra("songLength", selectedSong.getSongLength());
        intent.putExtra("coverArt", selectedSong.getCoverArt());

        //Start the activity
        startActivity(intent);



    }


    public void addToFavourite(View view) {
        String songID = view.getContentDescription().toString();
        Song song = songCollection.searchById(songID);
        favList.add(song);
        Gson gson = new Gson();
        String json = gson.toJson (favList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("list",json);
        editor.apply();
        Log.d("gson", json);
        //Toast.makeText(this, "button is clicked", Toast.LENGTH_SHORT).show();
    }

    public void goToFavouriteActivity(View view) {
        Intent intent = new Intent(this, FavouriteActivity.class);
        startActivity(intent);

            

    }
}

