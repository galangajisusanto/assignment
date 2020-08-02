package com.galangaji.bukuwarungassignment.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.galangaji.bukuwarungassignment.data.repositories.UserRepositoryImpl

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val repository: UserRepositoryImpl) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}