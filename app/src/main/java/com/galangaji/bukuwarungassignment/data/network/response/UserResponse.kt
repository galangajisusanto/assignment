package com.galangaji.bukuwarungassignment.data.network.response


import com.galangaji.bukuwarungassignment.data.db.entities.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    val page: Int = 0,
    @SerializedName("per_page")
    val perPage: Int = 0,
    val total: Int = 0,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    val data: List<User> = listOf(),
    val ad: Ad = Ad()
)


data class Ad(
    val company: String = "",
    val url: String = "",
    val text: String = ""
)