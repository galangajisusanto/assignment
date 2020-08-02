package com.galangaji.bukuwarungassignment.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galangaji.bukuwarungassignment.data.db.entities.User
import com.galangaji.bukuwarungassignment.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: UserRepository) : ViewModel() {


    private val mShowLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean>
        get() = mShowLoading

    private val mUsersList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>>
        get() = mUsersList

    private val mErrorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = mErrorMessage

    init {
        getUser()
    }


    private fun getUser() {

        mShowLoading.value = true

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getUsers()
            }

            mShowLoading.value = false
            result.observeForever {
                mUsersList.postValue(it)
            }
        }

        return
    }
}