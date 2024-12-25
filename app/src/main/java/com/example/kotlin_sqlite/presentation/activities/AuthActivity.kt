package com.example.kotlin_sqlite.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlin_sqlite.R
import com.example.kotlin_sqlite.databinding.ActivityAuthBinding
import com.example.kotlin_sqlite.presentation.DbHelper
import com.example.kotlin_sqlite.repository.MainRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var views: ActivityAuthBinding
    @Inject
    lateinit var mainRepository: MainRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        views = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(views.root)
        init()
    }

    private fun init(){
        views.apply {
            buttonAuth.setOnClickListener {

                val username = userLoginAuth.text.toString().trim()
                val password = userPassAuth.text.toString().trim()

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@AuthActivity, "Пожалуйста, введите имя пользователя и пароль", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                mainRepository.login(username, password) { isDone, message ->
                    if (!isDone) {
                        // выводим причину ошибки
                        Toast.makeText(this@AuthActivity, message, Toast.LENGTH_SHORT).show()
                    } else { // успешный вход в систему
                        startActivity(Intent(this@AuthActivity, MainActivity::class.java).apply {
                            putExtra("username", username)
                            putExtra("FRAGMENT_TO_SHOW", "MainFragment")
                            putExtra("TYPE", "view")
                        })
                        //finish()
                    }
                }
            }
            TransToReg.setOnClickListener {
                startActivity(Intent(this@AuthActivity, RegActivity::class.java))
            }
        }
    }
}