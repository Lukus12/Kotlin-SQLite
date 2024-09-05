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
import com.example.kotlin_sqlite.domain.models.Doctors
import com.example.kotlin_sqlite.domain.models.Product
import com.example.kotlin_sqlite.presentation.activities.ProductItemActivity
import com.squareup.picasso.Picasso


//класс, который подставляет в поля дизайна item_in_list значения из элементов списка Item из ItemsActivity
class ProductAdapter(private val context: Context, private var items: ArrayList<Product>): RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    //view - это наш дизайн, например, item_in_list.xml
    class MyViewHolder(view:View): RecyclerView.ViewHolder(view){

        val image: ImageView = view.findViewById(R.id.item_list_image)
        val title: TextView = view.findViewById(R.id.item_list_title)
        val price: TextView = view.findViewById(R.id.item_price)

    }
    // обрабока дизайна
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //указываем с каким дизайном работаем
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }


    //обращаемся к полям дизайна и устанавливаем значения
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //position автоматически увеличивается на 1, когда считывается новый элемент
        holder.title.text = items[position].nameProduct
        holder.price.text = items[position].price
        Picasso.get().load(items[position].imageURL).into(holder.image)


       holder.itemView.setOnClickListener { //передача данных на другую активити
            val intent = Intent(context, ProductItemActivity::class.java)

            intent.putExtra("itemImageURL", items[position].imageURL)
            intent.putExtra("itemName", items[position].nameProduct)
            intent.putExtra("itemText", items[position].desc)
            intent.putExtra("ItemPrice", items[position].price)

            context.startActivity(intent)
        }

    }
}