package com.candra.teoriscaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.candra.teoriscaffold.ui.theme.TeoriScaffoldTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeoriScaffoldTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyTopBar()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyTopBar() {
    var showMenu by remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                       scope.launch {
                          val result = scaffoldState.snackbarHostState.showSnackbar(
                              message = "Snackbar", actionLabel = "Action",
                              // Defaults to SnackbarDuration.Short
                              duration = SnackbarDuration.Indefinite
                          )
                           when(result){
                               SnackbarResult.ActionPerformed -> {
                                   // Handle snackbar action performed
                               }
                               SnackbarResult.Dismissed -> {
                                   // Handle snackbar dismissed
                               }
                           }
                       }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu" )
                    }
                },
                title = {
                    Text(text = "My Scaffold")
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite")
                    }
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Show More")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = {showMenu = false}) {
                        DropdownMenuItem(onClick = {}) {
                            Text(text = "Call me")
                        }
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Masukkan pesan anda", actionLabel = "Aksi anda"
                    )
                }
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah")
            }
        }
    ) {

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JetCoffeeApp(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "My Scaffold")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {})
            {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add" )
            }
        },
    ){ innerPadding -> 
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Hello World")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExampleApp2(){
    Scaffold(
        topBar =  {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.primary
                    )
            ) {
                Image(painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Avatar", modifier = Modifier.height(40.dp))
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add floating action button" )
            }
        }
    ) {

    }
}



@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TeoriScaffoldTheme {
        Greeting("Android")
    }
}

/*
Kesimpulan
Slot based-layout
Sacffold yang kita bahas sebelumnya merupakan salah satu contoh dari slot-based layout
adalah layout yang memungkinkan Anda untuk mengisi solot yang disedialan dengan Composable Function
lainnya. Pendekatan ini membuat komponen menjadi sangat fleksibel tanpa harus mengetahui detil
penempatannya.
 1. navigationIcon digunakan untuk mengisi ruang yang di kiri, title untuk ruang tengah, dan
 actions untuk ruang yang di kanan.

 Custom Slot-based Layout
 Lalu, bagaimana jika Anda ingin membuat komponen sendiri degnan mekanisme slot-based layout?
 Cara paling mudah adalah dengan menyontek kode yang ada dibalik slot-based layout bawaan.
 Salah satunya adalah TopAppBar berikut
 */