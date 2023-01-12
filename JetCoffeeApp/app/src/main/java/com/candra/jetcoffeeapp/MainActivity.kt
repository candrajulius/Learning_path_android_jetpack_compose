package com.candra.jetcoffeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.candra.jetcoffeeapp.model.Menu
import com.candra.jetcoffeeapp.model.dummyBestSellerMenu
import com.candra.jetcoffeeapp.model.dummyCategory
import com.candra.jetcoffeeapp.model.dummyMenu
import com.candra.jetcoffeeapp.ui.components.*
import com.candra.jetcoffeeapp.ui.theme.JetCoffeeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetCoffeeAppTheme {
                JetCoffeeApp()
            }
        }
    }
}

@Composable
fun JetCoffeeApp(){
    Scaffold(
        bottomBar = { BottomBar()}
    ) { innerPading ->
        Column (modifier = Modifier.verticalScroll(rememberScrollState()).padding(innerPading)){
            Banner()
            HomeSection(title = stringResource(id = R.string.section_category), content = { CategoryRow()})
            HomeSection(title = stringResource(id = R.string.section_best_seller_menu),
                Modifier,content = { MenuRow(listMenu = dummyMenu)})
            HomeSection(title = stringResource(id = R.string.section_best_seller_menu)) {
                MenuRow(listMenu = dummyBestSellerMenu) }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL, showSystemUi = true)
@Composable
fun JetCoffeeAppPreview(){
    JetCoffeeAppTheme {
        JetCoffeeApp()
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun CategoryRow(
    modifier: Modifier = Modifier
){
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ){
        items(dummyCategory, key = {it.textCategory}) {category ->
            CategoryItem(category = category)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CategoryRowPreview(){
    JetCoffeeAppTheme {
        CategoryRow()
    }
}

@Composable
fun MenuRow(
    listMenu: List<Menu>,
    modifier: Modifier = Modifier
){
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ){
        items(listMenu, key = {it.title}){menu ->
            MenuItem(menu)
        }
    }
}

@Composable
fun Banner(
   modifier: Modifier = Modifier,
){
    Box(modifier = modifier) {
        Image(painter = painterResource(id = R.drawable.banner),
            contentDescription = "Banner Image", contentScale = ContentScale.Crop,
        modifier = Modifier.heightIn(160.dp))
        SearchBar()
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier
){
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary,
        modifier = modifier
    ) {
        val navigationItems = listOf(
            BottomBarItem(
                title = stringResource(id = R.string.menu_home),
                icon = Icons.Default.Home
            ),
            BottomBarItem(stringResource(id = R.string.menu_favorite),
            icon = Icons.Default.Favorite),
            BottomBarItem(title = stringResource(id = R.string.menu_profile),
            icon = Icons.Default.AccountCircle)
        )
        navigationItems.map {
            BottomNavigationItem(
                icon = {
                    Icon(imageVector = it.icon, contentDescription = it.title)
                },
                label = {
                    Text(it.title)
                },
                selected = it.title == navigationItems[0].title,
                unselectedContentColor = Color.LightGray,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetCoffeeAppTheme {
        Greeting("Android")
    }
}

/*
Kesimpulan
Satu parameter baru yang belum pernah kita bahas adalah contentScale. Ia berfungsi untuk
mengatur proses tampilnya gambar pada sebuah komponen. Sebagai contoh,crop digunakan untuk membuat
gambar memenuhi ruang dan memotong bagian lebihnya. Berikut perbandingan dengan jenis yang lain
 3 paramter untuk contetnScale
 1. Fit
 2. Fill Bounds
 3. None
Arrangement.spacedBy() berfungsi untuk mengatur jarak antara item di dalam list
Pada contoh kita memberikan jarak 8 dp antar itemnya. ini merupakan properti baru yang tidak
dijumpaoi pada View XML.
listMenu dibuat sebagai parameter karena kita ingin menampilkan menu yang berbeda

Selanjutnya, gunakan SectionText untuk menampilkan bagian judul dan panggil MenuRow dengan data yang
berbeda pada JetCoffeeApp

Ada beberapa cara untuk memanggil fingsi lambda seperti dibawah ini
 1. Pada bagian kategori, merupakan contoh penggunaan named parameter
 2. Pada bagian menu favorit, memasukkan argument satu per satu
 3. Pada bagian menu terlaris, jika lambda ada di akhir parameter, ia dapat dikeluarkan
  setelah parenthesis

  Daripada membuat BottomNavigationItem satu per satu
  gunakanlah map data dari data class seperti langkah diatas. Berikut beberapa hal yang perlu
  Anda perhatikan pada kode tersebut
  1. icon dan label merupakan slot based layout untuk memasukkan ikon dan teks dibawahnya
  2. selected berfungsi untuk menentukan menu mana yang dipilih. Untuk sementara, kita memilih menu pertama
  secara hardcode
  3. onClick untuk memberi aksi ketiak item menu diklik
   Berikut beberapa hal yang perlu Anda ketahui dari penambahan kode diatas
   1. backgroundColor => mengganti warna background
   2. contentColor => mengganti wanra kontent alias ikon dan tulisan
   3. unselectedContentColor => menentukan warna pada kontent yang tidak dipilih
 */