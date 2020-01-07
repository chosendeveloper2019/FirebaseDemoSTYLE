package com.kritsanadum.firebasedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    private lateinit var database: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        database = FirebaseDatabase.getInstance().reference


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                val posts = ArrayList<Users>()
                for (snapshot in dataSnapshot.children) {
                    val users = snapshot.getValue(Users::class.java)
                    posts.add(users!!)
                }

                Log.e("Result"," MSG "+ posts.size)



                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("Error", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        database.addValueEventListener(postListener)



        btn_send.setOnClickListener {

            var getName = et_input.text.toString()

            var users =  Users("Kritsana "+getName,"email1@email.com")


            database.child("tb_user").push().setValue(users)

        }




    }
}
