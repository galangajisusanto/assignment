package com.galangaji.bukuwarungassignment.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galangaji.bukuwarungassignment.data.db.entities.Profile
import com.galangaji.bukuwarungassignment.data.repositories.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {

    private val mShowLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean>
        get() = mShowLoading

    private val mProfile = MutableLiveData<Profile>()
    val profile: LiveData<Profile>
        get() = mProfile

    private val mEmptyProfile = MutableLiveData<Boolean>()
    val emptyProfile: LiveData<Boolean>
        get() = mEmptyProfile

    private val mSaveProfile = MutableLiveData<Long>()
    val saveProfile: LiveData<Long>
        get() = mSaveProfile

    init {
        getProfile()
    }

    private fun getProfile() {
        mShowLoading.value = true
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getProfile()
            }
            mShowLoading.value = false

            result.observeForever {
                if (it.isNotEmpty()) {
                    mEmptyProfile.value = false
                    mProfile.value = it[0]
                } else {
                    mEmptyProfile.value = true
                }
            }

        }

    }

    fun saveProfile(firstName: String, lastName: String, email: String) {

        viewModelScope.launch {
            val result =
                repository.saveProfile(
                    Profile(
                        id = 0,
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        avatar = ""
                    )
                )
            mSaveProfile.value = result
        }
    }


}