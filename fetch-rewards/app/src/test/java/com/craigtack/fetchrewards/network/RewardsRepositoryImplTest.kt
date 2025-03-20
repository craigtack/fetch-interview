package com.craigtack.fetchrewards.network

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class RewardsRepositoryImplTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private val rewardsService = mockk<RewardsService>()

    private val repo = RewardsRepositoryImpl(
        ioDispatcher = testDispatcher,
        rewardsService = rewardsService,
    )

    @Test
    fun `getRewards - fetches rewards and filters out rewards with null or blank names`() = runTest {
        val rewardWithNullName = RewardApiModel(id = 0, listId = 1, name = null)
        val rewardWithEmptyName = RewardApiModel(id = 1, listId = 1, name = "")
        val rewardWithNonEmptyName = RewardApiModel(id = 2, listId = 1, name = "Item 2")
        coEvery { rewardsService.getRewards() } returns listOf(
            rewardWithNullName,
            rewardWithEmptyName,
            rewardWithNonEmptyName,
        )

        val rewards = repo.getRewards()

        assertEquals(1, rewards.size)
        assertEquals(rewardWithNonEmptyName.id, rewards.first().id)
        assertEquals(rewardWithNonEmptyName.listId, rewards.first().listId)
        assertEquals(rewardWithNonEmptyName.name, rewards.first().name)
    }
}
