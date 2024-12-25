package com.example.kotlin_sqlite.presentation.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlin_sqlite.R
import com.example.kotlin_sqlite.presentation.fragments.AddPetsFragment
import com.example.kotlin_sqlite.presentation.fragments.MainFragment
import com.example.kotlin_sqlite.presentation.fragments.PetsFragment
import com.example.kotlin_sqlite.presentation.fragments.ProfileFragment
import com.example.kotlin_sqlite.presentation.fragments.HistoryFragment
import com.example.kotlin_sqlite.repository.MainRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var mainRepository: MainRepository
    lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.placeHolder)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        mainRepository.getUsername(){ isSuccess, name ->
            if(isSuccess){
                if (name != null) {
                    username = name
                }
            }
            else{
                Log.d("Error", "$name")
            }
        }


        // Получаем данные из Intent
        val fragmentToShow = intent.getStringExtra("FRAGMENT_TO_SHOW")
        val fragmentToType = intent.getStringExtra("TYPE")
        val fragmentToDoctor = intent.getStringExtra("DOCTOR")

        Log.d("MainFragment", "ToShow:$fragmentToShow, ToType:$fragmentToType")

        // Загружаем MainFragment по умолчанию
        if (savedInstanceState == null) {
            when (fragmentToShow) {
                "MainFragment" -> switchToBackMainFragment()
                "PetsFragment" -> switchToPetsFragment(username, fragmentToType, fragmentToDoctor)
            }
        }
    }

    fun switchToProfileFragment(username: String) {
        val profileFragment = ProfileFragment().apply {
            arguments = Bundle().apply {
                putString("username", username)
            }
        }
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_enter, // Анимация входа
                R.anim.fragment_exit,  // Анимация выхода
                R.anim.fragment_pop_enter, // Анимация входа при возврате
                R.anim.fragment_pop_exit // Анимация выхода при возврате
            )
            .replace(R.id.placeHolder, profileFragment)
            .addToBackStack(null) // Добавляет в стек возврата
            .commit()
    }

    fun switchToPetsFragment(username: String, type: String?, doctor: String?) {
        val petsFragment = PetsFragment().apply {
            arguments = Bundle().apply {
                putString("username", username)
                putString("type", type)
                putString("doctor", doctor)
            }
        }
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_enter, // Анимация входа
                R.anim.fragment_exit,  // Анимация выхода
                R.anim.fragment_pop_enter, // Анимация входа при возврате
                R.anim.fragment_pop_exit // Анимация выхода при возврате
            )
            .replace(R.id.placeHolder, petsFragment)
            .addToBackStack(null) // Добавляет в стек возврата
            .commit()
    }

    fun switchToHistoryFragment(username: String) {
        val historyFragment = HistoryFragment().apply {
            arguments = Bundle().apply {
                putString("username", username)
            }
        }
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_enter, // Анимация входа
                R.anim.fragment_exit,  // Анимация выхода
                R.anim.fragment_pop_enter, // Анимация входа при возврате
                R.anim.fragment_pop_exit // Анимация выхода при возврате
            )
            .replace(R.id.placeHolder, historyFragment)
            .addToBackStack(null) // Добавляет в стек возврата
            .commit()
    }

    fun switchToAddPetsFragment(username: String) {
        val addPetsFragment = AddPetsFragment().apply {
            arguments = Bundle().apply {
                putString("username", username)
            }
        }
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_enter, // Анимация входа
                R.anim.fragment_exit,  // Анимация выхода
                R.anim.fragment_pop_enter, // Анимация входа при возврате
                R.anim.fragment_pop_exit // Анимация выхода при возврате
            )
            .replace(R.id.placeHolder, addPetsFragment)
            .addToBackStack(null) // Добавляет в стек возврата
            .commit()
    }

    fun switchToBackMainFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeHolder, MainFragment())
            .addToBackStack(null) // Добавляет в стек возврата
            .commit()
    }

}

