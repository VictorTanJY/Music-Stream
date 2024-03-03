package com.example.musicstream;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyView extends  RecyclerView.ViewHolder {

    public TextView titleTxt;
    public TextView titleArtiste;
    public ImageView image;
    public Button removeBtn;

    public MyView(@NonNull View itemView) {
        super(itemView);

        titleTxt = itemView.findViewById(R.id.titleTxt);
        titleArtiste = itemView.findViewById(R.id.txtArtiste);
        image = itemView.findViewById(R.id.image);
        removeBtn = itemView.findViewById(R.id.removeBtn);
    }
}
