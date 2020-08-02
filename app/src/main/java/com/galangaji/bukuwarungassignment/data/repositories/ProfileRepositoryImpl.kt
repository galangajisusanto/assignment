package com.galangaji.bukuwarungassignment.data.repositories

import androidx.lifecycle.LiveData
import com.galangaji.bukuwarungassignment.data.db.AppDatabase
import com.galangaji.bukuwarungassignment.data.db.entities.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileRepositoryImpl(
    private val db: AppDatabase
) : ProfileRepository {

    override suspend fun getProfile(): LiveData<List<Profile>> {
        return withContext(Dispatchers.IO) {
            db.getProfileDao().getAllProfile()
        }
    }

    override suspend fun saveProfile(profile: Profile): Long {
        return withContext(Dispatchers.IO) {
            db.getProfileDao().saveProfile(profile)
        }
    }
}