package com.candra.hello_jetpack_compose


import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.candra.hello_jetpack_compose.ui.theme.Hello_jetpack_composeTheme


private val sampleName = listOf(
    "Andre",
    "Desta",
    "Parto",
    "Wendy",
    "Komeng",
    "Raffi Ahmad",
    "Andhika Pratama",
    "Vincent Ryan Rompies"
)

private const val welcome_dicoding = "Welcome Candra Julius Sinaga"


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Hello_jetpack_composeTheme {
               HelloJetpackComposeApp()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    // Properties Delegation
    // Untuk memberitahu Compose jika ada state yang berubah,
    // gunakanlah mutableStateOf. Selain itu, dibutuhkan juga rememer untuk
    // menjaga supaya nilai tersebut tidak terseret.
    // ketika update UI. Efeknya, Anda perlu menggunakan .value untuk mengambil nilai
    // didalamnya
    var isExpanded by remember { mutableStateOf(false)}

    val animatedSizeOp by animateDpAsState(
        targetValue = if (isExpanded) 120.dp else 80.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
    ){
        Row (modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(painter = painterResource(id = R.drawable.jetpack_compose),
                contentDescription = "Logo jetpack compose", modifier = Modifier.size(animatedSizeOp))
            Spacer(modifier = Modifier.size(8.dp))
            Column(modifier = Modifier.weight(1f)){
                Text(text = "Hello $name", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = welcome_dicoding, style = MaterialTheme.typography.body1.copy(
                    fontStyle = FontStyle.Italic
                ))
            }
            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Outlined.ExpandMore,
                    contentDescription = if (isExpanded) "Show less" else "Show More")
            }
        }
    }
}

@Composable
fun GreetingList(names: List<String>){
    if (names.isNotEmpty()){
        LazyColumn{
            items(names){ name ->
                Greeting(name = name)
            }
        }
    }else{
        Box(contentAlignment = Alignment.Center) {
            Text("No people to great :(")
        }
    }
}

@Preview(
    showBackground = true,
    name = "Without Surface",
    group = "Without Surface",
    device = Devices.PIXEL_3A,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO
)

@Preview(
    name = "Group One",
    group = "Group One",
    device = Devices.PIXEL_3A_XL,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)

@Composable
fun HelloJetpackComposeApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ){
        GreetingList(names = sampleName)
    }
}

// Kesimpulan
/*
Di sini dp dijadikan state dan dibaca oleh Compose jika ada perubahan menggunakan animateDpAsState.
Selain itu, juga ada animationSpec untuk mengatur jenis animasi yang tampil
 */
