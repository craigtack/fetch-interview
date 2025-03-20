package com.craigtack.fetchrewards.ui

sealed interface RewardListUiState {
    object Loading : RewardListUiState
    data class Success(val rewards: List<Reward>) : RewardListUiState
    data class Error(val message: String) : RewardListUiState
}
