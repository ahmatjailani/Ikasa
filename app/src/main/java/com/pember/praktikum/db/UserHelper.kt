package com.pember.praktikum.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.pember.praktikum.db.DatabaseContract.UserColumns.Companion.EMAIL
import com.pember.praktikum.db.DatabaseContract.UserColumns.Companion.PASSWORD
import com.pember.praktikum.db.DatabaseContract.UserColumns.Companion._ID

class UserHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = DatabaseContract.UserColumns.TABLE_NAME

        private var INSTANCE: UserHelper? = null
        fun getInstance(context: Context): UserHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }
    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }
    fun queryLogin(inputEmail: String, inputPassword : String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$EMAIL = ? and $PASSWORD = ?",
            arrayOf(inputEmail,inputPassword),
            null,
            null,
            null,
            null)
    }
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }

    fun login(email : String, password : String) : Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$EMAIL = ? AND $PASSWORD = ?",
            arrayOf(email,password),
            null,
            null,
            null
        )
    }
}