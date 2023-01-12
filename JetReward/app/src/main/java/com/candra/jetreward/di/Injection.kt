package com.candra.jetreward.di

import com.candra.jetreward.data.RewardRepository

object Injection{
    fun provideRepository(): RewardRepository{
        return RewardRepository.getInstance()
    }
}