package com.craigtack.fetchrewards.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craigtack.fetchrewards.domain.GetSortedRewardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RewardListViewModel @Inject constructor(
    private val getSortedRewardsUseCase: GetSortedRewardsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<RewardListUiState>(RewardListUiState.Loading)
    val uiState: StateFlow<RewardListUiState> = _uiState.asStateFlow()

    fun fetchRewards() {
        viewModelScope.launch {
            try {
                val rewards = getSortedRewardsUseCase()
                _uiState.update { RewardListUiState.Success(rewards) }
            } catch (e: Exception) {
                _uiState.update { RewardListUiState.Error(e.message.toString()) }
            }
        }
    }
}
