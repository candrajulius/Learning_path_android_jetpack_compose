package com.candra.jetreward.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.candra.jetreward.model.OrderReward
import com.candra.jetreward.model.Reward
import com.candra.jetreward.R
import com.candra.jetreward.onNodeWithStringId
import com.candra.jetreward.ui.theme.JetRewardTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenKtTest{
    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeOrderReward = OrderReward(
        reward = Reward(4,R.drawable.reward_4,"Jaket Hoodie Dicoding",7500),
        count = 0
    )

    @Before
    fun setUp(){
        composeTestRule.setContent {
            JetRewardTheme {
                DetailContent(
                    image = fakeOrderReward.reward.image,
                    title = fakeOrderReward.reward.title,
                    basePoint = fakeOrderReward.reward.requirePoint,
                    count = fakeOrderReward.count,
                    onBackClick = {},
                    onAddToCart = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    // Skenario Pertama
    /*
    Memastikan data pada halaman detail tampil
     */
    @Test
    fun detailContent_isDisplayed(){
        composeTestRule.onNodeWithText(fakeOrderReward.reward.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.required_point,
                fakeOrderReward.reward.requirePoint
            )
        ).assertIsDisplayed()
    }

    // Skenario kedua
    /*
   Memastikan ketika jumlah produk ditambah, status tombol menjadi aktif
     */
    @Test
    fun increaseProduct_buttonEnabled()
    {
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    // Skenario ketiga
    /*
    Memastikan ketika tombol + ditekan 2 kali, jumlah produk menjadi 2
     */
    @Test
    fun increaseProduct_correctCounter(){
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick().performClick()
        composeTestRule.onNodeWithText("count").assert(hasText("2"))
    }
    // Pastikan nilai testTag sama dengan yang ditambahkan sebelumnya

    // Skenario keempat
    /*
    Memastikan ketika tombol dikurangi dari 0 jumlah produk tetap 0
     */
    @Test
    fun decreaseProduct_stillZero()
    {
        composeTestRule.onNodeWithStringId(R.string.minus_symbol).performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))
    }
}

/*
Catatan: Anda juga dapat menambahkan printToLog untuk melihat
semantic tree dari DetailContent

Jika pada baris pertama, Anda memastikan tomnol dalam kondisi tidak aktif
ketika pertama kali dijalankan. Setelah tombol + ditekan dan jumlah produk
menjadi lebih dari satu, barulah diperiksa apakah tombol aktif

Untuk menekan dua kali panggil perfirmClick dua kali juga
 */