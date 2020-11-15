package com.example.lab_final_test

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_dialog_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var ticket: String = ""
    var customerList  = arrayListOf<Customer>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.adapter = CustomerAdapter(this.customerList, applicationContext)
        recycler_view.layoutManager = LinearLayoutManager(applicationContext)

        btnNextPage.setOnClickListener(){
            clickBuy()
        }

//        val subjectSpinner: Spinner = ticket_spinner;
//        val subjectArray = resources.getStringArray(R.array.ticket_type_spinner)
//
//        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, subjectArray)
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        subjectSpinner.adapter = arrayAdapter
//
//        subjectSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View,
//                position: Int,
//                id: Long
//            ) {
//                text_ticket.text = subjectArray[position]
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//        }
    }

    override fun onResume() {
        super.onResume()
        callCustomerdata()
    }

    fun callCustomerdata(){
        customerList.clear()
        Toast.makeText(applicationContext, "Failure aa", Toast.LENGTH_SHORT).show()
        val serv: CustomerAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CustomerAPI::class.java)

        Toast.makeText(applicationContext, "Failure cc", Toast.LENGTH_SHORT).show()
        serv.retrieveCustomer()
            .enqueue(object : Callback<List<Customer>> {
                override fun onResponse(
                    call: Call<List<Customer>>,
                    response: Response<List<Customer>>
                ) {
                    Toast.makeText(applicationContext, "Failure bb", Toast.LENGTH_SHORT).show()
                    response.body()?.forEach{
                        customerList.add(Customer(it.cus_id,it.cus_name,it.cus_gender,it.ticket,it.num_ticket,it.total_price))
                    }
                    recycler_view.adapter= CustomerAdapter(customerList,applicationContext)
                    Toast.makeText(applicationContext, "test", Toast.LENGTH_SHORT).show()
                    text1.text = "Customer List : "+customerList.size.toString()+"Customer"
                }

                override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
                    return t.printStackTrace()
                    Toast.makeText(applicationContext, "Failure", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun clickBuy(){
        val intent = Intent(this, InsertActivity::class.java)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.item1 ->{
                    addCustomer()
                return true
            }
            else ->return super.onOptionsItemSelected(item)
        }
    }

    fun addCustomer() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.add_dialog_layout,null)
        val myBuilder = AlertDialog.Builder(this)
        myBuilder.setView(mDialogView)

        val mAlertDialog = myBuilder.show()
        mAlertDialog.btnAdd.setOnClickListener(){
            val serv: CustomerAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CustomerAPI::class.java)
            var selectedId: Int = mAlertDialog.radioGroup.checkedRadioButtonId
            var radioButton: RadioButton? = mAlertDialog.findViewById(selectedId)

            var price : Int = 0;
            var number = mAlertDialog.edt_number.text.toString().toInt();
            if(mAlertDialog.edt_ticket.text.toString() == "A"){
                price = 2000;
            }
            if(mAlertDialog.edt_ticket.text.toString() == "B"){
                price = 3000;
            }
            if(mAlertDialog.edt_ticket.text.toString() == "C"){
                price = 4000;
            }
            var total_price : Int = price*number;

            serv.insertCus(
                mAlertDialog.edt_name.text.toString(),
                radioButton?.text.toString(),
                mAlertDialog.edt_ticket.text.toString(),
                mAlertDialog.edt_number.text.toString().toInt(),
                total_price
            )
                .enqueue(object : Callback<Customer> {
                    override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                        if(response.isSuccessful()){
                            Toast.makeText(applicationContext,"Successfully Inserted",Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Customer>, t: Throwable) {
                        Toast.makeText(applicationContext,"Error onFailure",Toast.LENGTH_LONG).show()
                    }
                })
            recycler_view.adapter?.notifyDataSetChanged()
            mAlertDialog.dismiss()
        }
        mAlertDialog.btnCancel.setOnClickListener(){
            mAlertDialog.dismiss()
        }
    }

}