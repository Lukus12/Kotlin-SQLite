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
class ProductItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val image: ImageView = findViewById(R.id.item_product_image)
        val title: TextView = findViewById(R.id.item_product_title)
        val text:TextView = findViewById(R.id.item_product_desc)

        Picasso.get().load(intent.getStringExtra("itemImageURL")).into(image)
        title.text = intent.getStringExtra("itemName")
        text.text = intent.getStringExtra("itemText")



    }
}