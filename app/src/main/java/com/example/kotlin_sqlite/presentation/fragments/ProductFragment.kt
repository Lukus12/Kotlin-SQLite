package com.example.kotlin_sqlite.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_sqlite.domain.models.Product
import com.example.kotlin_sqlite.presentation.MainViewModel
import com.example.kotlin_sqlite.presentation.adapters.ProductAdapter
import com.example.kotlin_sqlite.databinding.FragmentProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    private val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemsList()
    }

    private fun initItemsList() = with(binding){

        itemsList.layoutManager = LinearLayoutManager(activity) // тут можно изменить то, как отображается наш список верт/гор


        val list = ArrayList<Product>()
        var items = Product(1, "Косточка(игрушка)", "Прочная и вкусная, хватит на долго","200руб", "https://magizoo.ru/upload/resize_cache/iblock/37f/de9hng2pnx3gh6z99tm4d0l9awrl8dmv/1200_807_16a915b8b09d6b9a53198b0553ba36c28/TRIOL_AROMA_18CM_12191128_1.jpg")
        list.add(items)
        items = Product(2, "Сухой Корм(для кошек)", "Лучшая еда для лучших кошек","400руб","https://cdn1.ozone.ru/s3/multimedia-y/6798090022.jpg")
        list.add(items)
        itemsList.adapter = ProductAdapter(requireContext(), list)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductFragment()
    }
}