package com.example.kotlin_sqlite.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_sqlite.DescRecordFragment
import com.example.kotlin_sqlite.R
import com.example.kotlin_sqlite.databinding.FragmentHistoryBinding
import com.example.kotlin_sqlite.domain.models.DataRecordModel
import com.example.kotlin_sqlite.presentation.MainViewModel
import com.example.kotlin_sqlite.presentation.activities.MainActivity
import com.example.kotlin_sqlite.presentation.adapters.HistoryAdapter
import com.example.kotlin_sqlite.repository.MainRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : Fragment(), HistoryAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentHistoryBinding
    private val model: MainViewModel by activityViewModels()

    @Inject
    lateinit var mainRepository: MainRepository

    private lateinit var username: String

    private var historyAdapter: HistoryAdapter? = null
    private lateinit var recordList: MutableList<DataRecordModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recordList = mutableListOf() // Инициализация списка записей
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        //fetchRecordData()
        subscribeObservers()
    }

    private fun init() = with(binding){
        username = arguments?.getString("username") ?: ""

        setupRecyclerView()

        backProfileButton.setOnClickListener {
            (activity as MainActivity).switchToProfileFragment(username)
        }

        mainMenuButton.setOnClickListener {
            (activity as MainActivity).switchToBackMainFragment()
        }
    }

    private fun subscribeObservers() = with(binding) {
        mainRepository.observeRecordsStatus { records ->
            Log.d("HistoryFragment", "Updated records: $records")
            updateRecordList(records) // Обновляем список записей
        }
    }

    private fun setupRecyclerView(){
        historyAdapter = HistoryAdapter(recordList, this)
        binding.historyRecyclerView.adapter = historyAdapter
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRecordList(records: List<DataRecordModel>) {
        recordList.clear() // Очищаем текущий список
        recordList.addAll(records) // Добавляем новые записи в список
        historyAdapter?.notifyDataSetChanged() // Уведомляем адаптер об изменениях
    }

    override fun onItemClick(record: DataRecordModel) {
        val descRecordFragment = DescRecordFragment()
        val bundle = Bundle().apply {
            putString("itemPetName", record.name)
            putString("itemDoctor", record.doctor)
            putString("itemDate", record.date)
            putString("itemTime", record.time)
            //putString("itemHistory", record.history)
            putString("itemUsername", username)
        }
        descRecordFragment.arguments = bundle

        // Переход к новому фрагменту
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.placeHolder,
                descRecordFragment
            )
            .addToBackStack(null)
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment
    }

}