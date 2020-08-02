package com.galangaji.bukuwarungassignment.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.galangaji.bukuwarungassignment.data.db.entities.User


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllUsers(users: List<User>)

    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<User>>
}