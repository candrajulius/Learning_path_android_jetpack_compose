package com.candra.jetreward.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.candra.jetreward.data.RewardRepository
import com.candra.jetreward.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CardViewModel(
    private val repository: RewardRepository
): ViewModel(){
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
    get() = _uiState

    fun getAddedOrderRewards()
    {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderRewards().collect { orderReward ->
                val totalRequirePoint = orderReward.sumOf { it.reward.requirePoint * it.count }
                _uiState.value = UiState.Success(CartState(orderReward,totalRequirePoint))
            }
        }
    }

    fun updateReward(rewardId: Long, count: Int){
        viewModelScope.launch {
            repository.updateRewards(rewardId,count)
                .collect { isUpdated ->
                    if (isUpdated){
                        getAddedOrderRewards()
                    }
                }
        }
    }
}