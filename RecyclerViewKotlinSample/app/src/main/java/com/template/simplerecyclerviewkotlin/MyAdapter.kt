package com.template.simplerecyclerviewkotlin

import android.icu.text.NumberFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

typealias OnItemClickListener<T> = (T) -> Unit

class MyAdapter(
    val items: MutableList<Item>,
    private val onItemClick: OnItemClickListener<Int>,
    private val onItemLongClick: OnItemClickListener<String>
) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    val totalPrice get() = items.sumBy { it.price * it.amount }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        Log.d("aaa", "getItemCount: " + items.size)
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = items[position]
        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textTitle.text = currentItem.title
        val numberFormat = NumberFormat.getCurrencyInstance()
        holder.textTotalPrice.text = numberFormat.format(currentItem.price * currentItem.amount)
    }

    fun insertItem(index: Int, newItem: Item) {
        items.add(index, newItem)
        notifyItemInserted(index) // 変更がある箇所だけ差分更新する.
        // notifyDataSetChanged() // これだとリスト全体の更新が走る.
    }

    fun removeItem(index: Int) {
        items.removeAt(index)
        notifyItemRemoved(index)
    }

    fun updateItem(position: Int) {
        items[position].apply {
            this.title = "update: ${this.title}"
        }
        notifyItemChanged(position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.image_view)!!
        val textTitle = itemView.findViewById<TextView>(R.id.text_title)!!
        val textTotalPrice = itemView.findViewById<TextView>(R.id.text_total_price)!!

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(position)
                }
            }
            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemLongClick("$position Item Long Clicked!!")
                }
                return@setOnLongClickListener false
            }
        }
    }
}