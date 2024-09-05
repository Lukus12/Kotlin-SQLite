package com.example.kotlin_sqlite.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_sqlite.domain.models.Product


//класс для работы с инфой, которая будет постоянно обновляться с получением данных с сервака
class MainViewModel:ViewModel() {
    //val liveDataCurrent = MutableLiveData<Item>() //обновлене одного элемента
    val liveDataList = MutableLiveData<List<Product>>() //обновление списка элементов
    val favData = MutableLiveData<List<Product>>()
}   