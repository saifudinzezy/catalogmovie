package com.example.cyber_net.catalogmovie2.utils;

import static com.example.cyber_net.catalogmovie2.BuildConfig.API;

public class Server {
    public static String NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API+"&language=en-US";
    public static String UP_COMMING = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API+"&language=en-US";
    public static String SEARCH = "https://api.themoviedb.org/3/search/movie?api_key="+API+"&language=en-US&query=";

    public static String cari(String cari){
        return SEARCH+cari;
    }
}
