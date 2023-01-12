package com.candra.jetreward.ui.screen.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.candra.jetreward.R
import com.candra.jetreward.ViewModelFactory
import com.candra.jetreward.di.Injection
import com.candra.jetreward.ui.common.UiState
import com.candra.jetreward.ui.components.CartItem
import com.candra.jetreward.ui.components.OrderButton

@Composable
fun CartScreen(
    viewModel: CardViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    orderButtonClicked: (String) -> Unit,
){
  viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
      when(uiState){
          is UiState.Loading -> {
              viewModel.getAddedOrderRewards()
          }
          is UiState.Success -> {
              CartContent(
                  uiState.data,
                  onProductCountChanged = { rewardId, count ->
                      viewModel.updateReward(rewardId,count)
                  },
                  onOrderButtonClicked = orderButtonClicked
              )
          }
          is UiState.Error -> {}
      }
  }
}

@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Long,count: Int) -> Unit,
    onOrderButtonClicked:(String) -> Unit,
    modifier: Modifier = Modifier
){
    val sharedMessage = stringResource(
        R.string.share_message,
        state.orderReward.count(),
        state.totalRequiredPoint
    )
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Text(text = stringResource(id = R.string.menu_cart),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            fontWeight = FontWeight.Bold,
             fontSize = 18.sp,
             textAlign = TextAlign.Center
            )
        }
        OrderButton(text = stringResource(id = R.string.total_order,state.totalRequiredPoint),
        enabled = state.orderReward.isNotEmpty(),
            onClick = {
                onOrderButtonClicked(sharedMessage)
            },
            modifier = Modifier.padding(16.dp))
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ){
            items(state.orderReward, key = {it.reward.id}){item ->
                CartItem(
                    rewardId = item.reward.id,
                    image = item.reward.image,
                    title = item.reward.title,
                    totalPoint = item.reward.requirePoint * item.count,
                    count = item.count,
                    onProductCountChanged = onProductCountChanged
                )
                Divider()
            }
        }
    }
}