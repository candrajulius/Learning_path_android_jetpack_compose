package com.candra.teorimanajemenstateandstateholder.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class FormInputState(initialInput: String){
    var input by mutableStateOf(initialInput)
}

@Composable
fun rememberFormInputState(input: String): FormInputState =
    remember(input){
        FormInputState(input)
    }

@Composable
fun MyForm(){
    val input = rememberFormInputState(input = "")
    FormInput(
        state = input
    )
}

@Composable
fun FormInput(
    state: FormInputState = rememberFormInputState(input = ""),
){
    OutlinedTextField(
        value = state.input,
        onValueChange = {state.input = it},
        label = { Text(text = "Nama") },
        modifier = Modifier.padding(8.dp)
    )
}
/*
Kesimpulan
FormInputState merupakan kelas biasa yang berisi state dengan tanda mutableStateOf
rememberFormInputState memanfaatkan remember supaya state tersimpan dan tidak hilang ketika
Recomposition
state diletakkan di parameter untuk menerapkan State Hoisting supaya reusable. Ia menggantikan parameter
input dan onValueChange yang sebelumnya ada
Untuk mengakses dan mengganti state, cukup panggil properti var di dalamnya, seperti state.input

 Dalam kasus tertentu ViewModel memiliki kelebihan daripada State Holder untuk melakukan
 manajemen state. Jika Anda tidak mendapatkan manfaat di bawah ini, gunakan saja Sate Holder

 1. Mempertahankan data ketika configuration change(seperti ketika device rotasi)
 2. Terintegrasi dengan library Jetpack lainnya, seperti Hilt dan Navigation Component
 3. Disimpan di cache ketika berada di Navigation backstack dan di bersihkan ketika keluar
 */