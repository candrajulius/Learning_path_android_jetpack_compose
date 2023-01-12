package com.candra.jetreward

import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.junit.Assert

fun NavController.assertCurrentRouteName(expectedRouteName: String)
{
    Assert.assertEquals(expectedRouteName,currentBackStackEntry?.destination?.route)
}