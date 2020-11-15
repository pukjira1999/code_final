package com.example.lab_final_test

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewParent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_insert.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_dialog_layout.*
import kotlinx.android.synthetic.main.add_dialog_layout.btnAdd
import kotlinx.android.synthetic.main.add_dialog_layout.edt_name
import kotlinx.android.synthetic.main.add_dialog_layout.edt_number
import kotlinx.android.synthetic.main.add_dialog_layout.radioGroup
import kotlinx.android.synthetic.main.add_dialog_layout.text_ticket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InsertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        val subjectSpinner: Spinner = ticket_spinner;
        val subjectArray = resources.getStringArray(R.array.ticket_type_spinner)

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, subjectArray)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        subjectSpinner.adapter = arrayAdapter

        subjectSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                text_ticket.text = subjectArray[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        btnAdd1.setOnClickListener() {
            addCustomer()
        }

        btnCancel1.setOnClickListener() {
            cancel()
        }
    }


    fun addCustomer() {
            val serv: CustomerAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CustomerAPI::class.java)
            var selectedId: Int = radioGroup.checkedRadioButtonId
            var radioButton: RadioButton? = findViewById(selectedId)

            var price : Int = 0;
            var number : Int = edt_number.text.toString().toInt();
            if(text_ticket.text.toString() == "A"){
                price = 2000;
            }
            if(text_ticket.text.toString() == "B"){
                price = 3000;
            }
            if(text_ticket.text.toString() == "C"){
                price = 4000;
            }
            var total_price : Int = price*number;

            serv.insertCus(
                edt_name.text.toString(),
                radioButton?.text.toString(),
                text_ticket.text.toString(),
                edt_number.text.toString().toInt(),
                total_price
            )
                .enqueue(object : Callback<Customer> {
                    override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                        if(response.isSuccessful()){
                            Toast.makeText(applicationContext,"Successfully Inserted",Toast.LENGTH_LONG).show()
                            finish()
                        }else{
                            Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Customer>, t: Throwable) {
                        Toast.makeText(applicationContext,"Error onFailure",Toast.LENGTH_LONG).show()
                    }
                })

        }

    fun cancel() {
        edt_name.text.clear()
        radioGroup.clearCheck()
        ticket_spinner.setSelection(0)
        edt_number.text.clear()
    }



}
