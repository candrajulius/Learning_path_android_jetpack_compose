package com.candra.mynavigationdrawer.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import com.candra.mynavigationdrawer.model.MenuItem
import com.candra.mynavigationdrawer.util.Constant

object DataMenuItem{
    val items  = listOf(
        MenuItem(
            title = Constant.HOME,
            icon = Icons.Default.Home
        ),
        MenuItem(
            title = Constant.FAVORITE,
            icon = Icons.Default.Favorite
        ),
        MenuItem(
            title = Constant.PROFILE,
            icon = Icons.Default.AccountCircle
        )
    )
}