package com.candra.teori_dasar_layout_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.candra.teori_dasar_layout_compose.ui.theme.Teori_Dasar_Layout_ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teori_Dasar_Layout_ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ContactCardName(name = "Candra Julius Sinaga")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactCard(){
    Row(
       modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box{
           Image(painterResource(R.drawable.ic_launcher_background), contentDescription = "Avatar",
           modifier = Modifier
               .size(60.dp)
               .clip(CircleShape))
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Online Status",
            tint = Color.Green, modifier = Modifier.align(Alignment.BottomEnd))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = "Candra Julius Sinaga", fontWeight = FontWeight.Bold)
            Text(text = "Online")
        }
    }
}




@Composable
fun ButtonWithText(text: String, modifier: Modifier = Modifier){
    Button(onClick = {},
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_500)),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text, textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Weight(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Row {
            ButtonWithText(text = "1",Modifier.weight(weight = 1f))
            ButtonWithText(text = "0")
            ButtonWithText(text = "0")
        }
    }
}

@Composable
fun ContactCardName(name: String, modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = {})
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "Avatar 1",
            modifier = Modifier
                .padding(4.dp)
                .border(2.dp, Color.Green, CircleShape)
                .size(60.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontWeight = FontWeight.Bold)
            Text(text = "Online")
            Icon(imageVector = Icons.Filled.Check, contentDescription = null,
                modifier = Modifier.offset(x=8.dp,y=30.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Teori_Dasar_Layout_ComposeTheme {
        Greeting("Android")
    }
}

/*
Kesimpulan
Arrangement digunakan untuk mengatur susunana beberapa komponen dalam satu layout
Arrangement ini bisa digunakan pada layout Column maupun Row. Berikut beberapa pilihan Arrangement
yang bisa anda gunakan
 1. Space Between => elemen awal dan akhir menempel di sisi ruang
    sisi ruangnya dibagi rata
 2. Space Around => sisa ruang akan dibagi menjadi jarak dibagian kiri dan kanan setiap elemen
 3. Space Evenly => sisa ruang akan dibagi rata ke semua elemen
 4. End(LTR) => semua elemen disusun di bagian akhir
 5. Center => semua elemen disusun di bagian tengah
 6. Start(LTR) => semua elemen disusun di bagian awal

 Alignment digunakan untuk mengatur posisi suatu komponen dalam suatu layout.
 Supaya lebih jelas, mari kita lihat penggunaannya pada masing-masing layout
  1. Alignment pada Column terdapat 3 macam, yaitu Start, CenterHorizontally, dan End
  2. Alignment pada Row terdapat 3 macam , yaitu Top, CenterVertically, dan Bottom
  3. Aligment pada box ada 9 antara lain sebagai berikut :
     TopStart, TopCenter, CenterStart, Center, BottomStart, BottomCenter
     
 Weight, Selain arrangement, komponen lain yang sering digunakan adalah weight.
 Weight dapat menentukan proporsi elemen pada suatu layout. Semakin besar nilai yang anda 
 masukkan pada modifier weight, semakin besar pula proporsinya pada suatu layout. Apabila
 semua weight bernilai sama, ketiga elemen akan memiliki proporsi yang sama.
 
 Selain weight, ada juga parameter fill yang berfungsi untuk menentukan apakah ia
 memenuhi layar atau tidak
 Lalu bagaimana jika hanya ada 1 elemen yang diberi modifier weight dan elemen lainnya tidak?
 Efeknya elemen tersebut akan memenuhi layar dan elemen lainnya akan menyesuaikan dengan ukuran aslinya

 Modifier
 Secara default, ukuran layout pada Compose sudah otomatis mengikuti konten.
 yang ada di dalamanya. Namun, Anda juga dapat menentukan sendiri ukuran suatu komponen
 dengan modifier, Berikut beberapa modifier yang bisa digunakan:
    1. fillMaxSize => membuat ukuran komponen memenuhi layout induknya
    2. wrapContentSize => menentukan ukuran berdasarkan ukuran kontent di dalamnya
    3. size => menentukan ukuran dengan angka yang pasti
    4. requiredSize => menentukan ukuran dengan angka pasti, tetapi tidak tergantung
    dengn layout parentnya.
    5. sizeIn => menentukan ukuran dengan rentang tertentu

    Selain size untuk menentukan ukuran tinggi dan lebar sekaligus, Anda juga pdapat menggunakan
    width dan height untuk mengatur tinggi dan lebar masing-masing. Berikut adalah contoh kasus ketika
    menggunakan requiredWidth dan width. Tanpa menentukan ukuran yang cukup lebar,
    Arrangement dan Alignment yang sudah diatur biasanya tidak akan terlihat

 Action
 Apabila menggunakan Button,biasanya sudah terdapat aksi onClick bawaan dari komponen tersebut. Lalu,
 bagaimana jika ingin menambahkan aksi onClick pada komponen lainnya ? Untungnya, modifier memungkinkan
 Anda untuk melakukan hal tersebut. Berikut beberapa aski yang bisa ditambahkan:
    1. Clickable => menambahkan aksi klik
    2. Draggable => menambahkan aksi drag
    3. Selectable => membuat komponen bisa dipilih
    4. Swipeable => menambahkan aksi swipe

 Padding & Offset
 Padding digunakan untuk memberikan jarak antara komponen. Berikut beberapa parameter
 yang bisa anda pilih untuk mengatur jaraknya
   1. all => untuk memberikan jarak di semua sisi
   2. start,top,bottom,end => memberikan jarak pada sisi tertentu
   3. horizontal,vertical => memberikan jarak pada sisi horizontal atau vertikal saja

Drawing
Modifier yang termasuk dalam kategori drawing biasanya berfungsi untuk
memodifikasi atau menambahkan aksesoris tambahan pada komponen, Berikut beberapa contohnya
    1. border => menambahkan bingkai
    2. clip => memotong komponen dengan bentuk tertentu
    3. alpah => membuat transparan
    4. background => menambahkan warna background
    5. shadow => menambahkan bayangan

 Urutan Modifier

 */