package com.example.myapplication;

public class DBSchema {
    public static final String DATABASE_NAME = "whatsaver.db";
    public static final int DATABASE_VERSION = 1;

    public static final String PDF_TABLE_NAME = "pdf";
    public static final String IMAGE_TABLE_NAME = "image";
    public static final String AUDIO_TABLE_NAME = "audio";
    public static final String VIDEO_TABLE_NAME = "video";
//    public static final String DOC_TABLE_NAME = "document";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TITLE = "title";


    public static final String SQL_CREATE_PDF = "CREATE TABLE " + PDF_TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY UNIQUE NOT NULL," + COLUMN_NAME + " TEXT NOT NULL," + COLUMN_TITLE + " TEXT NOT NULL);";
    public static final String SQL_CREATE_IMAGE = "CREATE TABLE " + IMAGE_TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY UNIQUE NOT NULL," + COLUMN_NAME + " TEXT NOT NULL," + COLUMN_TITLE + " TEXT NOT NULL);";
    public static final String SQL_CREATE_AUDIO = "CREATE TABLE " + AUDIO_TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY UNIQUE NOT NULL," + COLUMN_NAME + " TEXT NOT NULL," + COLUMN_TITLE + " TEXT NOT NULL);";
    public static final String SQL_CREATE_VIDEO = "CREATE TABLE " + VIDEO_TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY UNIQUE NOT NULL," + COLUMN_NAME + " TEXT NOT NULL," + COLUMN_TITLE + " TEXT NOT NULL);";
//    public static final String SQL_CREATE_DOCUMENT = "CREATE TABLE " + DOC_TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY UNIQUE NOT NULL," + COLUMN_NAME + " TEXT NOT NULL," + COLUMN_TITLE + " TEXT NOT NULL);";

    public static final String SQL_DELETE = " DROP TABLE IF EXISTS " + PDF_TABLE_NAME;
    public static final String SQL_DELETE_SELECTED_PDF = "delete from " + PDF_TABLE_NAME + " where " + COLUMN_ID + " = ";
    public static final String SQL_DELETE_SELECTED_IMAGE = "delete from " + IMAGE_TABLE_NAME + " where " + COLUMN_ID + " = ";
    public static final String SQL_DELETE_SELECTED_AUDIO = "delete from " + AUDIO_TABLE_NAME + " where " + COLUMN_ID + " = ";
    public static final String SQL_DELETE_SELECTED_VIDEO = "delete from " + VIDEO_TABLE_NAME + " where " + COLUMN_ID + " = ";
//    public static final String SQL_DELETE_SELECTED_DOCUMENT = "delete from " + DOC_TABLE_NAME + " where " + COLUMN_ID + " = ";

    public static final String SQL_UPDATE = "SELECT * from " + PDF_TABLE_NAME + " where " + COLUMN_ID + " = ";

    public static final String SQL_SELECT_ALL_PDF = "SELECT * FROM " + PDF_TABLE_NAME;
    public static final String SQL_SELECT_ALL_IMAGE = "SELECT * FROM " + IMAGE_TABLE_NAME;
    public static final String SQL_SELECT_ALL_AUDIO = "SELECT * FROM " + AUDIO_TABLE_NAME;
    public static final String SQL_SELECT_ALL_VIDEO = "SELECT * FROM " + VIDEO_TABLE_NAME;
//    public static final String SQL_SELECT_ALL_DOCUMENT = "SELECT * FROM " + DOC_TABLE_NAME;

    public static final String SQL_SELECT_PDF_BY_ID = "SELECT * FROM " + PDF_TABLE_NAME + " where " + COLUMN_ID + " = ";
    public static final String SQL_SELECT_IMAGE_BY_ID = "SELECT * FROM " + IMAGE_TABLE_NAME + " where " + COLUMN_ID + " = ";
    public static final String SQL_SELECT_AUDIO_BY_ID = "SELECT * FROM " + AUDIO_TABLE_NAME + " where " + COLUMN_ID + " = ";
    public static final String SQL_SELECT_VIDEO_BY_ID = "SELECT * FROM " + VIDEO_TABLE_NAME + " where " + COLUMN_ID + " = ";
//    public static final String SQL_SELECT_DOCUMENT_BY_ID = "SELECT * FROM " + DOC_TABLE_NAME + " where " + COLUMN_ID + " = ";
}
