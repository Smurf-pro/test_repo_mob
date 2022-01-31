package com.neznaika_77.food_delivery
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_layout.*
class LoginActivity : AppCompatActivity() {
    private lateinit var authentication: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        authentication = FirebaseAuth.getInstance()
        btn_voyti_to_acc.setOnClickListener {
            signInUser()
        }
        btn_to_menu_fr_pro.setOnClickListener {
            val intent = Intent(this,Vxod_Regis_Activity::class.java)
            startActivity(intent)
        }
        forgotPassword.setOnClickListener {
            val intent = Intent(this,PasswordRecovery::class.java)
            startActivity(intent)
        }
    }
    private fun signInUser(){
        if ((edit_new_login.text.toString().isNotBlank())&&(edit_password.text.toString().isNotBlank())) {
            authentication.signInWithEmailAndPassword(edit_new_login.text.toString(), edit_password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "Пользователь авторизован", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(this,ProfileActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(baseContext, "Неверно введены данные", Toast.LENGTH_LONG).show()
                    }
                }
        }
        else{
            Toast.makeText(baseContext, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
        }
    }
}