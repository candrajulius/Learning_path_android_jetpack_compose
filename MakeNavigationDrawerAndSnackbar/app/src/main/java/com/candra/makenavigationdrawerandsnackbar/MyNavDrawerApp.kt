package com.candra.makenavigationdrawerandsnackbar

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.candra.makenavigationdrawerandsnackbar.ui.theme.MakeNavigationDrawerAndSnackbarTheme
import kotlinx.coroutines.launch

@Composable
fun MyNavDrawerApp()
{
//    val scaffoldState = rememberScaffoldState()
//    val scope = rememberCoroutineScope()
//    val context = LocalContext.current

    val appState = rememberMyDrawerState()

    Scaffold(
        scaffoldState = appState.scaffoldState, // untuk mengatur elemen di dalamnya dengan animasi default
        topBar = {
            MyTopBar(
                onMenuClick = appState::onMenuClick
            )
        },
        drawerContent = {
           MyDrawerContent(
               onItemSelected = appState::onItemSelected,
               onBackPress = appState::onBackPress
           )
        },
        drawerGesturesEnabled = appState.scaffoldState.drawerState.isOpen,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(id = R.string.hello_world))
        }
    }
}

/*
Function reference juga dapat membaca satu paramter dan langsung memasukkannya pada fungsi
yang dipanggi tanpa menuliskannya. Contohnya ada pada appState::onItemSelected yang tidak perlu
menuliskan parameter title:String
 */

data class MenuItem(val title: String, val icon: ImageVector)

@Composable
fun MyDrawerContent(
    modifier: Modifier = Modifier,
    onItemSelected: (title: String) -> Unit,
    onBackPress: () -> Unit
) {
    val items = listOf(
        MenuItem(
            title = stringResource(id = R.string.home),
            icon = Icons.Default.Home
        ),
        MenuItem(
            title = stringResource(id = R.string.favourite),
            icon = Icons.Default.Favorite
        ),
        MenuItem(
            title = stringResource(id = R.string.profile),
            icon = Icons.Default.AccountCircle
        ),
    )
    Column(
        modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .height(190.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
        )
        for (item in items) {
            Row(
                modifier = Modifier
                    .clickable { onItemSelected(item.title) }
                    .padding(vertical = 12.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = Color.DarkGray
                )
                Spacer(modifier = Modifier.width(32.dp))
                Text(text = item.title, style = MaterialTheme.typography.subtitle2)
            }
        }
        Divider()

        /*
    Catatan
    Anda juga dapat mengganti Box dengan Image atau konten lain
    sesuai selera. Disini, kita menggunakan Box dengan background berwarna
    supaya sederhana
     */
    }
    BackPressHandler {
        // do something
        onBackPress()
    }
}

@Composable
fun BackPressHandler(enabled: Boolean = true, onBackPressed: () -> Unit)
{
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)
    val backCallback = remember {
        object: OnBackPressedCallback(enabled){
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }
    SideEffect {
        backCallback.isEnabled = enabled
    }

    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current){
        "No OnBackPressDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, backCallback){
        backDispatcher.addCallback(lifecycleOwner,backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}

/*
Kesimpulan
Sebenarnya sudah ada wrapper bawaan untuk menangani aksi back,
yakni BackHandler{}. Inti dari kode tersebut adalah untuk mengganti bawaan dengan aksi
lain. Namun, disini kita membuatnya secara manual untuk memahami berbagai macam Effect API
yang dipakai di dalamanya. Mari kita telaah satu per satu.
rememberUpdatedState: menyimpan status onBack secara aman walaupun ada perubahan pada
parameter lainnya.
SideEffect: memperbarui callback setiap kali composition berhasil dengan nilai eanbled
DisposableEffect: menghapus backCallback ketika meninggalkan composition
 */

@Composable
fun MyTopBar(onMenuClick: () -> Unit){
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                onMenuClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.menu)
                )
            }
        },
        title = {
            Text(stringResource(id = R.string.app_name))
        },
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview()
{
    MakeNavigationDrawerAndSnackbarTheme {
        MyNavDrawerApp()
    }
}
/*
Kesimpulan
scaffoldState: merupakan state bawaan dari Scaffold untuk mengatur elemen didalamnya
dengan animasi default. Di dalaamnya terdapat dua state, yakni drawerState untuk
mengatur Navigation Drawer dan snackbarHostState untuk mengatur Snackbar

rememberCoroutineScope diguanakan untuk memanggil Coroutine di dalam Composable,
Karena fungsi open merupakan suspend function, Anda perlu menggunakan coroutine scope untuk
memanggilnya


Menggunakan scaffoldState untuk mendapatkan drawerState dan sncakbarHostState
Menggunakan drawerState untuk membuka, menututp dan mengetahui state Navigation Drawer
Menggunakan rememberCoroutineScope untuk menjalankan suspend function open() dan close()
dari drawerState
Menggunakan Effect API seperti rememberUpdateState,SidefEffect, dan DisposableEffect untuk
meng-overrode fungsi onBackPressed
 */

