package com.candra.jetreward.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.candra.jetreward.data.RewardRepository
import com.candra.jetreward.model.OrderReward
import com.candra.jetreward.model.Reward
import com.candra.jetreward.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailRewardViewModel(
    private val repository: RewardRepository
): ViewModel(){
    private val _uiState: MutableStateFlow<UiState<OrderReward>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderReward>>
        get() = _uiState

    fun getRewardById(rewardId: Long){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderRewardById(rewardId))
        }
    }

    fun addToCart(reward: Reward, count: Int){
        viewModelScope.launch {
            repository.updateRewards(reward.id,count)
        }
    }
}