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
import com.example.kotlin_sqlite.domain.models.User
import com.example.kotlin_sqlite.presentation.DbHelper
import com.example.kotlin_sqlite.repository.MainRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegActivity : AppCompatActivity() {
    @Inject
    lateinit var mainRepository: MainRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reg)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.placeHolder)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userLogin: EditText = findViewById(R.id.userLogin)
        val userPass: EditText = findViewById(R.id.userPass)
        val buttonReg: Button = findViewById(R.id.buttonReg)
        val transToAuth: TextView = findViewById(R.id.TransToAuth)

        buttonReg.setOnClickListener{
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if(login == "" || pass=="") {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            }

            mainRepository.reg(login,pass){ isDone, message  ->
                if (!isDone) {
                    // выводим причину ошибки
                    Toast.makeText(this@RegActivity, message , Toast.LENGTH_SHORT).show()
                } else { // успешный вход в систему
                    startActivity(Intent(this@RegActivity, MainActivity::class.java).apply {
                        putExtra("username", login)
                        intent.putExtra("FRAGMENT_TO_SHOW", "MainFragment")
                        intent.putExtra("TYPE", "view")
                    })
                }
            }

        }

        transToAuth.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

    }
}
