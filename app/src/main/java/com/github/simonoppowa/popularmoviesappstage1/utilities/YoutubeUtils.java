package com.github.simonoppowa.popularmoviesappstage1.utilities;

import android.net.Uri;
import android.util.Log;

import java.net.URL;


public class YoutubeUtils {

    //youtube video link
    private static String YOUTUBE_VIDEO_BASE_URL = "https://www.youtube.com/watch";
    private static String YOUTUBE_YOUTUBE_VIDEO_KEY_QUERY_TAG = "v";


    //youtube thumbnail image
    private static String YOUTUBE_TN_BASE_URL = "https://img.youtube.com/vi/";
    private static String YOUTUBE_TN_QUALTITY_TAG = "default";
    private static String YOUTUBE_TN_HDQUALTITY_TAG = "hqdefault";
    private static String YOUTUBE_TN_FORMAT_TAG = ".jpg";


    public static Uri buildYoutubeVideoLink(String videoKey) {
        Uri builtUri = Uri.parse(YOUTUBE_VIDEO_BASE_URL).buildUpon()
                .appendQueryParameter(YOUTUBE_YOUTUBE_VIDEO_KEY_QUERY_TAG, videoKey)
                .build();

        Log.d("MYTAG",  builtUri.toString());

        return builtUri;
    }

    public static URL buildYoutubeThumbnailURL(String videoKey) {
        Uri builtUri = Uri.parse(YOUTUBE_TN_BASE_URL).buildUpon()
                .appendPath(videoKey)
                .appendPath(YOUTUBE_TN_HDQUALTITY_TAG + YOUTUBE_TN_FORMAT_TAG)
                .build();

        URL builtUrl = NetworkUtils.buildUrl(builtUri);

        Log.d("MYTAG",  builtUrl.toString());

        return builtUrl;
    }
}
