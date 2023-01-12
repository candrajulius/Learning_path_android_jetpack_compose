package com.candra.teori_mengatur_state_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.candra.teori_mengatur_state_compose.ui.theme.Teori_Mengatur_State_ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teori_Mengatur_State_ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun FormInput(){
    var name by remember { mutableStateOf("")} // State
    OutlinedTextField(
        value = name // Display State
       ,onValueChange = { newName -> // Event
        name = newName }, //Update State
        label = { Text(text = "Nama")},
    modifier = Modifier.padding(8.dp))
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StateFulCounter(modifier: Modifier = Modifier)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        var count by rememberSaveable { mutableStateOf(0)}
        Text(text = "Button clicked $count times")
        Button(onClick = {count++}) {
            Text("Click me!")
        }
    }
}

@Preview
@Composable
fun CounterPreview(){
    
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Teori_Mengatur_State_ComposeTheme {
        Greeting("Android")
    }
}
/*
Kesimpulan
State adalah data yang berubah-ubab sepanjang waktu untuk mengontrol UI

Unidirectional Data Flow
Gambaran umum proses dari Unidirectional Data Flow
Event -> Memperbarui State -> Menampilkan State
Event => segala sesuatu yang dapat menyebabkan State berubah.
Unidirectional Data Flow adalah pattern yang mengalirkan Events ke atas dan
State ke bawah,
Dengan mengikuti pattern UDF tersebut. Anda akan mendapatkan beberapa manfaat berikut:
 Testability => memisahkan state dengan UI sehingga pengetasn kedua komponen menjadi lebih mudah
 State encapsulation => proses pengubhan state hanya bisa dilakukan di sutu tempat sehingga
 mengurangi potensi muncul bug
 UI consistency => state yang dibuat langsung direfleksikan dalam bentuk UI sehingga
 membuat UI selalu konsisten dengan state yang didefinisikan

 mutableStateOf, remember, & rememberSaveable
 */