package com.example.listcomposesample

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.EmojiFoodBeverage
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val dummyItems =
        listOf(
            Item(Icons.Default.EmojiFoodBeverage, "beverage", 1000, 1),
            Item(Icons.Default.Cake, "cake", 1000, 3),
            Item(Icons.Default.Fastfood, "fastFood", 1000, 5),
            Item(Icons.Default.FoodBank, "foodBank", 1000, 10)
        )
    var items = mutableStateOf(generateDummyItems())
        private set

    fun insertItem() {
        val until = if (items.value.isEmpty()) 1 else items.value.size
        val index = Random.nextInt(until)
        val randomItem = dummyItems.random()
        val newItem = randomItem.let {
            Item(
                it.imageVector,
                "insert: ${it.title}",
                it.price,
                it.amount
            )
        }
        items.value = items.value.toMutableList().also { it.add(index, newItem) }
    }

    fun removeItem() {
        if (items.value.isEmpty()) {
            Log.d(MainViewModel::class.simpleName, "list is empty and cannot be deleted.")
            return
        }
        val index = Random.nextInt(items.value.size)
        items.value = items.value.toMutableList().also { it.removeAt(index) }
    }

    fun updateItem(index: Int) {
        items.value[index].apply {
            this.title = "update: ${this.title}"
        }
    }

    private fun generateDummyItems(size: Int = 10): List<Item> {
        val items: ArrayList<Item> = arrayListOf()
        for (i in 0 until size) {
            val randomItem = dummyItems.random()
            items.add(
                randomItem.let {
                    Item(
                        it.imageVector,
                        it.title,
                        it.price,
                        it.amount
                    )
                }
            )
        }
        return items
    }
}