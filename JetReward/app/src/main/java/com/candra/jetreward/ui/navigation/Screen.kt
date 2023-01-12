package com.candra.jetreward.ui.navigation

import com.candra.jetreward.util.Constant

sealed class Screen (val route: String){
    object Home : Screen(Constant.OBJECT_HOME_SCREEN)
    object Cart : Screen(Constant.OBJECT_CART_SCREEN)
    object Profile : Screen(Constant.OBJECT_PROFILE_SCREEN)
    object DetailReward : Screen("${Constant.OBJECT_HOME_SCREEN}/{${Constant.OBJECT_ARGUMENT_ID}}"){
        fun createRoute(rewardId: Long) = "${Constant.OBJECT_HOME_SCREEN}/$rewardId"
    }
}