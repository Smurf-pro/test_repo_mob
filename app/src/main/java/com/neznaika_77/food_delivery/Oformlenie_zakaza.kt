package com.neznaika_77.food_delivery
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.oformlenie_zakaza_layout.*
import java.text.SimpleDateFormat
import java.util.*
class Oformlenie_zakaza : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        title = "KotlinApp"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.oformlenie_zakaza_layout)
        suum.text = intent.getStringExtra("summa")
        itg()
        oform.setOnClickListener {
            oformlenie()
        }
        }
    // различные проверки введенных пользователем данных
    private fun oformlenie() {
            if ((Surname.text.toString().isNotBlank()) && (name.text.toString().isNotBlank()) && (adress.text.toString().isNotBlank())) {
                if (isRus(Surname.text.toString())) {
                    if (isRus(name.text.toString())) {
                        if (isAdr(adress.text.toString())) {
                            if (isCom(comment.text.toString())) {
                                sendEmail()
                            } else {
                                comment.text.dropLast(1)
                                Toast.makeText(
                                        this,
                                        "В коментарии можно вводить только цифры, буквы русского и английского алфавита",
                                        Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            adress.text.dropLast(1)
                            Toast.makeText(
                                    this,
                                    "В адресе можно вводить только буквы русского алфавита",
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        name.text.dropLast(1)
                        Toast.makeText(
                                this,
                                "В имени можно вводить только буквы русского алфавита",
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Surname.text.dropLast(1)
                    Toast.makeText(
                            this,
                            "В фамилии можно вводить только буквы русского алфавита",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Поля не заполнены", Toast.LENGTH_SHORT).show()
            }
    }
//отправка почты
    private fun sendEmail() {
    try {
        val databaza: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val id = FirebaseAuth.getInstance().currentUser?.uid
        databaza.child(id!!).child("email")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val value = dataSnapshot.getValue<String>()
                    Log.d("good", "Value is: $value")
                    textView13.text = value.toString()
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("bad", "Failed to read value.", error.toException())
                }
            })
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "plain/text"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(textView13.text))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Чек")
        emailIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Дата заказа: " + currentDate + "\n" + "Покупатель: " + Surname.text + name.text + "\n" + "Адрес: " + adress.text + "\n" + "Коментарий к заказу: " + comment.text + "\n" + "Итоговая сумма заказа: " + itogo.text
        )
        this.startActivity(Intent.createChooser(emailIntent, "Sending email..."))
    }
    catch (t: Throwable) {
        Toast.makeText(this, "Request failed try again: $t", Toast.LENGTH_LONG).show()
    }    }
    // подсчет итоговой суммы
    private fun itg(){
        itogo.text = ((suum.text.toString().toInt() + 200.toString().toInt()).toString())
    }
// проверка введенных символов
    private fun isRus(string: String): Boolean {
        for (c in string)
        {
            if (c !in 'А'..'Я' && c !in 'а'..'я') {
                return false
            }
        }
        return true
    }
    private fun isAdr(string: String): Boolean {
        for (c in string)
        {
            if (c !in 'А'..'Я' && c !in 'а'..'я' && c !in ' '..' ' && c !in '0'..'9' && c !in '.'..'.') {
                return false
            }
        }
        return true
    }
    //проверка введенных символов
    private fun isCom(string: String): Boolean {
            for (c in string)
            {
                if (c !in 'А'..'Я' && c !in 'а'..'я' && c !in 'A'..'Z' && c !in 'a'..'z' && c !in '0'..'9' && c !in ' '..' ') {
                    return false
                }
            }
            return true
    }
}