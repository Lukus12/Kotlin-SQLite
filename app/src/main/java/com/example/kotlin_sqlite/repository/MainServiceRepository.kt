package com.example.kotlin_sqlite.repository


import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
// com.example.zov_android.service.MainService
//import com.example.zov_android.service.MainServiceActions
import javax.inject.Inject


class MainServiceRepository @Inject constructor(
    private val context: Context
) {
/*
    fun startService(username: String){
        Thread{
            val intent = Intent(context, MainService::class.java)
            intent.putExtra("username", username)
            intent.action = MainServiceActions.START_SERVICE.name
            startServiceIntent(intent)
        }.start()
    }

    private fun startServiceIntent(intent: Intent){
        context.startForegroundService(intent)
    }

    fun stopService() {
        val intent = Intent(context,MainService::class.java)
        intent.action = MainServiceActions.STOP_SERVICE.name
        startServiceIntent(intent)
    }*/
}