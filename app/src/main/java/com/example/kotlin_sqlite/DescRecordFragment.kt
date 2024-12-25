package com.example.kotlin_sqlite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.kotlin_sqlite.databinding.FragmentDescRecordBinding
import com.example.kotlin_sqlite.databinding.FragmentHistoryBinding
import com.example.kotlin_sqlite.presentation.MainViewModel
import com.example.kotlin_sqlite.presentation.activities.MainActivity
import com.example.kotlin_sqlite.presentation.fragments.HistoryFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescRecordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentDescRecordBinding
    private val model: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding){
        arguments?.let { bundle ->
            val petName = bundle.getString("itemPetName") // Извлекаем данные по ключу
            val doctor = bundle.getString("itemDoctor")
            val date = bundle.getString("itemDate")
            val time = bundle.getString("itemTime")

            petNameRecordText.text = petName
            doctorRecordText.text = doctor
            dateRecordText.text = date
            timeRecordText.text = time
        }

        mainMenuButton.setOnClickListener {
            mainMenuButton.setOnClickListener {
                (activity as MainActivity).switchToBackMainFragment()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment
    }
}