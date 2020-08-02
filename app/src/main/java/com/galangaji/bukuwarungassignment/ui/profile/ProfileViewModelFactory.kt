package com.galangaji.bukuwarungassignment.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.galangaji.bukuwarungassignment.data.repositories.ProfileRepository

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(private val repository: ProfileRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(repository) as T
    }
}