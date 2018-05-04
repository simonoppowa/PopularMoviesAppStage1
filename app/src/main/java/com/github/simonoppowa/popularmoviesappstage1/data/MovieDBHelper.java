package com.github.simonoppowa.popularmoviesappstage1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.github.simonoppowa.popularmoviesappstage1.data.MovieContract.MovieEntry.COLUMN_DATE_ADDED;
import static com.github.simonoppowa.popularmoviesappstage1.data.MovieContract.MovieEntry.COLUMN_MOVIE_ID;
import static com.github.simonoppowa.popularmoviesappstage1.data.MovieContract.MovieEntry.COLUMN_MOVIE_TITLE;
import static com.github.simonoppowa.popularmoviesappstage1.data.MovieContract.MovieEntry.FAVORITES_TABLE_NAME;

public class MovieDBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 1;


    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIES_TABLE =
                "CREATE TABLE " +
                        FAVORITES_TABLE_NAME + " ( " +
                        COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY ," +
                        COLUMN_MOVIE_TITLE + " STRING NOT NULL ," +
                        COLUMN_DATE_ADDED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"  + " );";

        Log.d("MYTAG",  SQL_CREATE_MOVIES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FAVORITES_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
