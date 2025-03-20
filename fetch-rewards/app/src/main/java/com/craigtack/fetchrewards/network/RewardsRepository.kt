package com.craigtack.fetchrewards.network

import com.craigtack.fetchrewards.di.IoDispatcher
import com.craigtack.fetchrewards.ui.Reward
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RewardsRepository {
    suspend fun getRewards(): List<Reward>
}

class RewardsRepositoryImpl @Inject constructor(
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val rewardsService: RewardsService,
) : RewardsRepository {

    override suspend fun getRewards(): List<Reward> {
        return withContext(ioDispatcher) {
            rewardsService.getRewards()
                .mapNotNull {
                    // Discard rewards with no name.
                    if (it.name.isNullOrBlank().not()) {
                        Reward(id = it.id, listId = it.listId, name = it.name)
                    } else {
                        null
                    }
                }
        }
    }
}
