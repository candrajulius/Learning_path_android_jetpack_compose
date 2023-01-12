package com.candra.jetreward.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.candra.jetreward.R
import com.candra.jetreward.ViewModelFactory
import com.candra.jetreward.di.Injection
import com.candra.jetreward.model.OrderReward
import com.candra.jetreward.ui.common.UiState
import com.candra.jetreward.ui.components.RewardItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
){

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState){
            is UiState.Loading -> {
                viewModel.getAllRewards()
            }
            is UiState.Success -> {
                HomeContent(
                    orderReward = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail)
            }
            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun HomeContent(
   orderReward: List<OrderReward>,
   modifier: Modifier = Modifier,
   navigateToDetail : (Long) -> Unit
){
    LazyVerticalGrid(columns = GridCells.Adaptive(160.dp), contentPadding = PaddingValues(16.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp),
    modifier = modifier.testTag("RewardList")){
        items(orderReward){data ->
            RewardItem(image = data.reward.image, title = data.reward.title,
                requirePoint = data.reward.requirePoint, modifier = Modifier.clickable {
                    navigateToDetail(data.reward.id)
                }
            )
        }
    }
}
/*
Selanjutnya, tambahkan event navigateToDetail pada HomeScreen ketika item diklik menggunakan modifier
clickable
 */