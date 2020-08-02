package com.galangaji.bukuwarungassignment.data.repositories

import androidx.lifecycle.LiveData
import com.galangaji.bukuwarungassignment.data.db.entities.User
import com.galangaji.bukuwarungassignment.data.network.response.UserResponse
import com.galangaji.bukuwarungassignment.utils.UseCaseResult

interface UserRepository {
    suspend fun fetchUsers(): UseCaseResult<UserResponse>
    suspend fun getUsers(): LiveData<List<User>>
    fun saveUsers(users: List<User>)
}