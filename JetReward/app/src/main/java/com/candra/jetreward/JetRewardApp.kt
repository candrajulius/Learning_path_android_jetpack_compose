package com.candra.jetreward

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.candra.jetreward.ui.navigation.NavigationItem
import com.candra.jetreward.ui.navigation.Screen
import com.candra.jetreward.ui.screen.cart.CartScreen
import com.candra.jetreward.ui.screen.detail.DetailScreen
import com.candra.jetreward.ui.screen.home.HomeScreen
import com.candra.jetreward.ui.screen.profile.ProfileScreen
import com.candra.jetreward.ui.theme.JetRewardTheme
import com.candra.jetreward.util.Constant
import com.candra.jetreward.util.FunctionObject.actionToDestinationWithPopUpTo
import com.candra.jetreward.util.FunctionObject.shareOrder

@Composable
fun JetRewardApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
){
    /*
    Digunakan untuk tidak menampilkan bottom bar ketika di halaman detail
    dengan memanfaatkan currentBackStackEntryAsState
     */
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailReward.route){
                BottomBar(navController)
            }
        },
        modifier = modifier
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Home.route){
                HomeScreen(
                    navigateToDetail = {rewardId ->
                        navController.navigate(Screen.DetailReward.createRoute(rewardId))
                    }
                )
            }
            composable(Screen.Cart.route){
                val context = LocalContext.current
                CartScreen(
                    orderButtonClicked = {message ->
                        shareOrder(context,message)
                    }
                )
            }
            composable(Screen.Profile.route){
                ProfileScreen()
            }
            // Digunakan untuk pergi ke detail reward beserta mengirimkan argument
            // dan panggil route dari HomeScreen
            composable(
                route = Screen.DetailReward.route,
                arguments = listOf(navArgument(Constant.OBJECT_ARGUMENT_ID) {type = NavType.LongType}),
            ){
                val id = it.arguments?.getLong(Constant.OBJECT_ARGUMENT_ID) ?: -1L
                DetailScreen(rewardId = id, navigateBack = {
                    navController.navigateUp()
                },
                   navigateToCart = {
                       navController.popBackStack()
                       actionToDestinationWithPopUpTo(navController = navController,Screen.Cart.route)
                   }
                )
            }
        }
    }
}





/*
Karena mengarah ke halaman Cart yang merupakan halaman utama pada Bottom Navigation, kita menggunakan
kode yang sama dengan navigasi pada item Bottom Navigation. Bedanya kita menambahkan popBackStack untuk
menghapus stack pada halaman home

Tanpa popBackStack, ketika kita menekan menu Home, ia akan menampilkan stack aplikasi
terakhir, yakni halaman detail. Dengan popBackStack, stack tersebut dibersihkan sehingga
halaman yang tampil adalah halaman awal (home)

Intent yang diubah berjenis ACTION_SEND, artinya hanya aplikasi yang bisa menerima jenis ACTION_SEND
sajalah yang bisa menangani tugas ini, seperti aplikasi SMS dan email. Kita juga mendefinisikan jenis
MIME data yang dibagikan, yakni text/plain. Kemudian untuk memasukkan data, kita menggunakan putExtra.
EXTRA_SUBJECT digunakan untuk judul dan EXTRA_TEXT digunakan untuk isi pesan

 createChooser merupakan jenis eksekusi intent yang menampilkan beberapa pilihan aplikasi yang bisa
 membuka data yang dibagikan
 */

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(id = R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_cart),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            )
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(text = item.title)},
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        actionToDestinationWithPopUpTo(navController,item.screen.route)
                    }
                )
            }
        }
    }
}



/*
Kesimpulan
navController.navigate => digunakan untuk eksekusi navigatis ke route sesuai dengan item yang dipilih
popUpTo => digunakan untuk kembali ke halaman awal supaya tidak membuka halaman baru terus menerus
saveState dan restoreState => Mengembalikkan state ketika item dipilih lagi
launchSingleTop => digunakan supaya tidak ada halaman yang dobel ketika memilih ulang item yang sama

 Tanpa popUpTo => Ketika ditekan tombol Back pada halaman Home, aplikasi kembali ke Halaman Profile
 Keranjang dan Home lagi. Setelah itu baru bisa keluar
 Tanpa launchSingleTop => Ketika ditekan tombol Back pada halaman Home, aplikasi tidak langsung
 keluar karena kembali ke halaman Home yang sebelumnya (dobel)

 untuk mengetahui halaman yang sedang dipilih untuk menentukan status select => gunakanlah currentBackStackEntryAsState
 Pada kode diatas, kita membandingkan route dari back stack dengan route pada navigation item.
 Jika sama (alias dipilih), parameter selected akan bernilai true.

Di rekap yang sudah dilakaukan pada latihan ini:

 1. Membuat NavHost, NavGraph, dan NavController
 2. Mengintegrasikan Navigation Component dengan Bottom Navigation
 3. Mengirimkan data argument antara halaman
 4. Menerapkan prinsip prinsip navigasi yang tepat
 5. Memanfaatkan Intent untuk navigasi ke aplikasi lain
 */

/*
Navigation Component merupakan salah satu komponen di dalam library Jetpack yang
berfungsi untuk bernavigasi antara Composable

Berikut 5 prinsip Navigation yang dapat membuat aplikasi Anda konsisten dan sesuai harapan
pengguna
    a. Setiap aplikasi harus memiliki start destination yang tetap. Start destination
    merupakan halaman awal ketika aplikasi dibuka. Sehingga aplikasi selalu menampilkan
    halaman yang sama saat dijalankan pertama kali
    b. Stack harus menyimpan state saat kembali lagi dari halaman selanjutnya
    c. Tombol Up(yang ada di Top App Bar) dan tombol Back (yang ada di bawah ) harus sesuai saat
    navigasi
    Tombol Up tidak seharusnya menyebabkan keluar dari aplikasi
    d. Deep Link seharusnya memiliki alur backstck yang sama dengan navigasi manual
 */

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview(){
    JetRewardTheme {
        JetRewardApp()
    }
}