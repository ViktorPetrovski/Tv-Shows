package com.viktorpetrovski.moviesgo.util;

import com.google.gson.Gson;
import com.viktorpetrovski.moviesgo.data.remote.apiModel.TvShowListResponse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Victor on 3/17/18.
 */

public class TvShowUtil {

    public static TvShowListResponse getPopularTvShows(ClassLoader classLoader) throws UnsupportedEncodingException {
        InputStream inputStream = classLoader.getResourceAsStream("api-response/popular_tv_shows.json");
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        return new Gson().fromJson(reader, TvShowListResponse.class);
    }
}
