package com.candra.jetcoffeeapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.candra.jetcoffeeapp.R
import com.candra.jetcoffeeapp.model.Category
import com.candra.jetcoffeeapp.ui.theme.JetCoffeeAppTheme

@Composable
fun CategoryItem(
    category: Category,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(category.imageCategory),
            contentDescription = null, modifier = Modifier
                .size(60.dp)
                .clip(CircleShape))
        Text(text = stringResource(category.textCategory),
        fontSize = 10.sp, modifier = Modifier.paddingFromBaseline(top = 16.dp, bottom = 8.dp))
    }
}

@Composable
@Preview(showBackground = true)
fun CategoryItemPreview(){
    JetCoffeeAppTheme {
        CategoryItem(category = Category(
            R.drawable.icon_category_cappuccino,
            R.string.category_cappucino
        ),
        modifier = Modifier.padding(horizontal = 8.dp))
    }
}
/*
kesimpulan
Hal baru yang perlu diperhatikan pada kode diatas adalah penggunaan padding. paddingFromBaseline
digunakan untuk mengatur jarak dari batas bawah teks, bukan komponen.
Sebanarnya ada banyak cara untuk membuat UI tersebut, seperti menggunakan Spacer
ataupun padding biasa. Poin pentingnya adalah UI tersebut sesuai dengan desain yang diminta
tim desainer.

 */