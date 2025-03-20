package com.craigtack.fetchrewards.domain

import com.craigtack.fetchrewards.di.IoDispatcher
import com.craigtack.fetchrewards.network.RewardsRepository
import com.craigtack.fetchrewards.ui.Reward
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * A use case that gets rewards from the API and sorts the results first by list ID and then by name.
 */
interface GetSortedRewardsUseCase {
    suspend operator fun invoke(): List<Reward>
}

class GetSortedRewardsUseCaseImpl @Inject constructor(
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val rewardsRepository: RewardsRepository,
) : GetSortedRewardsUseCase {

    override suspend operator fun invoke(): List<Reward> {
        return withContext(ioDispatcher) {
            rewardsRepository.getRewards().sortedWith { reward1, reward2 ->
                if (reward1.listId == reward2.listId) {
                    reward1.name.compareTo(reward2.name)
                } else {
                    reward1.listId.compareTo(reward2.listId)
                }
            }
        }
    }
}
