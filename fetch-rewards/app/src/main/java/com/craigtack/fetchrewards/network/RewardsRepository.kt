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
    private val rewardsComparator: RewardsComparator,
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
                .sortedWith(rewardsComparator)
        }
    }
}

/**
 * A comparator that sorts rewards first by list ID and then by name.
 */
class RewardsComparator @Inject constructor() : Comparator<Reward> {
    override fun compare(o1: Reward, o2: Reward): Int {
        return if (o1.listId == o2.listId) {
            o1.name.compareTo(o2.name)
        } else {
            o1.listId.compareTo(o2.listId)
        }
    }
}
