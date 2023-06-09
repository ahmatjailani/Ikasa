package com.pember.praktikum.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.pember.praktikum.db.DatabaseContract.IkanColumns.Companion.TABLE_NAME
import com.pember.praktikum.db.DatabaseContract.TransaksiColumns.Companion.TABLE_NAME as TABLE_TRANSAKSI
import com.pember.praktikum.db.DatabaseContract.UserColumns.Companion.TABLE_NAME as TABLE_USER
import com.pember.praktikum.db.DatabaseContract.CartColumns.Companion.TABLE_NAME as TABLE_CART
import com.pember.praktikum.db.DatabaseContract.LoginColumns.Companion.TABLE_NAME as TABLE_LOGIN

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ikasa"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_IKAN = "CREATE TABLE IF NOT EXISTS $TABLE_NAME" +
                " (${DatabaseContract.IkanColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.IkanColumns.NAMA} TEXT NOT NULL," +
                " ${DatabaseContract.IkanColumns.HARGA} INTEGER NOT NULL," +
                " ${DatabaseContract.IkanColumns.DESKRIPSI} TEXT NOT NULL," +
                " ${DatabaseContract.IkanColumns.STOCK} INTEGER NOT NULL," +
                " ${DatabaseContract.IkanColumns.GAMBAR} BLOB NOT NULL);"
        private const val SQL_CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS $TABLE_USER" +
                " (${DatabaseContract.UserColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.UserColumns.NAMA} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumns.EMAIL} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumns.ROLE} INTEGER NOT NULL," +
                " ${DatabaseContract.UserColumns.PASSWORD} TEXT NOT NULL);"
        private const val SQL_CREATE_TABLE_TRANSAKSI = "CREATE TABLE IF NOT EXISTS $TABLE_TRANSAKSI" +
                " (${DatabaseContract.TransaksiColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.TransaksiColumns.ID_USER} INTEGER NOT NULL," +
                " ${DatabaseContract.TransaksiColumns.ID_IKAN} INTEGER NOT NULL," +
                " ${DatabaseContract.TransaksiColumns.BERAT} INTEGER NOT NULL," +
                " ${DatabaseContract.TransaksiColumns.TOTAL} INTEGER NOT NULL," +
                " ${DatabaseContract.TransaksiColumns.STATUS} TEXT NOT NULL);"
        private const val SQL_CREATE_TABLE_CART = "CREATE TABLE IF NOT EXISTS $TABLE_CART" +
                " (${DatabaseContract.CartColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.CartColumns.ID_USER} INTEGER NOT NULL," +
                " ${DatabaseContract.CartColumns.ID_IKAN} INTEGER NOT NULL," +
                " ${DatabaseContract.CartColumns.BERAT} INTEGER NOT NULL," +
                " ${DatabaseContract.CartColumns.TOTAL} INTEGER NOT NULL);"
        private const val SQL_CREATE_TABLE_LOGIN = "CREATE TABLE IF NOT EXISTS $TABLE_LOGIN" +
                " (${DatabaseContract.LoginColumns.ID_USER} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.LoginColumns.NAMA_USER} TEXT NOT NULL," +
        " ${DatabaseContract.LoginColumns.ROLE} INTEGER NOT NULL);"

        private const val SQL_CREATE_ADMIN = "INSERT INTO $TABLE_USER" +
                " (${DatabaseContract.UserColumns.NAMA}, ${DatabaseContract.UserColumns.EMAIL}, ${DatabaseContract.UserColumns.ROLE}, ${DatabaseContract.UserColumns.PASSWORD})" +
                " VALUES ('Admin', 'admin@gmail.com', 1,'admin');"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_IKAN)
        db.execSQL(SQL_CREATE_TABLE_USER)
        db.execSQL(SQL_CREATE_TABLE_TRANSAKSI)
        db.execSQL(SQL_CREATE_TABLE_CART)
        db.execSQL(SQL_CREATE_TABLE_LOGIN)
        db.execSQL(SQL_CREATE_ADMIN)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSAKSI")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CART")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LOGIN")
        onCreate(db)
    }
}