package com.candra.teori_state_hoisting_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.candra.teori_state_hoisting_compose.ui.theme.Teori_State_Hoisting_ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teori_State_Hoisting_ComposeTheme {
                MyScreen()
            }
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier){
    var count by rememberSaveable { mutableStateOf(0)}
    StatelessCounter(
        count = count,
        onClick = {count++},
        modifier = modifier
    )
}

@Composable
fun StatelessCounter(
    count: Int, // State
    onClick: () -> Unit, // Event
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        Text("Button clicked $count times: ")
        Button(onClick = {onClick()}) {
            Text("Click me")
        }
    }
}

@Composable
fun MyScreen(modifier: Modifier = Modifier)
{
    // State terletak di MyScreee dan MySwitch
    var checked by remember { mutableStateOf(false)}
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =  Modifier.padding(16.dp)
    ) {
        MySwitch(checked = checked,onCheckChanged = {checked  = it})
        Text(text = if (checked) "ON" else "OFF", modifier = Modifier.clickable {
            checked = !checked
        })
    }
}

// checked bersifat Immutable (tidak dapat diubah)
@Composable
fun MySwitch(
    checked: Boolean,
    onCheckChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    Switch(checked = checked, onCheckedChange = {
        onCheckChanged(it)
    })
}

@Preview(showBackground = true)
@Composable
fun CounterPreview()
{
    Teori_State_Hoisting_ComposeTheme {
        StatefulCounter()
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Teori_State_Hoisting_ComposeTheme {
        Greeting("Android")
    }
}