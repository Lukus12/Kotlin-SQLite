package com.example.kotlin_sqlite.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_sqlite.presentation.MainViewModel
import com.example.kotlin_sqlite.databinding.FragmentServicesBinding
import com.example.kotlin_sqlite.domain.models.Services
import com.example.kotlin_sqlite.presentation.adapters.ServicesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServicesFragment : Fragment() {
    private lateinit var binding: FragmentServicesBinding
    private val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServicesBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemsList()
    }

    private fun initItemsList() = with(binding){

        itemsListServices.layoutManager = LinearLayoutManager(activity) // тут можно изменить то, как отображается наш список верт/гор

        val list = ArrayList<Services>()
        var items = Services(1, "Стрижка когтей", "Точить больше будет нечего", "250руб")
        list.add(items)
        items = Services(2, "Диагностика", "Найдём причины беспокойства вашего питомца","300руб")
        list.add(items)
        itemsListServices.adapter = ServicesAdapter(requireContext(), list)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ServicesFragment()
    }
}