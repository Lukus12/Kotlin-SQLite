package com.example.kotlin_sqlite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//класс, который подставляет в поля дизайна item_in_list значения из элементов списка Item из ItemsActivity
class ItemsAdapter(var items:List<Item>, var context:Context): RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    //view - это наш дизайн, например, item_in_list.xml
    class MyViewHolder(view:View): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.item_list_image)
        val title: TextView = view.findViewById(R.id.item_list_title)
        val desc: TextView = view.findViewById(R.id.item_list_desc)
        val price: TextView = view.findViewById(R.id.item_list_price)
    }
    // обрабока дизайна
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //указываем с каким дизайном работаем
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    //обращаемся к полям дизайна и устанавливаем значения
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //position автоматически увеличивается на 1, когда считывается новый элемент
        holder.title.text = items[position].title
        holder.desc.text = items[position].desc
        holder.price.text = items[position].price.toString() + " руб"

        //получаем id изображений, просканировав папку в которой они лежат
        val imageId = context.resources.getIdentifier(
            items[position].image, //название изображения
            "drawable", //папка для поиска
            context.packageName //название пакета
        )
        //устанавливаем изображение по id
        holder.image.setImageResource(imageId)
    }
}