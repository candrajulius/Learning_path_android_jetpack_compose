package com.candra.convertersuhu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.candra.convertersuhu.ui.theme.ConverterSuhuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConverterSuhuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        StatefulTemperatureInput()
                        ConverterApp()
                        TwoWayConverterApp()
                    }
                }
            }
        }
    }
}

/*
Ini adalah  Stateful Composable dan bagian bawah merupakan
Stateless Composable.
 */
@Composable
fun StatefulTemperatureInput(
    modifier: Modifier = Modifier,
){
    var input by remember { mutableStateOf("")}
    var output by remember { mutableStateOf("")}
    Column(
        modifier.padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.stateful_converter),
            style = MaterialTheme.typography.h5
        )
        OutlinedTextField(
            value = input,
            label = { Text(text = stringResource(id = R.string.enter_celsius))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { newInput ->
                input = newInput
                output = convertToFahrenheit(newInput)
            }
        )
        Text(text = stringResource(id = R.string.temperature_fahrenhit, output))
    }
}
/*
=======================================================
 */

/*
Kode dibawah merupakan Stateless Composable. Dapat dilihat bahwa kedua
Composable daapt berjalan dengan hasil yang sama. Namun,dari sisi
efektifitas, komponen kedua lebih fleksible dan reusable
 */
@Composable
fun StatelessTemperatureInput(
    input: String,
    output: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
         modifier.padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.stateless_converter),
            style = MaterialTheme.typography.h5
        )
        OutlinedTextField(
            value = input,
            label = { Text(text = stringResource(id = R.string.enter_celsius))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = onValueChange)
        Text(text = stringResource(id = R.string.temperature_fahrenhit,output))
    }
}

private fun convertToFahrenheit(celsius: String) = celsius.toDoubleOrNull()?.let {
    (it * 9 / 5) + 32
}.toString()

/*

 */
@Composable
private fun ConverterApp(
    modifier: Modifier = Modifier,
){
    var input by remember { mutableStateOf("")}
    var output by remember { mutableStateOf("")}

    Column(
        modifier
    ) {
        StatelessTemperatureInput(
            input = input,
            output = output,
            onValueChange = {newInput ->
                input = newInput
                output = convertToFahrenheit(newInput)
            }
        )
    }
}

enum class Scale(val scaleName: String){
    CELSIUS("Celsius"),
    FAHRENHEIT("Fahrenheit")
}

@Composable
fun GeneralTemperatureInput(
    scale: Scale,
    input: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    Column(
        modifier
    ) {
        OutlinedTextField(
            value = input,
            label = { Text(text = stringResource(id = R.string.enter_temperature,scale.scaleName))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = onValueChange)
    }
}
/*
Supaya tidak hardcode, jenis suhu yang diambil dari enum ya dikirimkan melalui
parameter
 */



@Composable
private fun TwoWayConverterApp(
    modifier: Modifier = Modifier,
){
    var celsius by remember { mutableStateOf("")}
    var fahrenheit by remember { mutableStateOf("")}
    Column(
        modifier.padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.two_way_converter),
            style = MaterialTheme.typography.h5
        )
        GeneralTemperatureInput(scale = Scale.CELSIUS, input = celsius,
            onValueChange = { newInput ->
                celsius = newInput
                fahrenheit = convertToFahrenheit(newInput)
            }
        )
        GeneralTemperatureInput(
            scale = Scale.FAHRENHEIT,
            input = fahrenheit ,
            onValueChange = { newInput ->
                fahrenheit = newInput
                celsius = convertToCelsius(newInput)
            }
        )
    }
}

private fun convertToCelsius(fahrenheit: String) =
    fahrenheit.toDoubleOrNull()?.let {
        (it - 32) * 5 / 9
    }.toString()
/*
Ingat! Jika dua state berubah ketika ada event yang sama, mereka harus diletakkan
di level yagn sama
 */


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ConverterSuhuTheme {
        Greeting("Android")
    }
}

/*
Kesimpulan
keyboardOptions digunakan untuk mengonfigurasi tampilan keyboard. Karena kita akan membuat
aplikasi semacam kalkulator, gunakan KeyboardType.Number supaya dapat menampilkan
nomor saja sebagai input seperti berikut

toDoubleOrNull digunakan karena secara default value dari sebuah Text Field bertipe String
Untuk itu, Anda perlu mengubahnya menjadi Double terlebih dahulu dan jika ia tidak
bertipe angka akan mengembalikkan null. Setelah melakukan perhitungan, nilai tersebut diubah
lagi menjadi String menggunakan toString() untuk ditampilkan di TextField


 */