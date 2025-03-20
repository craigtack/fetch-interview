package com.craigtack.fetchrewards.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RewardsModule {

    @Binds
    abstract fun provideRewardsRepository(repository: RewardsRepositoryImpl): RewardsRepository
}
