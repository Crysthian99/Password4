package com.example.password

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class UsersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: UserModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.UserEntry.COLUMN_WEBID, user.webid)
        values.put(DBContract.UserEntry.COLUMN_WEB, user.web)
        values.put(DBContract.UserEntry.COLUMN_PASS, user.pass)
        values.put(DBContract.UserEntry.COLUMN_SALT, user.salt)
        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.UserEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(webid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.UserEntry.COLUMN_WEBID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(webid)
        // Issue SQL statement.
        db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }




    fun readAllUsers(): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var webid: Int
        var web: String
        var usid: String
        var pass: String
        var salt: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                webid = cursor.getInt(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_WEBID))
                web = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_WEB))
                pass = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_PASS))
                salt = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_SALT))
                users.add(UserModel(webid, web, pass, salt))
                cursor.moveToNext()
            }
        }
        return users
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "pass4.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                    DBContract.UserEntry.COLUMN_WEBID + " TEXT PRIMARY KEY," +
                    DBContract.UserEntry.COLUMN_WEB + " TEXT," +
                    DBContract.UserEntry.COLUMN_PASS + " TEXT," +
                    DBContract.UserEntry.COLUMN_SALT + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME
    }

}