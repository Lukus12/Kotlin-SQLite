package com.example.kotlin_sqlite.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.kotlin_sqlite.databinding.FragmentProfileBinding
import com.example.kotlin_sqlite.presentation.MainViewModel
import com.example.kotlin_sqlite.presentation.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val model: MainViewModel by activityViewModels()

    private lateinit var username: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init()= with(binding) {

        // Получаем имя пользователя из аргументов фрагмента
        username = arguments?.getString("username") ?: ""

        usernameUnderIcon.text = username

        petsButton.setOnClickListener {
            (activity as MainActivity).switchToPetsFragment(username, "view", null)
        }

        appointmentsButton.setOnClickListener {
            (activity as MainActivity).switchToHistoryFragment(username)
        }

        mainMenuButton.setOnClickListener {
            (activity as MainActivity).switchToBackMainFragment()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment
    }
}