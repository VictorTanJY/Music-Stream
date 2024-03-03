package com.example.musicstream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

public class FavouriteActivity extends AppCompatActivity {
RecyclerView favList;
SongAdapter songAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        favList = findViewById(R.id.recycleView);

        songAdapter = new SongAdapter(MainActivity.favList);
        // Attach the adapter to the recyclerview to populate items.
        favList.setAdapter(songAdapter);
        // Set Layout manager to position the items.
        favList.setLayoutManager(new LinearLayoutManager(this));
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                songAdapter.getFilter().filter(newText);
                return false;
            }
        });



    }

    public void removeAll(View view) {
        MainActivity.favList.clear();
        songAdapter.notifyDataSetChanged();
    }
}