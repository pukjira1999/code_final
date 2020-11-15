package com.example.lab_final_test

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface CustomerAPI {
    @GET("allcus")
    fun retrieveCustomer(): Call<List<Customer>>

    @FormUrlEncoded
    @POST("cus")
    fun insertCus(
        @Field("cus_name") cus_name : String,
        @Field("cus_gender") cus_gender : String,
        @Field("ticket") ticket : String,
        @Field("num_ticket") num_ticket : Int,
        @Field("total_price") total_price : Int
    ): Call<Customer>
}