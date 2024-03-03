package com.example.musicstream;

public class Song {
    //instance variables
    private String id;
    private String title;
    private String artiste;
    private String fileLink;
    private double songLength;
    private String coverArt;

    //Constructor
    public Song (String initId, String initTitle, String initArtiste , String initFileLink,
                 double initDuration, String initCoverName ){
        id = initId;
        title = initTitle;
        artiste = initArtiste;
        fileLink = initFileLink;
        songLength = initDuration;
        coverArt = initCoverName;
    }
    //Getter methods for all instance variables
    public String getId() {
        return id;
    }
    public String getTitle () {
        return title ;
    }
    public String getArtiste() {
        return artiste;
    }
    public String getFileLink() {
        return fileLink;
    }
    public double getSongLength() {
        return songLength;
    }
    public String getCoverArt () {
        return coverArt;
    }



}
