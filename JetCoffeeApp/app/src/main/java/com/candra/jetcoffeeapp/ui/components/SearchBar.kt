package com.candra.jetcoffeeapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.candra.jetcoffeeapp.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.candra.jetcoffeeapp.ui.theme.JetCoffeeAppTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
){
    TextField(value = "", 
        onValueChange = {}, leadingIcon = {
          Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        colors= TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(text = stringResource(id = R.string.placeholder_search))
        },
    modifier = modifier.padding(16.dp).fillMaxWidth().heightIn(min = 48.dp).clip(RoundedCornerShape(16.dp)))
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview(){
    JetCoffeeAppTheme {
        SearchBar()
    }
}
/*
Berikut beberapa hal yang perlu di perhatikan
1. leadingIcon merupakan slot yang dapat diisi dengan Composable Function lain
seperti Icon. Untuk contnt description tidak perlu diisi karena sudah ada placeholder
yang memberikan keterangan fungsi dari komponen ini
2. colors digunakan untuk mengubah warna bagian-bagian pada TextField. Alih-alih menggnunakan
parameter yang berbeda, cukup gunakan sebuah parameter yang berisi banyak pengaturan
3. backgroundColor digunakan untuk mengganti warna background. Parameter lain yang berhubungan dengan
indicator berfungsi untuk menghapus garis warna yang biasanya muncul dibawah text field, baik ketika fokus
maupun tidak
4. placeholder berfungsi untuk memberikan petunjuk terkait teks yang harus diisi. Sama halnya seperti
hint pada view XML
5. stringResource digunakan untuk mengambil teks dari resource string.xml
6. heightIn dengan parameter min digunakan untuk menentukan tinggi minimal dari Text Field. Mengapa
tidak menggunakan tinggi yang tetap saja ? Hal ini merupakan best practice supaya Text Field bisa
membesar ketika ukuran teks juga dibuat besar oleh sistem

 */