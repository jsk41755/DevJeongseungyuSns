package com.jeongseunggyu.devjeongseungyusns.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post (
    val id: Long? = null,
    val title: String? = null,
    val content: String? = null,

    @SerialName("user_id")
    val userID: String? = null,

    @SerialName("created_at")
    val createdAt: String? = null
)

@Serializable
data class PostRequest (
    val title: String,
    val content: String? = null,

    @SerialName("user_id")
    val userID: String? = null
)