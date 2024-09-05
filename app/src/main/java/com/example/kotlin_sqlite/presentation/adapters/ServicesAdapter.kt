package com.example.kotlin_sqlite.presentation.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_sqlite.R
import com.example.kotlin_sqlite.domain.models.Services
import com.example.kotlin_sqlite.presentation.activities.ProductItemActivity
import com.squareup.picasso.Picasso


//класс, который подставляет в поля дизайна item_in_list значения из элементов списка Item из ItemsActivity
class ServicesAdapter(private val context: Context, private var items:List<Services>): RecyclerView.Adapter<ServicesAdapter.MyViewHolder>() {

    //view - это наш дизайн, например, services_in_list.xml
    class MyViewHolder(view:View): RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.item_services_title)
        val price: TextView = view.findViewById(R.id.item_services_price)
    }
    // обрабока дизайна
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //указываем с каким дизайном работаем
        val view = LayoutInflater.from(parent.context).inflate(R.layout.services_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }


    //обращаемся к полям дизайна и устанавливаем значения
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //position автоматически увеличивается на 1, когда считывается новый элемент
        holder.title.text = items[position].nameServices
        holder.price.text = items[position].priceServices


        holder.itemView.setOnClickListener { //передача данных на другую активити
            val intent = Intent(context, ProductItemActivity::class.java)

            intent.putExtra("itemPrice", items[position].priceServices)
            intent.putExtra("itemName", items[position].nameServices)
            intent.putExtra("itemText", items[position].descServices)

            context.startActivity(intent)
        }

    }
}