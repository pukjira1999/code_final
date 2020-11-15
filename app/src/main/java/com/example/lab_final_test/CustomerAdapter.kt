package com.example.lab_final_test

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CustomerAdapter(val item : List<Customer>, val context: Context): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view_item = LayoutInflater.from(parent.context).inflate(R.layout.cus_item_layout,parent,false)
        return ViewHolder(view_item)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvID.text="ID: "+item[position].cus_id
        holder.tvName.text=" Name:"+item[position].cus_name
        holder.tvGender.text="Gender: "+item[position].cus_gender
        holder.tvTicket.text="Ticket: "+item[position].ticket
        holder.tvNumber.text="Number: "+item[position].num_ticket
        holder.tvPrice.text="Price: "+item[position].total_price
    }
}