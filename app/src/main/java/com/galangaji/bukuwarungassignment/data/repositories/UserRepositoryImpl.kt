package com.galangaji.bukuwarungassignment.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.galangaji.bukuwarungassignment.data.db.AppDatabase
import com.galangaji.bukuwarungassignment.data.db.entities.User
import com.galangaji.bukuwarungassignment.data.network.MyApi
import com.galangaji.bukuwarungassignment.data.network.SafeApiRequest
import com.galangaji.bukuwarungassignment.data.network.response.UserResponse
import com.galangaji.bukuwarungassignment.utils.ApiException
import com.galangaji.bukuwarungassignment.utils.NoInternetException
import com.galangaji.bukuwarungassignment.utils.UseCaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest(), UserRepository {

    private val userList = MutableLiveData<List<User>>()

    init {
        userList.observeForever {
            saveUsers(it)
        }
    }


    override suspend fun fetchUsers(): UseCaseResult<UserResponse> {
        return try {
            val result = apiRequest { api.getUsers() }
            UseCaseResult.Success(result)
        } catch (ex: ApiException) {
            UseCaseResult.Error(ex)
        } catch (ex: NoInternetException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

    override suspend fun getUsers(): LiveData<List<User>> {
        return withContext(Dispatchers.IO) {

            when (val response = fetchUsers()) {
                is UseCaseResult.Success -> {
                    userList.postValue(response.data.data)
                    Log.d("data_user", response.data.data.toString())
                }
            }

            db.getUserDao().getAllUsers()
        }
    }

    override fun saveUsers(users: List<User>) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getUserDao().saveAllUsers(users)
        }

    }
}