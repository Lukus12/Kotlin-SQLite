package com.example.kotlin_sqlite.presentation.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_sqlite.R
import com.example.kotlin_sqlite.domain.models.PetsClient


class PetsAdapter(
    private var items:List<PetsClient>,
    private val listener: OnItemClickListener
): RecyclerView.Adapter<PetsAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(pet: PetsClient)
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.item_pets_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsAdapter.MyViewHolder {
        //указываем с каким дизайном работаем
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pets_in_list, parent, false)
        return PetsAdapter.MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //position автоматически увеличивается на 1, когда считывается новый элемент
        holder.title.text = items[position].name

        holder.itemView.setOnClickListener {
            listener.onItemClick(items[position])
        }

    }

    override fun getItemCount(): Int {
        return items.count()
    }

}