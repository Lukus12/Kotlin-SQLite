package com.example.kotlin_sqlite.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_sqlite.R
import com.example.kotlin_sqlite.databinding.FragmentPetsBinding
import com.example.kotlin_sqlite.domain.models.PetsClient
import com.example.kotlin_sqlite.presentation.MainViewModel
import com.example.kotlin_sqlite.presentation.activities.MainActivity
import com.example.kotlin_sqlite.presentation.adapters.PetsAdapter
import com.example.kotlin_sqlite.repository.MainRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PetsFragment : Fragment(), PetsAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentPetsBinding
    private val model: MainViewModel by activityViewModels()

    private lateinit var username: String
    private lateinit var typeFragment: String
    private lateinit var doctor: String

    @Inject
    lateinit var mainRepository: MainRepository

    private var petsAdapter: PetsAdapter? = null
    private lateinit var petsList: MutableList<PetsClient>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPetsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        fetchPetsData() // Получаем данные о питомцах
    }

    private fun init() = with(binding){
        username = arguments?.getString("username") ?: ""

        doctor = arguments?.getString("doctor") ?: ""
        typeFragment = arguments?.getString("type") ?: ""

        if(typeFragment == "select"){
            backProfileButton.isVisible = false
        }


        // Получаем имя пользователя из аргументов фрагмента
        petsList = mutableListOf()
        // устанавливаем список
        setupRecyclerView()
        petsRecyclerView.adapter = petsAdapter
        petsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        addPet.setOnClickListener {
            (activity as MainActivity).switchToAddPetsFragment(username)
        }

        backProfileButton.setOnClickListener {
            (activity as MainActivity).switchToProfileFragment(username)
        }

        mainMenuButton.setOnClickListener {
            (activity as MainActivity).switchToBackMainFragment()
        }

    }

    private fun setupRecyclerView(){
        petsAdapter = PetsAdapter(petsList, this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchPetsData() {
        mainRepository.getPets(username) { isSuccess, pets ->
            if (isSuccess && pets != null) {
                petsList.clear() // Очищаем список перед добавлением новых данных
                petsList.addAll(pets) // Добавляем полученных питомцев в список
                petsAdapter?.notifyDataSetChanged() // Уведомляем адаптер об изменениях
            } else {
                // Обработка ошибки или отсутствие питомцев
                Toast.makeText(context, "Ошибка получения данных", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClick(pet: PetsClient) {
        if(typeFragment=="select") {
            val recordFragment = RecordFragment()
            val bundle = Bundle().apply {
                putString("itemPetName", pet.name)
                putString("itemDoctor", doctor)
                putString("itemUsername", username)
            }
            recordFragment.arguments = bundle

            // Переход к новому фрагменту
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.placeHolder,
                    recordFragment
                )
                .addToBackStack(null)
                .commit()
        }
        else{
            val inPetFragment = InPetFragment()
            val bundle = Bundle().apply {
                putString("itemPetName", pet.name)
                putString("itemPetBirth", pet.dateOfBirth)
                putString("itemPetDist", pet.distinctiveFeatures)
                putString("ItemPetGender", pet.gender)
            }
            inPetFragment.arguments = bundle

            // Переход к новому фрагменту
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.placeHolder,
                    inPetFragment
                )
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PetsFragment
    }
}