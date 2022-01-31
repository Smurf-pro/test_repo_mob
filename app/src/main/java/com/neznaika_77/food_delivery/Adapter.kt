package com.neznaika_77.food_delivery

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

// адаптер для recyclerview
class Adapter ( listArray:ArrayList<ListItem>,context: Context): RecyclerView.Adapter<Adapter.ViewHolder>() {
    var listarrayq = listArray
    var contextT = context
    // класс который берет элементы из item_layout и заполняет их данными
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val Titl = view.findViewById<TextView>(R.id.nameeCart)
        val cont = view.findViewById<TextView>(R.id.textView3)
        val image = view.findViewById<ImageView>(R.id.imageView2)
        val Price = view.findViewById<TextView>(R.id.priceeCart)
// функция будет заполнять данными элементы item_layout
        fun bind(listItem: ListItem,context: Context){
            Titl.text = listItem.titleText
             val textcon = listItem.contentText.substring(0,40) + "..."
             cont.text = textcon
         //   cont.text = listItem.contentText
            Price.text = listItem.priceText.toString()
            image.setImageResource(listItem.image_id)

            itemView.setOnClickListener {
                Toast.makeText(context, " ${Titl.text}", Toast.LENGTH_SHORT).show()
                val i = Intent(context, ContentActivity::class.java).apply {
                    putExtra("title", Titl.text.toString())
                     putExtra("content", listItem.contentText)
                  //  putExtra("content", cont.text.toString())
                    putExtra("price",Price.text.toString())
                    putExtra("image", listItem.image_id)
                }
                context.startActivity(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(contextT)
        return ViewHolder(inflater.inflate(R.layout.item_layout, parent, false))
    }
    // Этот класс определяет сколько элементов адаптер будет выводить в recyclerview
    override fun getItemCount(): Int {
        return listarrayq.size
    }
// функция которая по позиции берет данные и заполняет элементы ListItem
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItems = listarrayq.get(position)
        holder.bind(listItems,contextT)
    }
    // функция для обновления списка товаров
    fun updateAdapter(listArray: List<ListItem>){
        // чистим массив
        listarrayq.clear()
        // передаем список товаров
        listarrayq.addAll(listArray)
        // сообщает адаптеру об изменении данных
        notifyDataSetChanged()
    }
}