package com.example.myapplication;

public class DBSchema {
    public static final String DATABASE_NAME = "whatsaver.db";
    public static final int DATABASE_VERSION = 1;

    public static final String LOCATION_TABLE_NAME = "location";
    public static final String IMAGE_TABLE_NAME = "image";
    public static final String AUDIO_TABLE_NAME = "audio";
    public static final String VIDEO_TABLE_NAME = "video";
    public static final String DOC_TABLE_NAME = "document";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LOCATION_TITLE = "title";
    public static final String COLUMN_IMAGE_TITLE = "title";
    public static final String COLUMN_AUDIO_TITLE = "title";
    public static final String COLUMN_VIDEO_TITLE = "title";
    public static final String COLUMN_DOCUMENT_TITLE = "title";

    public static final String SQL_CREATE_LOCATION = "CREATE TABLE " + LOCATION_TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT," + COLUMN_LOCATION_TITLE + " TEXT);";
    public static final String SQL_CREATE_IMAGE = "CREATE TABLE " + IMAGE_TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT," + COLUMN_IMAGE_TITLE + " TEXT);";
    public static final String SQL_CREATE_AUDIO = "CREATE TABLE " + AUDIO_TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT," + COLUMN_AUDIO_TITLE + " TEXT);";
    public static final String SQL_CREATE_VIDEO = "CREATE TABLE " + VIDEO_TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY UNIQUE NOT NULL," + COLUMN_NAME + " TEXT NOT NULL," + COLUMN_VIDEO_TITLE + " TEXT NOT NULL);";
    public static final String SQL_CREATE_DOCUMENT = "CREATE TABLE " + DOC_TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT," + COLUMN_DOCUMENT_TITLE + " TEXT);";

    public static final String SQL_DELETE = " DROP TABLE IF EXISTS " + LOCATION_TABLE_NAME;
    public static final String SQL_DELETE_SELECTED_LOCATION = "delete from " + LOCATION_TABLE_NAME + " where " + COLUMN_ID + " = ";
    public static final String SQL_DELETE_SELECTED_IMAGE = "delete from " + IMAGE_TABLE_NAME + " where " + COLUMN_ID + " = ";
    public static final String SQL_DELETE_SELECTED_AUDIO = "delete from " + AUDIO_TABLE_NAME + " where " + COLUMN_ID + " = ";
    public static final String SQL_DELETE_SELECTED_VIDEO = "delete from " + VIDEO_TABLE_NAME + " where " + COLUMN_ID + " = ";
    public static final String SQL_DELETE_SELECTED_DOCUMENT = "delete from " + DOC_TABLE_NAME + " where " + COLUMN_ID + " = ";

    public static final String SQL_UPDATE = "SELECT * from " + LOCATION_TABLE_NAME + " where " + COLUMN_ID + " = ";

    public static final String SQL_SELECT_ALL_LOCATION = "SELECT * FROM " + LOCATION_TABLE_NAME;
    public static final String SQL_SELECT_ALL_IMAGE = "SELECT * FROM " + IMAGE_TABLE_NAME;
    public static final String SQL_SELECT_ALL_AUDIO = "SELECT * FROM " + AUDIO_TABLE_NAME;
    public static final String SQL_SELECT_ALL_VIDEO = "SELECT * FROM " + VIDEO_TABLE_NAME;
    public static final String SQL_SELECT_ALL_DOCUMENT = "SELECT * FROM " + DOC_TABLE_NAME;

    public static final String SQL_SELECT_LOCATION_BY_ID = "SELECT * FROM " + LOCATION_TABLE_NAME + " where " + COLUMN_ID + " = ";
    public static final String SQL_SELECT_IMAGE_BY_ID = "SELECT * FROM " + IMAGE_TABLE_NAME + " where " + COLUMN_ID + " = ";
    public static final String SQL_SELECT_AUDIO_BY_ID = "SELECT * FROM " + AUDIO_TABLE_NAME + " where " + COLUMN_ID + " = ";
    public static final String SQL_SELECT_VIDEO_BY_ID = "SELECT * FROM " + VIDEO_TABLE_NAME + " where " + COLUMN_ID + " = ";
    public static final String SQL_SELECT_DOCUMENT_BY_ID = "SELECT * FROM " + DOC_TABLE_NAME + " where " + COLUMN_ID + " = ";
}
