package com.candra.emptycomposeactivity

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.candra.emptycomposeactivity.ui.theme.EmptyComposeActivityTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CalcaulatorAppKtTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp(){
        composeTestRule.setContent {
            EmptyComposeActivityTheme {
                CalculatorApp()
            }
        }
    }

    @Test
    fun calculate_area_of_rectangle_correct(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.enter_length))
            .performTextInput("3")
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.enter_width))
            .performTextInput("4")
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.count)).performClick()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.count), useUnmergedTree = true)
            .assertHasClickAction()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.result,12.0))
            .assertExists()
    }

    @Test
    fun wrong_input_not_calucalted(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.enter_length))
            .performTextInput("..3")
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.enter_width))
            .performTextInput("4")
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.count)).performClick()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.result,0.0))
            .assertExists()
    }
}

/*
onNodeWithText => mencari elemen dengan teks tertentu
performTextInput => melakukan aksi untuk memasukkan input pada  TextField
performClick => melakukan aksi untuk menekan elemen yang dipilih
assertExists => meamsatikan suatu elemen eksis atau ada

Untuk mendapatkan context manfaatkan composeTestRule.activity untuk mendapatkan context


Annotation @Before digunakan supaya fungsi ini dipanggil terlebih dahulu sebelum setiap
fungsi lainnya dipanggil secara ototmatis. Hal ini tentunya membuat kode menjadi lebih efisien dariapda
harus memanggil secara manual
 */