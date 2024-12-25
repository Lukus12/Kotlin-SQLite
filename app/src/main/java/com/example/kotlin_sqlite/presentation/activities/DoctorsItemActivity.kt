package com.example.kotlin_sqlite.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
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

        val button: Button = findViewById(R.id.item_doctors_button);

        val image: ImageView = findViewById(R.id.in_item_doctors_image)
        val title: TextView = findViewById(R.id.in_item_doctors_title)
        val text:TextView = findViewById(R.id.in_item_doctors_desc)
        val tel: TextView = findViewById(R.id.in_item_doctors_tel)

        val doctorName: String? = intent.getStringExtra("itemDocName")

        Picasso.get().load(intent.getStringExtra("itemDocImageURL")).into(image)
        title.text = doctorName
        text.text = intent.getStringExtra("itemDocDesc")
        tel.text = "Телефон: " + intent.getStringExtra("ItemDocTel")

        button.setOnClickListener {
            startActivity(Intent(this@DoctorsItemActivity, MainActivity::class.java).apply {
                putExtra("FRAGMENT_TO_SHOW", "PetsFragment")
                putExtra("TYPE", "select")
                putExtra("DOCTOR", doctorName)
            })
        }
    }
}