package com.example.kotlin_sqlite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_sqlite.databinding.FragmentDoctorsBinding
import com.example.kotlin_sqlite.domain.models.Doctors
import com.example.kotlin_sqlite.presentation.MainViewModel
import com.example.kotlin_sqlite.presentation.adapters.DoctorsAdapter

class DoctorsFragment : Fragment() {
    private lateinit var binding: FragmentDoctorsBinding
    private val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemsList()
    }

    private fun initItemsList() = with(binding){

        itemsDocList.layoutManager = LinearLayoutManager(activity) // тут можно изменить то, как отображается наш список верт/гор


        val list = ArrayList<Doctors>()
        var items = Doctors(1, "Игорь Алексеевич", "Спец по кошкам","8-800-353-52-22", "https://evroportal.ru/wp-content/uploads/2016/06/897701440575bfed599a8b0.89490139.jpg")
        list.add(items)
        items = Doctors(2, "Геннадий Васильевич", "Спец по собакам","8-800-322-52-52","https://klike.net/uploads/posts/2023-07/1690340977_3-44.jpg")
        list.add(items)
        itemsDocList.adapter = DoctorsAdapter(requireContext(), list)
    }


    companion object {
        @JvmStatic
        fun newInstance() = DoctorsFragment()
    }
}