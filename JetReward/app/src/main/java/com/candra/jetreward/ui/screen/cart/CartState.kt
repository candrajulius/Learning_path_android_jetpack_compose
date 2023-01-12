package com.candra.jetreward.ui.screen.cart

import com.candra.jetreward.model.OrderReward

data class CartState(
    val orderReward: List<OrderReward>,
    val totalRequiredPoint: Int
)