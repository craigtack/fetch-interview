package com.craigtack.fetchrewards.ui

/**
 * A reward with a non-null/non-blank [name].
 */
data class Reward(
    val id: Int,
    val listId: Int,
    val name: String,
)
