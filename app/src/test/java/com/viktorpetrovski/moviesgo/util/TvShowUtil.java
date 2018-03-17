package com.viktorpetrovski.moviesgo.util;

import com.google.gson.Gson;
import com.viktorpetrovski.moviesgo.data.model.TvShow;
import com.viktorpetrovski.moviesgo.data.remote.apiModel.TvShowListResponse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Victor on 3/17/18.
 */

public class TvShowUtil {

    public static TvShowListResponse getTvShowsList(ClassLoader classLoader) throws UnsupportedEncodingException {
        return getTvShowsListResponseJsonForFilename(classLoader,"api-response/popular_tv_shows.json");
    }

    public static TvShow getTvShowDetails(ClassLoader classLoader) throws UnsupportedEncodingException {
        return getTvShowJsonForFilename(classLoader,"api-response/tv_show_details.json");
    }


    private static TvShowListResponse getTvShowsListResponseJsonForFilename(ClassLoader classLoader, String filename) throws UnsupportedEncodingException {
        InputStream inputStream = classLoader.getResourceAsStream(filename);
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        return new Gson().fromJson(reader, TvShowListResponse.class);
    }

    private static TvShow getTvShowJsonForFilename(ClassLoader classLoader, String filename) throws UnsupportedEncodingException {
        InputStream inputStream = classLoader.getResourceAsStream(filename);
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        return new Gson().fromJson(reader, TvShow.class);
    }
}
