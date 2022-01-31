package com.neznaika_77.food_delivery

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context):SQLiteOpenHelper(context,"mptcart.db", null, 1) {
    val TABLE_NAME="cart_table"
    val COL2 = "NAME"
    val COL3 = "PRICE"
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT,PRICE INTEGER)"
        db.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_USER_TABLE)
        onCreate(db)
    }
    fun addData(name:String?, prucint: String?):Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL2,name)
        contentValues.put(COL3,prucint)
        val result = db.insert(TABLE_NAME,null,contentValues)
        return result!=-1L
    }
    fun showData(): Cursor?{
        val db = this.writableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
    fun delData():Boolean{
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME;")
        val result = db.insert(TABLE_NAME,null,null)
        return result!=-1L
    }
}