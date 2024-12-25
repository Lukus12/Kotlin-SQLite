package com.example.kotlin_sqlite.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.kotlin_sqlite.R
import com.example.kotlin_sqlite.databinding.FragmentAddPetsBinding
import com.example.kotlin_sqlite.databinding.FragmentHistoryBinding
import com.example.kotlin_sqlite.domain.models.PetsClient
import com.example.kotlin_sqlite.presentation.MainViewModel
import com.example.kotlin_sqlite.presentation.activities.MainActivity
import com.example.kotlin_sqlite.repository.MainRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddPetsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentAddPetsBinding
    private val model: MainViewModel by activityViewModels()

    private lateinit var username: String

    private lateinit var petname: String
    private lateinit var petdist: String
    private lateinit var petdate: String
    private lateinit var petgen: String

    @Inject
    lateinit var mainRepository: MainRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPetsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init()= with(binding) {

        // Получаем имя пользователя из аргументов фрагмента
        username = arguments?.getString("username") ?: ""

        acceptAddPetButton.setOnClickListener {
            petname = petNameEditText.text.toString()
            petgen = genderEditText.text.toString()
            petdate = birthDateEditText.text.toString()
            petdist = distEditText.text.toString()

            addPetToClient()
        }
    }


    private fun addPetToClient(){
        mainRepository.addPets(username, petname, petdist, petdate, petgen){ isSuccess, com ->
            if (isSuccess) {
                (activity as MainActivity).switchToPetsFragment(username, "view", null)
            }
            else {
                // Обработка ошибки или отсутствие питомцев
                Toast.makeText(context, com, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddPetsFragment
    }
}