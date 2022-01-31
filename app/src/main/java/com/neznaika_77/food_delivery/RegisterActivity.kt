package com.neznaika_77.food_delivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.register_layout.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var authentication: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_layout)
        authentication = FirebaseAuth.getInstance()
        btn_voyti_to_acc.setOnClickListener {
            signUpUser()
        }
        btn_to_menu_fr_pro.setOnClickListener {
            val intent = Intent(this, Vxod_Regis_Activity::class.java)
            startActivity(intent)
        }
    }
    private fun signUpUser(){
        if ((edit_new_login.text.toString().isNotBlank())&&(Password.text.toString().isNotBlank())&&(EmailAddress.text.toString().isNotBlank())&&(Phone.text.toString().isNotBlank())&&(Phone.text.toString().length==11)) {
                authentication.createUserWithEmailAndPassword(
                    EmailAddress.text.toString(),
                    Password.text.toString()
                ).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext,"Пользователь зарегистрирован", Toast.LENGTH_LONG).show()
                        val databaza: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
                        val id = FirebaseAuth.getInstance().currentUser?.uid
                        databaza.child(id!!).child("name").setValue(edit_new_login.text.toString())
                        databaza.child(id!!).child("email").setValue(EmailAddress.text.toString())
                        databaza.child(id!!).child("phone").setValue(Phone.text.toString())
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(baseContext, task.exception?.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                        Log.i("signUpUser", task.exception.toString())
                    }
                }
        }
        else{
            Toast.makeText(baseContext, "Не все поля заполнены. Либо заполнены не коректно", Toast.LENGTH_SHORT).show()
        }
    }
}