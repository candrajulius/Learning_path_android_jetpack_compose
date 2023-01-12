package com.candra.calculatorapptesting

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.candra.calculatorapptesting.ui.theme.CalculatorAppTestingTheme

@Composable
fun CalculatorApp(
    modifier: Modifier = Modifier
)
{
    var lengthInput by remember { mutableStateOf("")}
    var widthInput by remember { mutableStateOf("")}
    val context = LocalContext.current
    var result by remember {mutableStateOf("")}

    val length = lengthInput.toDoubleOrNull() ?: 0.0
    val width = widthInput.toDoubleOrNull() ?: 0.0

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column{
            TextFieldCalculator(value = lengthInput, 
                onValueChange = {lengthInput = it}, 
                label = stringResource(id = R.string.enter_length))
            Spacer(modifier = modifier.height(8.dp))
            TextFieldCalculator(value = widthInput, 
                onValueChange = {widthInput = it},
                label = stringResource(id = R.string.enter_width))
            Button(onClick = { 
                if (lengthInput.isEmpty()){
                    Toast.makeText(context,"Length not be empty",Toast.LENGTH_SHORT).show()
                    result = "0"
                }else if (widthInput.isEmpty()){
                    Toast.makeText(context,"Width not be empty",Toast.LENGTH_SHORT).show()
                    result = "0"
                }else{
                    result = (length * width).toString()
                }
            }) {
                Text(text = stringResource(id = R.string.count))
            }
            Text(text = stringResource(id = R.string.result,result))
        }
    }
}



@Composable
private fun TextFieldCalculator(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String
){
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview()
{
    CalculatorAppTestingTheme {
        CalculatorApp()
    }
}