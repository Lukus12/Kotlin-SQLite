package com.example.kotlin_sqlite.repository

import com.example.kotlin_sqlite.domain.models.DataRecordModel
import com.example.kotlin_sqlite.domain.models.PetsClient
import com.example.kotlin_sqlite.firebaseClient.FirebaseClient
import com.google.gson.Gson

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val firebaseClient: FirebaseClient,
    private val gson: Gson
) {

    fun login(username: String, password: String, isDone: (Boolean, String?) -> Unit) {
        firebaseClient.login(username, password, isDone)
    }

    fun reg(username: String, password: String, isDone: (Boolean, String?) -> Unit) {
        firebaseClient.reg(username, password, isDone)
    }

    fun getUsername(isDone: (Boolean, String?) -> Unit){
        firebaseClient.getUsername(){ isSuccess, name ->
            isDone(isSuccess, name)
        }
    }

    fun getPets(username: String, isDone: (Boolean, List<PetsClient>?) -> Unit) {
        firebaseClient.getPets(username) { isSuccess, pets ->
            isDone(isSuccess, pets)
        }
    }

    fun addPets(username: String, petname: String, petdist:String, petdate:String, petgen:String, isDone: (Boolean, String?) -> Unit) {
        firebaseClient.addPets(username, petname, petdist, petdate, petgen) { isSuccess, com ->
            isDone(isSuccess, com)
        }
    }

    fun addRecord(username: String, petname: String, doctor: String, date: String, time: String, isDone: (Boolean, String?) -> Unit) {
        firebaseClient.addRecord(username, petname, doctor, date, time){ isSuccess, com ->
            isDone(isSuccess, com)
        }
    }

    fun getRecords(username: String, isDone: (Boolean, List<DataRecordModel>?) -> Unit) {
        firebaseClient.getRecords(username) { isSuccess, records ->
            isDone(isSuccess, records)
        }
    }

    // наблюдение за статусом записи
    fun observeRecordsStatus(status:(List<DataRecordModel>)->Unit){ //имя пользователя, статус
        firebaseClient.observeRecordStatus(status)
    }



    /*private fun changeMyStatus(status: UserStatus) {
        firebaseClient.changeMyStatus(status)
    }*/


}