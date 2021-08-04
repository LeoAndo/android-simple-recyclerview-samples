package com.example.listcomposesample

import android.icu.text.NumberFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.EmojiFoodBeverage
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun MainScreen(
    items: List<Item>,
    onAddItem: () -> Unit,
    onRemoveItem: () -> Unit,
    onUpdateItem: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar {
                Text(text = stringResource(id = R.string.app_name))
            }
        }
    ) {
        // content
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            val (listView, totalPrice, insertItemButton, removeItemButton) = createRefs()
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier
                    .constrainAs(listView) {
                        bottom.linkTo(totalPrice.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            ) {
                itemsIndexed(items = items, itemContent = { index, item ->
                    ItemRow(index, item, onItemClick = onUpdateItem)
                })
            }
            Text(
                text = formatTotalPrice(items),
                style = TextStyle(fontSize = 24.sp),
                modifier = Modifier.constrainAs(totalPrice) {
                    bottom.linkTo(insertItemButton.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
            )
            Button(onClick = { onAddItem() },
                modifier = Modifier
                    .constrainAs(insertItemButton) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(removeItemButton.start)
                    }
                    .padding(bottom = 8.dp)
            ) {
                Text(text = stringResource(id = R.string.insert_item_button))
            }
            Button(onClick = { onRemoveItem() },
                modifier = Modifier
                    .constrainAs(removeItemButton) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(insertItemButton.end)
                    }
                    .padding(bottom = 8.dp)
            ) {
                Text(text = stringResource(id = R.string.remove_item_button))
            }
            createHorizontalChain(
                insertItemButton,
                removeItemButton,
                chainStyle = ChainStyle.Spread
            )
        }
    }
}

private fun formatTotalPrice(items: List<Item>): String {
    val totalPrice = items.sumBy { it.price * it.amount }
    val format = NumberFormat.getCurrencyInstance()
    return format.format(totalPrice)
}

@Composable
fun ItemRow(index: Int, item: Item, onItemClick: (Int) -> Unit) {
    val numberFormat = NumberFormat.getCurrencyInstance()
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .clickable { onItemClick(index) }
                .padding(8.dp)
        ) {
            // Create references for the composables to constrain
            val (image, title, price) = createRefs()
            Image(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(end = 8.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                imageVector = item.imageVector,
                contentDescription = ""
            )
            Text(
                text = item.title,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(image.end)
                })
            Text(text = numberFormat.format(item.price * item.amount),
                modifier = Modifier.constrainAs(price) {
                    top.linkTo(title.bottom)
                    start.linkTo(image.end)
                })
        }
    }
}

val dummyItems =
    listOf(
        Item(Icons.Default.EmojiFoodBeverage, "beverage", 1000, 1),
        Item(Icons.Default.Cake, "cake", 1000, 3),
        Item(Icons.Default.Fastfood, "fastFood", 1000, 5),
        Item(Icons.Default.FoodBank, "foodBank", 1000, 10)
    )

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen(items = dummyItems, onAddItem = {}, onRemoveItem = {}, onUpdateItem = {})
}

@Preview
@Composable
fun PreviewItemRow() {
    ItemRow(index = 0, item = dummyItems.first(), onItemClick = {})
}
