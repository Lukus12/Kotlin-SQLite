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

class DoctorsItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctors_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val image: ImageView = findViewById(R.id.in_item_doctors_image)
        val title: TextView = findViewById(R.id.in_item_doctors_title)
        val text:TextView = findViewById(R.id.in_item_doctors_desc)
        val tel: TextView = findViewById(R.id.in_item_doctors_tel)

        Picasso.get().load(intent.getStringExtra("itemDocImageURL")).into(image)
        title.text = intent.getStringExtra("itemDocName")
        text.text = intent.getStringExtra("itemDocDesc")
        tel.text = "Телефон: " + intent.getStringExtra("ItemDocTel")
    }
}