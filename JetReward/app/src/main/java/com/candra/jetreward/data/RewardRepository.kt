package com.candra.jetreward.data

import com.candra.jetreward.model.FakeRewardDataSource
import com.candra.jetreward.model.OrderReward
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RewardRepository {
    private val orderRewards = mutableListOf<OrderReward>()

    init {
        if (orderRewards.isEmpty()){
            FakeRewardDataSource.dummyRewards.forEach {
                orderRewards.add(OrderReward(it,0))
            }
        }
    }

    fun getAllRewards():Flow<List<OrderReward>>{
        return flowOf(orderRewards)
    }

    fun getOrderRewardById(rewardId: Long): OrderReward{
        return orderRewards.first {
            it.reward.id == rewardId
        }
    }

    fun updateRewards(rewardId: Long,newContValue: Int): Flow<Boolean>{
        val index = orderRewards.indexOfFirst { it.reward.id == rewardId }
        val result = if (index >= 0){
            val orderReward = orderRewards[index]
            orderRewards[index] =
                orderReward.copy(reward = orderReward.reward, count = newContValue)
            true
        }else{
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderRewards(): Flow<List<OrderReward>>{
        return getAllRewards().map { orderRewards ->
            orderRewards.filter { orderReward -> orderReward.count != 0 }
        }
    }

    companion object{
        @Volatile
        private var instance: RewardRepository? = null

        fun getInstance(): RewardRepository =
            instance ?: synchronized(this){
                RewardRepository().apply {
                    instance = this
                }
            }
    }
}