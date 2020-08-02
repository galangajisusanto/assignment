package com.galangaji.bukuwarungassignment.data.repositories

import androidx.lifecycle.LiveData
import com.galangaji.bukuwarungassignment.data.db.entities.Profile

interface ProfileRepository {
    suspend fun getProfile(): LiveData<List<Profile>>
    suspend fun saveProfile(profile: Profile): Long
}