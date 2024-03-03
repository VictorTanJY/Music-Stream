package com.example.musicstream;

public class SongCollection {
    // Instance variable: An array to store 2 Song objects
    public Song songArray [] = new Song[4];

    //Constructor of SongCollection class
    public SongCollection()  { prepareSongs ();  }

    //Create Song objects and store them into songArray
    public void prepareSongs () {

        //Create the first Song object
        Song theWayYouLookTonight = new Song("S1001","The Way You Look Tonight","Michael Buble",
                "a5b8972e764025020625bbf9c1c2bbb06e394a60?cid=2afe87a64b0042dabf51f37318616965",4.66,
                "michael_buble_collection");
        //Create the second Song object
        Song billieJean = new Song("S1002", "Billie Jean","Michael Jackson",
                "f504e6b8e037771318656394f532dede4f9bcaea?cid=2afe8", 4.9, "billie_jean");
        Song feelingGood = new Song ("S1003","Feeling Good","Michael Buble", "31fc9f95c681542af33e72bda7baa4951117dccd?cid=2afe87a64b0042dabf51f37318616965", 3.96
                , "michael_buble_collection");
        Song comeFlyWithMe = new Song ("S1004","Come Fly With Me","Michael Buble", "cd985b3cc11e57ae50939c071d68d3fad9a77779?cid=2afe87a64b0042dabf51f37318616965", 3.31
                , "michael_buble_collection");

        //Insert the song objects into the SongArray
        songArray[0] = theWayYouLookTonight;
        songArray[1] = billieJean;
        songArray[2] = feelingGood;
        songArray[3] = comeFlyWithMe;
        }
    //Search and return the song with the specified id.
    public Song searchById (String id) {
        Song song = null;
        for (int index = 0; index < songArray.length; index++) {
            song = songArray[index];
            if (song.getId().equals(id)) {
                return song;
            }
        }

        //If the song cannot be found in the SongArray,
        //The null song object will be returned
        return null;

    }
    public Song getNextSong (String currentSongId) {
        Song song = null;
        for (int index = 0; index < songArray.length; index++){
            String tempSongId = songArray[index].getId();
            if (tempSongId.equals(currentSongId)&& (index < songArray.length -1)) {
                song = songArray[index+1];
                break;
            }
        }
        return song;

    }
    public Song getPrevSong (String currentSongId){
        Song song = null;
        for (int index = 0; index < songArray.length; index++){
            String tempSongId = songArray[index].getId();
            if (tempSongId.equals(currentSongId)&& (index > 0)){
                song = songArray[index -1];
                break;
            }
        }
        return song;
    }
    public Song getCurrentSong (String currentSongId) {
        Song song = null;
        for (Song value : songArray) {
            String tempSongId = value.getId();
            if (tempSongId.equals(currentSongId)) {
                return value;
            }
        }
        return song;
    }

}
