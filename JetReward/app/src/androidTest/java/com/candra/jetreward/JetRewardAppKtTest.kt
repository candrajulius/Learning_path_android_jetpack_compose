package com.candra.jetreward

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.candra.jetreward.model.FakeRewardDataSource
import com.candra.jetreward.ui.navigation.Screen
import com.candra.jetreward.ui.theme.JetRewardTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JetRewardAppKtTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp()
    {
        composeTestRule.setContent {
            JetRewardTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetRewardApp(navController = navController)
            }
        }
    }

    // Skenario pertama
    /*
    Memastikan start destinantion menampilkan halaman Home. Gunakan currentBackStackEntry
    untuk melihat halaman yang sedang tampil saat ini alias
     */
    @Test
    fun navHost_verifyStartDestination()
    {
        navController.assertCurrentRouteName(Screen.Home.route)
        /*
        assertEquals digunakan untuk memastikan apakah kedua nilai yang dimasukkan sama
        Parameter pertama adalah nilai yang diharapkan (expected) dan parameter kedua adalah nilai
        yang sebenarnya (actual)
         */
    }

    // Skenario Kedua
    /*
       Mamastikan ketika item pada list ditekan, tampil halaman detail dengan data yang benar
       Karena list tidak memiliki Semantic Property.
     */
    @Test
    fun navHost_clickItem_navigesToDetailWithData()
    {
        composeTestRule.onNodeWithTag("RewardList").performScrollToIndex(10)
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[10].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailReward.route)
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[10].title).assertIsDisplayed()
    }

    /*
    Sekanrio ketiga
     Memastikan menu Bottom Navigation menampilkan halaman yang tepat
     */
    @Test
    fun navHost_bottomNavigation_working(){
        composeTestRule.onNodeWithStringId(R.string.menu_cart).performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    /*
    Skenario keempat
    Memastikan ketika ikon back ditekan pada halaman detail, aplikasi kembali ke halaman
    home
     */
    @Test
    fun navHost_clickItem_navigatesBack()
    {
        composeTestRule.onNodeWithTag("RewardList").performScrollToIndex(10)
        composeTestRule.onNodeWithTag(FakeRewardDataSource.dummyRewards[10].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailReward.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back))
            .performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }
    /*
    Skenario kelima
    Memastikan ketika menambahkan barang ke keranjang, back stack masih sesuai
     */
    @Test
    fun navHost_checkout_rightBackStack()
    {
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[4].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailReward.route)
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }
}

/*
   Catatan
   - TestNavHostController merupakan subclass dari NavHostController dengan tambahan
   fungsi yang dikhususkan untuk testing. Dengan class tersebut, Anda dapat mengakses back
   stack dari class testing

   Selain Anda juga perlu menambahkan ComposeNavigator untuk membuat navigation graph
   pada NavController menggunakan fungsi addNavigator. Hal ini biasanya ditambahkan secara otomatis
   oleh NavHost. Namun, karena tidak menggunakan NavHost pada berkas testing.Anda harus
   menambahkannya secara manual. Tanpa navigator ini. Anda akan mengalami error
    */