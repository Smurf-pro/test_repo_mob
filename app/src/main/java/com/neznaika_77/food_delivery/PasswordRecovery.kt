package com.neznaika_77.food_delivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.password_recovery_layout.*

class PasswordRecovery : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.password_recovery_layout)
        mAuth = FirebaseAuth.getInstance()
        rec_pass_btn.setOnClickListener {
            recovery()
        }
        nazad.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun recovery(){
        if (pochta_for_recovery.text.toString().isNotBlank()){
            val email = pochta_for_recovery.text.toString().trim()
            mAuth!!.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@PasswordRecovery, "Check email to reset your password!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@PasswordRecovery, "Fail to send reset password email!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else{
            Toast.makeText(this, "Заполните поле <Почта> ", Toast.LENGTH_SHORT).show()
        }
    }
}