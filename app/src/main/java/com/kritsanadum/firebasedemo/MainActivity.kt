package com.kritsanadum.firebasedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

      var database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //inint
        var arrData = ArrayList<Users>()
        var arrContractID = ArrayList<String>()

        var myRef = database.getReference("/tb_user")

        //get & push data
        btn_send.setOnClickListener {

            var userObj  = Users(et_username.text.toString() , et_email.text.toString())

            myRef.push().setValue(userObj)


        }

        myRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(datasnapshot: DataSnapshot) {

                //clear data
                arrData = ArrayList<Users>()
                arrContractID = ArrayList<String>()

                var arrShow = ArrayList<String>()

                var mAdapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,arrShow)
                var value = datasnapshot!!.children
                value.forEach {

                    arrContractID.add(it.key.toString())

                    arrData.add(Users(it.child("username").value.toString() ,
                        it.child("email").value.toString()) )


                    arrShow.add(it.child("username").value.toString())
                    Log.e("DATA",""+it.child("email").value)

                }

                mAdapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,arrShow)
                lv_result.adapter = mAdapter

            }

        })

        lv_result.setOnItemClickListener { parent, view, position, id ->

            var builder = AlertDialog.Builder(this@MainActivity)

            var et_edit = EditText(this)

            et_edit.setText(arrData.get(position).username)

            builder.setView(et_edit)

            builder.setTitle("แก้ไขรายการ")


            builder.setNeutralButton("แก้ไข"){dialog, which ->

                var userObj = Users(et_edit.text.toString(),arrData.get(position).email)
                myRef.child(arrContractID.get(position)).setValue(userObj)

            }

            builder.setPositiveButton("ลบ"){dialog, which ->

                myRef.child(arrContractID.get(position)).removeValue()
            }

            var dialog = builder.create()
            dialog.show()
        }

    }
}
