package com.example.apicalling.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apicalling.Logs
import com.example.apicalling.R
import com.example.apicalling.listener.OnItemClicked
import kotlinx.android.synthetic.main.logslayout.view.*

class LogsAdapter(val arrayList:List<Logs>,val context:Context,var itemClicked: OnItemClicked): RecyclerView.Adapter<LogsAdapter.LogsViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.logslayout,parent,false)
        return LogsViewHolder(root)
    }

    override fun getItemCount(): Int {
     return arrayList.size
    }

    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        holder.bind(arrayList.get(position))
    }
    inner class LogsViewHolder(private val binding: View): RecyclerView.ViewHolder(binding){
        @SuppressLint("SetTextI18n")
        fun bind(item: Logs)
        {

            binding.logslayouttextView.text = "url: "+item.url
            binding.logslayouttextView.setOnClickListener {
                itemClicked.onClick(adapterPosition)
            }
        }
    }
}