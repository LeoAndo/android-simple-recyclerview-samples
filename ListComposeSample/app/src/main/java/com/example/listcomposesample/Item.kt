package com.example.listcomposesample

import androidx.compose.ui.graphics.vector.ImageVector

data class Item(
    val imageVector: ImageVector,
    var title: String,
    val price: Int,
    val amount: Int
)