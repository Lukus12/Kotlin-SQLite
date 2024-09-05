package com.example.kotlin_sqlite.presentation.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlin_sqlite.domain.models.Product
import com.example.kotlin_sqlite.presentation.MainViewModel
import com.example.kotlin_sqlite.presentation.adapters.ProductAdapter
import com.example.kotlin_sqlite.presentation.adapters.VpAdapter
import com.example.kotlin_sqlite.databinding.FragmentMainBinding
import com.example.kotlin_sqlite.fragments.ServicesFragment
import com.example.kotlin_sqlite.fragments.DoctorsFragment
import com.google.android.material.tabs.TabLayoutMediator


class MainFragment : Fragment() {

    private val fList = listOf(
        ProductFragment.newInstance(),
        ServicesFragment.newInstance(),
        DoctorsFragment.newInstance()
    )
    private val tList = listOf(
        "Товар",
        "Услуги",
        "Врачи"
    )

    private lateinit var binding: FragmentMainBinding
    private val model: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    //функция инициализации
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
       // parseData()

    }
    //переключение вкладок
    private fun init()= with(binding){


        search.setIconifiedByDefault(false)

        val adapter = VpAdapter(activity as FragmentActivity, fList)
        vp.adapter = adapter
        TabLayoutMediator(tabLayout, vp){
                tab, pos -> tab.text = tList[pos]
        }.attach()


        vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
// Здесь можно добавить код обновления фрагмента при его переключении
                val fragment = fList[position]
                if (fragment is ProductFragment) {
                    Log.d("MyLog", "all")
                   // model.favData.value = ProductAdapter.fafv.asd
                    model.liveDataList.value = ll
                    /*if (arr.isEmpty() and arrf.isEmpty()) {
                        model.favData.value = ItemsAdapter.fafv.asd
                        model.liveDataList.value = ll
                    }
                    else {
                        model.favData.value = arrf
                        model.liveDataList.value = arr
                    }*/



                } else if (fragment is ServicesFragment) {
                    Log.d("MyLog", "fav")
                    if (arr.isEmpty() and arrf.isEmpty()) {
                      //  model.favData.value = ProductAdapter.fafv.asd
                        model.liveDataList.value = ll
                    }
                    else {
                        model.favData.value = arrf
                        model.liveDataList.value = arr
                    }
                }

            }
        })

    }



    /*private fun parseData():List<Product>{

        val list = ArrayList<Product>()
        val item = Product(1, "Potato", "https://cookifi.com/blog/wp-content/uploads/2018/06/potato-hd-png-potato-png-1707.png")

        list.add(item)


        model.liveDataList.value = list

        searchCh(list)
        ll = list
        return list
    }*/



    private fun searchCh(list: ArrayList<Product>) {
        /*val arr = ArrayList<Item>()
        val arrf= ArrayList<Item>()*/

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(qwe: String?): Boolean {
                /* arr.clear()
                 for (item in list) {
                     if (item.nameTV.toLowerCase().contains(qwe.toString().toLowerCase())) {
                         arr.add(item)
                         //Toast.makeText(activity, "$arr", Toast.LENGTH_SHORT).show()
                     }
                 }
                 model.liveDataList.value = arr
                 model.favData.value = arr
                 //Toast.makeText(activity, "$arr", Toast.LENGTH_SHORT).show()*/
                return false
            }

            override fun onQueryTextChange(zxc: String?): Boolean {
                arr.clear()
                for (item in list) {
                    if (item.nameProduct.toLowerCase().contains(zxc.toString().toLowerCase())) {
                        arr.add(item)
                    }
                    //Toast.makeText(activity, "Поиск по содержанию", Toast.LENGTH_SHORT).show()

                }
                model.liveDataList.value = arr
                Log.d("live", "${model.liveDataList.value}")
                arrf.clear()

                if (zxc == "") {
                   // model.favData.value = ProductAdapter.fafv.asd
                    model.liveDataList.value = list
                }
                model.favData.value = arrf
                //Toast.makeText(activity, "$arr", Toast.LENGTH_SHORT).show()
                return false
            }

        })

    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
        var ll = ArrayList<Product>()
        val arr = ArrayList<Product>()
        val arrf= ArrayList<Product>()
    }
}