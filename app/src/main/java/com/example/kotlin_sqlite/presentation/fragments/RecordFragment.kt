package com.example.kotlin_sqlite.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.kotlin_sqlite.databinding.FragmentAddRecordBinding
import com.example.kotlin_sqlite.presentation.MainViewModel
import com.example.kotlin_sqlite.presentation.activities.MainActivity
import com.example.kotlin_sqlite.repository.MainRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentAddRecordBinding
    private val model: MainViewModel by activityViewModels()

    private lateinit var username: String
    private lateinit var doctor: String
    private lateinit var petname: String
    private lateinit var date: String
    private lateinit var time: String


    @Inject
    lateinit var mainRepository: MainRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddRecordBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding) {

        arguments?.let { bundle ->
            petname = bundle.getString("itemPetName").toString() // Извлекаем данные по ключу
            doctor = bundle.getString("itemDoctor").toString()
            username = bundle.getString("itemUsername").toString()

            petNameText.text = petname
            doctorText.text = doctor
        }


        creatRecord.setOnClickListener {
            date = dateText.text.toString()
            time = timeText.text.toString()

            addRecord()
        }


        mainMenuButton.setOnClickListener {
            (activity as MainActivity).switchToBackMainFragment()
        }
    }

    private fun addRecord(){
        mainRepository.addRecord(username, petname, doctor, date, time){ isSuccess, com ->
            if (isSuccess) {
                (activity as MainActivity).switchToHistoryFragment(username)
            }
            else {
                // Обработка ошибки или отсутствие питомцев
                Toast.makeText(context, com, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecordFragment
    }
}