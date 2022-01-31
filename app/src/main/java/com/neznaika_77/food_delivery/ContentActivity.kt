package com.neznaika_77.food_delivery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_content_layout.*

class ContentActivity : AppCompatActivity() {
    var cartDB: DataBaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_layout)
        nazv_card.text = intent.getStringExtra("title")
        text_card.text = intent.getStringExtra("content")
        price_card.text = intent.getStringExtra("price")
        foto_card.setImageResource(intent.getIntExtra("image", R.drawable.ic_launcher_foreground))
        pokupochka.setOnClickListener {
            add(intent.getStringExtra("price").toString(), intent.getStringExtra("title").toString())
        }
    }

    private fun add(priice: String, namee: String) {
        var user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            cartDB = DataBaseHelper(this@ContentActivity)
            val insertData: Boolean = cartDB!!.addData(namee, priice)
            if (insertData == true) {
                Toast.makeText(baseContext, namee + " добавлено в корзину", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@ContentActivity, "Что-то пошло не так!", Toast.LENGTH_SHORT).show()
            }
            Log.e("warning", "mistake")
            } else {
                // No user is signed in.
                Toast.makeText(baseContext, "Войдите или зарегистрируйтесь",Toast.LENGTH_LONG).show()
                val intent = Intent(this, Vxod_Regis_Activity::class.java)
                startActivity(intent)
            }
        }
    }