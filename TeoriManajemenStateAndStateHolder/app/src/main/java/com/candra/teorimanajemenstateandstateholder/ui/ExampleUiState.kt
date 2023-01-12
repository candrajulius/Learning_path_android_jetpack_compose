package com.candra.teorimanajemenstateandstateholder.ui

import com.candra.teorimanajemenstateandstateholder.Example

data class ExampleUiState(
    val dataToDisplayOnScreen: List<Example> = emptyList(),
    val errorMessage: String = "",
    val loading: Boolean = false
)

class ExampleViewModel(
    private val repository: MyR
)

/*
UI State berupa data class, enum, ataupun interface yang dapat menunjukkan status halaman
biasanya berisi loading, sukses, dan error
mutableStateOf diperlukan untuk membaca data ketika terjadi perubahan. Karena terletak di luar Composabel
ia tidak perlu dibarengi dengan remember
Apabila data berupa steram, gunakanlah beberapa extension berikut untuk mengubahnya menjadi State
    Flow.collectAsState() => mengubah Flow menjadi State. Tidak diperlukan dependency tambahan
    LiveData.observerAsState() => mengubah LiveData menjadi State. Membutuhkan dependency
    androidx.compose.runtime:runtime-livedata
 Observable.subscribeAsState() => mengubah Observable object dari RxJava2 atau RxJava3 menjadi State.
 Membutuhkan dependency androidx.compose.runtime:runtime-rxjava2 atau androidx.compose.runtime:runtime-rxjava3

 Selain memiliki kelebihan, tentu ada beberapa hal yang perlu diperhatikan ketika menggunakan ViewModel
 Berikut diantaranya:
 ViewModel memiliki lifetime yang lebih panjang daripada Composable. Untuk itu, jangan letakkan state
 yang menahan Composition(seperti ScaffoldState) pada ViewModel karena bisa menyebabkan memory leak
 Cukup gunakan ViewModel pada Composable yang di level Screen. Jangan kirimkan object Viewmodel ke
 composable lainnya. Cukup kirimkan argument yang dibutuhkan saja. Hal ini penting supaya Recomposition efektif
 Pisahkan Stateless dan Stateful Composable yang mengandung ViewModel. Hal ini akan memudahkan Anda
 untuk melakukan test dan preview
 */