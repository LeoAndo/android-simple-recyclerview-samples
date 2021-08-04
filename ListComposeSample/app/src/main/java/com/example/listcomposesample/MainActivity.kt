package com.example.listcomposesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.listcomposesample.ui.theme.ListComposeSampleTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListComposeSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen(
                        items = viewModel.items.value,
                        onAddItem = viewModel::insertItem,
                        onRemoveItem = viewModel::removeItem,
                        onUpdateItem = viewModel::updateItem,
                    )
                }
            }
        }
    }
}