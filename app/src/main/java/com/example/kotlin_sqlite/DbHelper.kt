package com.example.kotlin_sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class DbHelper(val context: Context, val factory:SQLiteDatabase.CursorFactory? ) :
    SQLiteOpenHelper(context,"dbApp", factory, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id INT PRIMARY KEY, login TEXT, email TEXT, pass TEXT)"
        db!!.execSQL(query) //выполнение sql запроса
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    //регистрируем нового пользователя в нашей базе данных
    fun addUser(user:User){
        val values = ContentValues()
        values.put("login", user.login) // подставляем данные в sql запрос
        values.put("email", user.email)
        values.put("pass", user.pass)

        val db = this.writableDatabase //обращение к нашей бд, в которую можно что-то записать
        db.insert("users", null, values)
        db.close()
    }

}