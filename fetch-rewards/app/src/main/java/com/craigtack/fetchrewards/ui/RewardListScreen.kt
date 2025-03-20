package com.craigtack.fetchrewards.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.craigtack.fetchrewards.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardListScreen(modifier: Modifier = Modifier, viewModel: RewardListViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        when (val newUiState = uiState) {
            is RewardListUiState.Loading -> {
                CircularProgressIndicator()
            }
            is RewardListUiState.Success -> {
                RewardList(newUiState.rewards)
            }
            is RewardListUiState.Error -> {
                Text(stringResource(R.string.an_error_occurred, newUiState.message))
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.fetchRewards()
    }
}

@Composable
fun RewardList(rewards: List<Reward>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(rewards) { reward ->
            RewardRow(reward)
        }
    }
}

@Composable
fun RewardRow(reward: Reward, modifier: Modifier = Modifier) {
    OutlinedCard(modifier.fillMaxWidth()) {
        Column(Modifier.padding(8.dp)) {
            Text(stringResource(R.string.id_label_value, reward.id))
            Text(stringResource(R.string.list_id_label_value, reward.listId))
            Text(stringResource(R.string.name_label_value, reward.name))
        }
    }
}

@Preview
@Composable
fun RewardRowPreview() {
    RewardRow(
        Reward(
            id = 0,
            listId = 1,
            name = "Item 0",
        )
    )
}
