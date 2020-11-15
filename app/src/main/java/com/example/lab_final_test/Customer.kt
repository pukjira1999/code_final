package com.example.lab_final_test

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Customer(
    @Expose
    @SerializedName("cus_id") val cus_id: Int,

    @Expose
    @SerializedName("cus_name") val cus_name: String,

    @Expose
    @SerializedName("cus_gender") val cus_gender: String,

    @Expose
    @SerializedName("ticket") val ticket: String,

    @Expose
    @SerializedName("num_ticket") val num_ticket: Int,

    @Expose
    @SerializedName("total_price") val total_price: Int
) {}