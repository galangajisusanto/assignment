package com.galangaji.bukuwarungassignment.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.galangaji.bukuwarungassignment.data.db.entities.Profile


@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(profile: Profile): Long

    @Query("SELECT * FROM profile")
    fun getAllProfile(): LiveData<List<Profile>>
}