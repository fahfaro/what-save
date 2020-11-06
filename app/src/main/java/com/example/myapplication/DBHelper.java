package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.DBSchema.AUDIO_TABLE_NAME;
import static com.example.myapplication.DBSchema.DATABASE_NAME;
import static com.example.myapplication.DBSchema.DATABASE_VERSION;
import static com.example.myapplication.DBSchema.DOC_TABLE_NAME;
import static com.example.myapplication.DBSchema.IMAGE_TABLE_NAME;
import static com.example.myapplication.DBSchema.LOCATION_TABLE_NAME;
import static com.example.myapplication.DBSchema.VIDEO_TABLE_NAME;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBSchema.SQL_CREATE_LOCATION);
        db.execSQL(DBSchema.SQL_CREATE_IMAGE);
        db.execSQL(DBSchema.SQL_CREATE_AUDIO);
        db.execSQL(DBSchema.SQL_CREATE_VIDEO);
        db.execSQL(DBSchema.SQL_CREATE_DOCUMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertLocatioData(String title) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBSchema.COLUMN_TITLE, title);
        db.insert(LOCATION_TABLE_NAME, null, values);
        db.close();
    }

    public void insertImageData(String title) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBSchema.COLUMN_TITLE, title);
        db.insert(IMAGE_TABLE_NAME, null, values);
        db.close();
    }

    public void insertAudioData(String title) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBSchema.COLUMN_TITLE, title);
        db.insert(AUDIO_TABLE_NAME, null, values);
        db.close();
    }

    public void insertVideoData(String title) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBSchema.COLUMN_TITLE, title);
        db.insert(VIDEO_TABLE_NAME, null, values);
        db.close();
    }

    public void insertDocumentData(String title) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBSchema.COLUMN_TITLE, title);
        db.insert(DOC_TABLE_NAME, null, values);
        db.close();
    }

    public List<LocationModel> getDataSql() {
        List<LocationModel> locations_list = new ArrayList<LocationModel>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery(DBSchema.SQL_SELECT_ALL_LOCATION, new String[]{});
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                locations_list.add(new LocationModel(id, name));
            }
        }
        db.close();
        return locations_list;
    }

    public List<ImageModel> getImageDataSql() {
        List<ImageModel> image_list = new ArrayList<ImageModel>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery(DBSchema.SQL_SELECT_ALL_IMAGE, new String[]{});
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                image_list.add(new ImageModel(id, name));
            }
        }
        db.close();
        return image_list;
    }

    public List<AudioModel> getAudioDataSql() {
        List<AudioModel> audio_list = new ArrayList<AudioModel>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery(DBSchema.SQL_SELECT_ALL_AUDIO, new String[]{});
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                audio_list.add(new AudioModel(id, name));
            }
        }
        db.close();
        return audio_list;
    }

    public List<VideoModel> getVideoDataSql() {
        List<VideoModel> video_list = new ArrayList<VideoModel>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery(DBSchema.SQL_SELECT_ALL_VIDEO, new String[]{});
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                video_list.add(new VideoModel(id, name));
            }
        }
        db.close();
        return video_list;
    }

    public List<DocumentModel> getDocumentDataSql() {
        List<DocumentModel> document_list = new ArrayList<DocumentModel>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery(DBSchema.SQL_SELECT_ALL_DOCUMENT, new String[]{});
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                document_list.add(new DocumentModel(id, name));
            }
        }
        db.close();
        return document_list;
    }
}
