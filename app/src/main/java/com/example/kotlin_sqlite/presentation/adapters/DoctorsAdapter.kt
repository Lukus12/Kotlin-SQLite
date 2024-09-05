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
import com.example.kotlin_sqlite.presentation.activities.DoctorsItemActivity

import com.squareup.picasso.Picasso


//класс, который подставляет в поля дизайна item_in_list значения из элементов списка Item из ItemsActivity
class DoctorsAdapter(private val context: Context, private var items:List<Doctors>): RecyclerView.Adapter<DoctorsAdapter.MyViewHolder>() {

    //view - это наш дизайн, например, item_in_list.xml
    class MyViewHolder(view:View): RecyclerView.ViewHolder(view){

        val image: ImageView = view.findViewById(R.id.item_doctors_image)
        val title: TextView = view.findViewById(R.id.item_doctors_title)
        val tel: TextView = view.findViewById(R.id.item_doctors_tel)

    }
    // обрабока дизайна
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //указываем с каким дизайном работаем
        val view = LayoutInflater.from(parent.context).inflate(R.layout.doctors_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }


    //обращаемся к полям дизайна и устанавливаем значения
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //position автоматически увеличивается на 1, когда считывается новый элемент
        holder.title.text = items[position].nameDoctors
        holder.tel.text = items[position].telDoctors
        Picasso.get().load(items[position].imageURL).into(holder.image)


        holder.itemView.setOnClickListener { //передача данных на другую активити
            val intent = Intent(context, DoctorsItemActivity::class.java)

            intent.putExtra("itemDocImageURL", items[position].imageURL)
            intent.putExtra("itemDocName", items[position].nameDoctors)
            intent.putExtra("itemDocDesc", items[position].descDoctors)
            intent.putExtra("ItemDocTel", items[position].telDoctors)

            context.startActivity(intent)
        }

    }
}