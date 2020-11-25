package com.example.myapplication.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.myapplication.models.AudioModel;
import com.example.myapplication.models.ImageModel;
import com.example.myapplication.models.PdfModel;
import com.example.myapplication.models.VideoModel;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public DBHelper(@Nullable Context context) {
        super(context, DBSchema.DATABASE_NAME, null, DBSchema.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBSchema.SQL_CREATE_PDF);
        db.execSQL(DBSchema.SQL_CREATE_IMAGE);
        db.execSQL(DBSchema.SQL_CREATE_AUDIO);
        db.execSQL(DBSchema.SQL_CREATE_VIDEO);
//        db.execSQL(DBSchema.SQL_CREATE_DOCUMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertPdfData(long id, String name, String title) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBSchema.COLUMN_ID,id);
        values.put(DBSchema.COLUMN_NAME, name);
        values.put(DBSchema.COLUMN_TITLE, title);
        db.insert(DBSchema.PDF_TABLE_NAME, null, values);
        db.close();
    }

    public void insertImageData(long  id, String name, String title) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBSchema.COLUMN_ID, id);
        values.put(DBSchema.COLUMN_NAME, name);
        values.put(DBSchema.COLUMN_TITLE, title);
        db.insert(DBSchema.IMAGE_TABLE_NAME, null, values);
        db.close();
    }

    public void insertAudioData(long id, String name, String title) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBSchema.COLUMN_ID,id);
        values.put(DBSchema.COLUMN_NAME, name);
        values.put(DBSchema.COLUMN_TITLE, title);
        db.insert(DBSchema.AUDIO_TABLE_NAME, null, values);
        db.close();
    }

    public void insertVideoData(long id, String name, String title) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBSchema.COLUMN_ID, id);
        values.put(DBSchema.COLUMN_NAME, name);
        values.put(DBSchema.COLUMN_TITLE, title);
        db.insert(DBSchema.VIDEO_TABLE_NAME, null, values);
        db.close();
    }

//    public void insertDocumentData(int id, String name, String title) {
//        db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(DBSchema.COLUMN_NAME, name);
//        values.put(DBSchema.COLUMN_TITLE, title);
//        db.insert(DOC_TABLE_NAME, null, values);
//        db.close();
//    }

    public List<PdfModel> getPdfDataSql() {
        List<PdfModel> pdf_list = new ArrayList<>();
        db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(DBSchema.SQL_SELECT_ALL_PDF, new String[]{});
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                String title = cursor.getString(2);
                pdf_list.add(new PdfModel(id, name, title));
            }
        }
        db.close();
        return pdf_list;
    }

    public List<ImageModel> getImageDataSql() {
        List<ImageModel> image_list = new ArrayList<>();
        db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(DBSchema.SQL_SELECT_ALL_IMAGE, new String[]{});
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                String title = cursor.getString(2);
                image_list.add(new ImageModel(id, name, title));
            }
        }
        db.close();
        return image_list;
    }

    public List<AudioModel> getAudioDataSql() {
        List<AudioModel> audio_list = new ArrayList<>();
        db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(DBSchema.SQL_SELECT_ALL_AUDIO, new String[]{});
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                String title = cursor.getString(2);
                audio_list.add(new AudioModel(id, name, title));
            }
        }
        db.close();
        return audio_list;
    }

    public List<VideoModel> getVideoDataSql() {
        List<VideoModel> video_list = new ArrayList<>();
        db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(DBSchema.SQL_SELECT_ALL_VIDEO, new String[]{});
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                String title = cursor.getString(2);
                video_list.add(new VideoModel(id, name, title));
            }
        }
        db.close();
        return video_list;
    }

//    public List<DocumentModel> getDocumentDataSql() {
//        List<DocumentModel> document_list = new ArrayList<DocumentModel>();
//        db = getReadableDatabase();
//        Cursor cursor = db.rawQuery(DBSchema.SQL_SELECT_ALL_DOCUMENT, new String[]{});
//        if (cursor != null) {
//            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
//                int id = cursor.getInt(0);
//                String name = cursor.getString(1);
//                String title = cursor.getString(2);
//                document_list.add(new DocumentModel(id, name, title));
//            }
//        }
//        db.close();
//        return document_list;
//    }

    public void deleteSelectedPdf(long id) {
        db = getWritableDatabase();
        db.execSQL(DBSchema.SQL_DELETE_SELECTED_PDF + id);
        db.close();
    }

    public void deleteSelectedImage(long id) {
        db = getWritableDatabase();
        db.execSQL(DBSchema.SQL_DELETE_SELECTED_IMAGE + id);
        db.close();
    }

    public void deleteSelectedAudio(long id) {
        db = getWritableDatabase();
        db.execSQL(DBSchema.SQL_DELETE_SELECTED_AUDIO + id);
        db.close();
    }

    public void deleteSelectedVideo(long id) {
        db = getWritableDatabase();
        db.execSQL(DBSchema.SQL_DELETE_SELECTED_VIDEO + id);
        db.close();
    }

//    public void deleteSelectedDocument(long id) {
//        db = getWritableDatabase();
//        db.execSQL(DBSchema.SQL_DELETE_SELECTED_DOCUMENT + id);
//        db.close();
//    }

    public String getPdfName(long id) {
        db = getWritableDatabase();
        String title = null;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(DBSchema.SQL_SELECT_PDF_BY_ID + id, new String[]{});
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                title = cursor.getString(2);
            }
        }
        db.close();
        return title;
    }

    public String getImageName(long id) {
        db = getWritableDatabase();
        String title = null;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(DBSchema.SQL_SELECT_IMAGE_BY_ID + id, new String[]{});
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                title = cursor.getString(2);
            }
        }
        db.close();
        return title;
    }

    public String getAudioName(long id) {
        db = getWritableDatabase();
        String title = null;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(DBSchema.SQL_SELECT_AUDIO_BY_ID + id, new String[]{});
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                title = cursor.getString(2);
            }
        }
        db.close();
        return title;
    }

    public String getVideoName(long id) {
        db = getWritableDatabase();
        String title = null;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(DBSchema.SQL_SELECT_VIDEO_BY_ID + id, new String[]{});
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                title = cursor.getString(2);
            }
        }
        db.close();
        return title;
    }

//    public String getDocumnetName(int pos) {
//        int id = pos + 1;
//        db = getWritableDatabase();
//        String title = null;
//        Cursor cursor = db.rawQuery(DBSchema.SQL_SELECT_DOCUMENT_BY_ID + id, new String[]{});
//        if (cursor != null) {
//            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
//                title = cursor.getString(2);
//            }
//        }
//        db.close();
//        return title;
//    }
}