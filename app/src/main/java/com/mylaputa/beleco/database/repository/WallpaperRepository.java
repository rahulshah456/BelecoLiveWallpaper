package com.mylaputa.beleco.database.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.mylaputa.beleco.database.LiveWallpaperDatabase;
import com.mylaputa.beleco.database.dao.WallpaperDao;
import com.mylaputa.beleco.database.models.LocalWallpaper;
import com.mylaputa.beleco.utils.Constant;

import java.util.List;

public class WallpaperRepository {

    private WallpaperDao mWallpaperDao;
    private LiveData<List<LocalWallpaper>> mLocalWallpapers;


    WallpaperRepository(Context context, String playlistId) {
        LiveWallpaperDatabase database = LiveWallpaperDatabase.getDatabase(context);
        mWallpaperDao = database.wallpaperDao();
        mLocalWallpapers = mWallpaperDao.getPlaylistWallpapers(playlistId);
    }


    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<LocalWallpaper>> getPlaylistWallpapers(String playlistId){
        return mLocalWallpapers;
    }


    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(LocalWallpaper wallpaper) {
        LiveWallpaperDatabase.databaseWriteExecutor.execute(() -> {
            mWallpaperDao.insertWallpaper(wallpaper);
        });
    }


    void delete(LocalWallpaper wallpaper) {
        LiveWallpaperDatabase.databaseWriteExecutor.execute(() -> {
            mWallpaperDao.deleteWallpaper(wallpaper);
        });
    }


    void deletePlaylistWallpapers(String playlistId) {
        LiveWallpaperDatabase.databaseWriteExecutor.execute(() -> {
            mWallpaperDao.deletePlaylistWallpapers(playlistId);
        });
    }


}