package com.candra.jetreward

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.candra.jetreward.data.RewardRepository
import com.candra.jetreward.ui.screen.cart.CardViewModel
import com.candra.jetreward.ui.screen.detail.DetailRewardViewModel
import com.candra.jetreward.ui.screen.home.HomeViewModel
import java.lang.IllegalArgumentException


class ViewModelFactory(private val repository: RewardRepository):
        ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(DetailRewardViewModel::class.java)){
            return DetailRewardViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(CardViewModel::class.java)){
            return CardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}