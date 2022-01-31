package com.neznaika_77.food_delivery

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.corzina_layout.*

class CorzinaActivity : AppCompatActivity() {
    var cartdb:DataBaseHelper?=DataBaseHelper(this@CorzinaActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.corzina_layout)
        go()
        clear_list.setOnClickListener {
            delte()
        }
        btn_to_menu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        zakaz_oform.setOnClickListener {
            if (zakaziki.text !== "Пусто") {
                val Intent = Intent(this, Oformlenie_zakaza::class.java).apply {
                    putExtra("summa", summ_viv.text.toString())
                }
                startActivity(Intent)
            }else{
                Toast.makeText(this, "Сумма заказа 0 рублей. Добавьте что-нибудь в корзину", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun delte(){
        val deleteData:Boolean = cartdb!!.delData()
        Toast.makeText(this@CorzinaActivity,"Корзина очищена", Toast.LENGTH_LONG).show()
        zakaziki.text = "Пусто"
        summ_viv.text = "0"
    }
    private fun go(){
        val data: Cursor?=cartdb!!.showData()
        if (data!!.getCount() == 0){
            zakaziki.text = "Пусто"
            return
        }
        zakaziki.text = ""
        val buffer = StringBuffer()
        var buffer2:Int
        while(data.moveToNext()){
            buffer.append("Название: "+data.getString(1)+"\n")
            buffer.append("Цена: "+data.getString(2).toString()+" руб.\n"+"\n")
            zakaziki.text = buffer.toString()
            buffer2= summ_viv.text.toString().toInt() + data.getString(2).toString().toInt()
            summ_viv.text = buffer2.toString()
        }
    }
}