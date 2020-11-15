package com.example.lab_final_test

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cus_item_layout.view.*

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val tvID = view.tv_id
    val tvName = view.tv_name
    val tvGender = view.tv_gender
    val tvTicket = view.tv_ticket
    val tvNumber = view.tv_num
    val tvPrice = view.tv_price
}