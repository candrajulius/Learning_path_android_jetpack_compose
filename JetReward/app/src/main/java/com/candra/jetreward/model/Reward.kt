package com.candra.jetreward.model

data class Reward(
    val id: Long,
    val image: Int,
    val title: String,
    val requirePoint: Int
)