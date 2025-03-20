package com.craigtack.fetchrewards.ui

import com.craigtack.fetchrewards.domain.GetSortedRewardsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class RewardListViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private val getSortedRewardsUseCase = mockk<GetSortedRewardsUseCase>()

    private val viewModel = RewardListViewModel(getSortedRewardsUseCase)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchRewards - updates state to success when use case completes successfully`() = runTest {
        coEvery { getSortedRewardsUseCase() } returns listOf()
        assertIs<RewardListUiState.Loading>(viewModel.uiState.value)

        viewModel.fetchRewards()

        assertIs<RewardListUiState.Success>(viewModel.uiState.value)
    }

    @Test
    fun `fetchRewards - updates state to error when use case fails`() = runTest {
        coEvery { getSortedRewardsUseCase() } throws Exception()
        assertIs<RewardListUiState.Loading>(viewModel.uiState.value)

        viewModel.fetchRewards()

        assertIs<RewardListUiState.Error>(viewModel.uiState.value)
    }
}
