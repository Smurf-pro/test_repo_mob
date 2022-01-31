package com.neznaika_77.food_delivery
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*
import java.util.*
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var adapter: Adapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.setNavigationItemSelectedListener  (this)
        val liist = ArrayList<ListItem>()
       liist.addAll(fillArrays(resources.getStringArray(R.array.Pizza), // выводим из массивов данные которые буду отображаться по умолчанию при открытии проги
               resources.getStringArray(R.array.Pizza_Content), resources.getIntArray(R.array.Pizza_price), getImageId(R.array.Pizza_Foto)))
        recView.hasFixedSize()
        recView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(liist, this)
        recView.adapter = adapter
    }
// выгруза элементов панели
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
}
// обработка мнажатия по панеле
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        return when (item.itemId) {
            R.id.item1 -> {
                //val intent = Intent(this, MainActivity::class.java)
               // startActivity(intent)
                Toast.makeText(this, "Меню", Toast.LENGTH_SHORT).show()
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
//    Обработка нажатий меню.
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
        R.id.id_pizza -> {
            Toast.makeText(this, "Пицца", Toast.LENGTH_SHORT)
                .show()
            adapter?.updateAdapter(fillArrays(resources.getStringArray(R.array.Pizza),
                resources.getStringArray(R.array.Pizza_Content), resources.getIntArray(R.array.Pizza_price), getImageId(R.array.Pizza_Foto)))
        }
        R.id.id_burgers -> {
            Toast.makeText(this, "Бургеры", Toast.LENGTH_SHORT).show()
            adapter?.updateAdapter(fillArrays(resources.getStringArray(R.array.Burgers),
                resources.getStringArray(R.array.Burgers_Content), resources.getIntArray(R.array.Burgers_price), getImageId(R.array.Burgers_Foto)))
        }
        R.id.id_hot_dogs -> {
            Toast.makeText(this, "Хот-доги", Toast.LENGTH_SHORT).show()
            adapter?.updateAdapter(fillArrays(resources.getStringArray(R.array.HotDogs),
                resources.getStringArray(R.array.HotDogs_Content), resources.getIntArray(R.array.HotDogs_price), getImageId(R.array.HotDogs_Foto)))
        }
        R.id.id_donuts -> {
            Toast.makeText(this, "Пончики", Toast.LENGTH_SHORT).show()
            adapter?.updateAdapter(fillArrays(resources.getStringArray(R.array.Donuts),
                resources.getStringArray(R.array.Donuts_Content), resources.getIntArray(R.array.Donuts_price), getImageId(R.array.Donuts_Foto)))
        }
        R.id.Profile ->{
            Toast.makeText(this, "Профиль", Toast.LENGTH_SHORT).show()
            if (FirebaseAuth.getInstance().currentUser == null) {
                val intent1 = Intent(this, Vxod_Regis_Activity::class.java)
                startActivity(intent1)
            }
            else{
                val intent2 = Intent(this, ProfileActivity::class.java)
                startActivity(intent2)
            }
            }
        R.id.Korzinaaaa ->{
            Toast.makeText(this, "Корзина", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CorzinaActivity::class.java)
            startActivity(intent)
        }
    }
    drawerLayout.closeDrawer(GravityCompat.START)
    return true
    }

// функция которая будет заполнять массив. передаем 3 массива.
private fun fillArrays(
            titleArray: Array<String>,
            contentArray: Array<String>,
            priceArray:IntArray,
            imageArray: IntArray
    ): List<ListItem> {
        val listItemArray = ArrayList<ListItem>()
        for (n in titleArray.indices) // цикл для заполнения.
        {
            val listItem = ListItem(imageArray[n], titleArray[n], contentArray[n], priceArray[n]) // переменная для отдельного элемента
            listItemArray.add(listItem)
        }
        return listItemArray
    }
// функция для расшифровки идентификатора картинок из массива
private fun getImageId(imageArrayId:Int):IntArray {
        val tarray: TypedArray = resources.obtainTypedArray(imageArrayId) // тут записаны все идентификаторы
        val cont = tarray.length()
        val ids = IntArray(cont) // массив для готовых идентификаторов
        for(i in ids.indices){ // достаем ресур и переводим его в int
            ids[i] = tarray.getResourceId(i,0)
        }
        tarray.recycle() // возможноть переиспользовать
        return ids
    }
}