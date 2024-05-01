package com.example.kotlin_sqlite

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_items)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //создаём наш список (в виде массива с элементами типа Item)
        val itemsList:RecyclerView = findViewById(R.id.itemsList)
        val items = arrayListOf<Item>()

        items.add(Item(1,"potato","Delicious Potatoes","It's good for your health","grown with love", 50))
        items.add(Item(2,"house","Cool House","Comfortable and beautiful","Anyone would want such a house", 100000))
        items.add(Item(3,"mouse","Mouse King","The great mouse King","Be careful, otherwise it will make you a mouse", 10000))
        items.add(Item(4,"floppy","Big Floppa Slippers","Soft and comfortable, sometimes they make a \"kar\" sound","Who did this to floppy", 2500))

        itemsList.layoutManager = LinearLayoutManager(this) // указываем в каком формате будут располагаться элементы дизайна
        itemsList.adapter = ItemsAdapter(items, this) // подставляем в адаптер свой собственный
    }
}