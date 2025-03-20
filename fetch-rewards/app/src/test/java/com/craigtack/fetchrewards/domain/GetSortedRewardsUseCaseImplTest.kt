package com.craigtack.fetchrewards.domain

import com.craigtack.fetchrewards.network.RewardsRepository
import com.craigtack.fetchrewards.ui.Reward
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetSortedRewardsUseCaseImplTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private val rewardsRepository = mockk<RewardsRepository>()

    private val useCase = GetSortedRewardsUseCaseImpl(
        ioDispatcher = testDispatcher,
        rewardsRepository = rewardsRepository,
    )

    @Test
    fun `invoke - sorts rewards first by list ID and then by name`() = runTest {
        val firstReward = Reward(id = 0, listId = 1, name = "Item 0")
        val secondReward = Reward(id = 1, listId = 1, name = "Item 1")
        val thirdReward = Reward(id = 2, listId = 2, name = "Item 2")
        coEvery { rewardsRepository.getRewards() } returns listOf(
            thirdReward,
            secondReward,
            firstReward,
        )

        val sortedRewards = useCase()

        assertEquals(3, sortedRewards.size)
        assertEquals(firstReward, sortedRewards[0])
        assertEquals(secondReward, sortedRewards[1])
        assertEquals(thirdReward, sortedRewards[2])
    }
}
