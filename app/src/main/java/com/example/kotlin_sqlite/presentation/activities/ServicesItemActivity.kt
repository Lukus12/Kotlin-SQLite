package com.example.kotlin_sqlite.presentation.activities

import android.os.Bundle
import android.widget.ImageView

import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlin_sqlite.R
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServicesItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_services_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val title: TextView = findViewById(R.id.in_item_services_title)
        val text: TextView = findViewById(R.id.in_item_services_desc)
        val price: TextView = findViewById(R.id.in_item_services_price)

        title.text = intent.getStringExtra("itemName")
        text.text = intent.getStringExtra("itemText")
        price.text = intent.getStringExtra("itemPrice")


    }
}