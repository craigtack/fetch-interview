package com.craigtack.fetchrewards.network

import retrofit2.http.GET

interface RewardsService {
    @GET("hiring.json")
    suspend fun getRewards(): List<RewardApiModel>
}
