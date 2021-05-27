package com.example.apicalling.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apicalling.R
import com.example.apicalling.model.User
import com.example.apicalling.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.itemlayout.view.*

class DataAdapter (val viewModel: MainViewModel,val arrayList: ArrayList<User>,val context: Context):RecyclerView.Adapter<DataAdapter.UsersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        var root = LayoutInflater.from(parent.context).inflate(R.layout.itemlayout,parent,false)
        return UsersViewHolder(root)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(arrayList.get(position))
    }

    inner class UsersViewHolder(private val binding: View): RecyclerView.ViewHolder(binding){
        fun bind(item: User)
        {
            binding.textView.text = item.first_name.plus(" ").plus(item.last_name)
            binding.textView2.text = item.email
            Glide.with(context).load(item.avatar).into(binding.imageView)

        }
    }
    fun addItems(list: ArrayList<User>){
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

}