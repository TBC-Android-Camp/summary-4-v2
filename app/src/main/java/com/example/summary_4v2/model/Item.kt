package com.example.summary_4v2.model

import com.squareup.moshi.Json

data class Item(
    val id: Int,
    val image: String?,
    @Json(name = "is_typing")
    val isTyping: Boolean,
    @Json(name = "last_active")
    val lastActive: String,
    @Json(name = "last_message")
    val lastMessage: String,
    @Json(name = "laste_message_type")
    val lastMessageType: String,
    val owner: String,
    @Json(name = "unread_messages")
    val unreadMessages: Int
)