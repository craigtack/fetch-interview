package com.craigtack.fetchrewards.di

import com.craigtack.fetchrewards.domain.GetSortedRewardsUseCase
import com.craigtack.fetchrewards.domain.GetSortedRewardsUseCaseImpl
import com.craigtack.fetchrewards.network.RewardsRepository
import com.craigtack.fetchrewards.network.RewardsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RewardsModule {

    @Binds
    abstract fun provideGetSortedRewardsUseCase(
        useCase: GetSortedRewardsUseCaseImpl,
    ): GetSortedRewardsUseCase

    @Binds
    abstract fun provideRewardsRepository(repository: RewardsRepositoryImpl): RewardsRepository
}
