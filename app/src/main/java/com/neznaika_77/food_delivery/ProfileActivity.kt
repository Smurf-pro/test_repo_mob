package com.neznaika_77.food_delivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.profile_layout.*
import com.google.firebase.database.ktx.getValue

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_layout)
       btn_out.setOnClickListener{
           FirebaseAuth.getInstance().signOut()
           val intent = Intent(this, Vxod_Regis_Activity::class.java)
           startActivity(intent)
       }
        to_menu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val databaza: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val id = FirebaseAuth.getInstance().getCurrentUser()?.getUid()
        databaza.child(id!!).child("name")
                .addValueEventListener(object :
                        ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val value = dataSnapshot.getValue<String>()
                        Log.d("harosh", "Value is: $value")
                        txt_log.text = value.toString()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.w("loshara", "Failed to read value.", error.toException())
                    }
                })
        databaza.child(id!!).child("email")
                .addValueEventListener(object :
                        ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val value = dataSnapshot.getValue<String>()
                        Log.d("harosh", "Value is: $value")
                        txt_email.text = value.toString()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.w("loshara", "Failed to read value.", error.toException())
                    }
                })
        databaza.child(id!!).child("phone")
                .addValueEventListener(object :
                        ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val value = dataSnapshot.getValue<String>()
                        Log.d("harosh", "Value is: $value")
                        txt_number.text = value.toString()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.w("loshara", "Failed to read value.", error.toException())
                    }
                })
    }
}