package com.example.kotlin_sqlite.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_sqlite.R
import com.example.kotlin_sqlite.domain.models.DataRecordModel
import com.example.kotlin_sqlite.domain.models.RecordStatus

class HistoryAdapter(
    private var items: List<DataRecordModel>,
    private val listener: OnItemClickListener
): RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(record: DataRecordModel)
    }

    private var recordsList:List<Pair<String,String>>? = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list:List<Pair<String,String>>){
        this.recordsList = list
        notifyDataSetChanged() // уведомляет об изменении данных, в будущем использовать notifyItemRangeInserted
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.item_history_date)
        val value: TextView = view.findViewById(R.id.item_history_namepet)
        val status: TextView = view.findViewById(R.id.item_history_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.MyViewHolder {
        //указываем с каким дизайном работаем
        val view = LayoutInflater.from(parent.context).inflate(R.layout.records_in_list, parent, false)
        return HistoryAdapter.MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //position автоматически увеличивается на 1, когда считывается новый элемент
        holder.title.text = items[position].date
        holder.value.text = items[position].name

        when(items[position].status){
            RecordStatus.WAITING.toString() -> holder.status.text = "Ожидание"
            RecordStatus.ACCEPTED.toString() -> holder.status.text = "Принята"
            RecordStatus.CANCEL.toString() -> holder.status.text = "Отклонена"
            RecordStatus.COMPLETED.toString() -> holder.status.text = "Завершена"
        }


        holder.itemView.setOnClickListener {
            listener.onItemClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}