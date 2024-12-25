package com.example.kotlin_sqlite.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.kotlin_sqlite.databinding.FragmentInPetBinding
import com.example.kotlin_sqlite.presentation.MainViewModel
import com.example.kotlin_sqlite.presentation.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InPetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentInPetBinding
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInPetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init()= with(binding) {


        arguments?.let { bundle ->
            val petName = bundle.getString("itemPetName") // Извлекаем данные по ключу
            val petGender = bundle.getString("ItemPetGender")
            val petBirth = bundle.getString("itemPetBirth")
            val petDist = bundle.getString("itemPetDist")

            petNameEditText.setText(petName)
            genderEditText.setText(petGender)
            birthDateEditText.setText(petBirth)
            distEditText.setText(petDist)
        }

        mainMenuButton.setOnClickListener {
            mainMenuButton.setOnClickListener {
                (activity as MainActivity).switchToBackMainFragment()
            }
        }

    }


    companion object {
        @JvmStatic
        fun newInstance() = InPetFragment
    }
}