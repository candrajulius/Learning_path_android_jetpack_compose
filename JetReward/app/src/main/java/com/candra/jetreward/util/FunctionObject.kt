package com.candra.jetreward.util

import android.content.Context
import android.content.Intent
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.candra.jetreward.R

object FunctionObject {

    fun shareOrder(context: Context,summary: String)
    {
        Intent(Intent.ACTION_SEND).apply {
            type = Constant.OBJECT_TEXT_PLAIN
            putExtra(Intent.EXTRA_SUBJECT,context.getString(R.string.dicoding_reward))
            putExtra(Intent.EXTRA_TEXT,summary)
        }.also {
            context.startActivity(Intent.createChooser(it,context.getString(R.string.dicoding_reward)))
        }
    }

    fun actionToDestinationWithPopUpTo(navController: NavHostController,screen: String)
    {
        navController.navigate(screen){
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}