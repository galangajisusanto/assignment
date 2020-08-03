package com.galangaji.bukuwarungassignment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.galangaji.bukuwarungassignment.R
import com.galangaji.bukuwarungassignment.data.db.AppDatabase
import com.galangaji.bukuwarungassignment.data.network.MyApi
import com.galangaji.bukuwarungassignment.data.network.NetworkConnectionInterceptor
import com.galangaji.bukuwarungassignment.data.repositories.UserRepository
import com.galangaji.bukuwarungassignment.data.repositories.UserRepositoryImpl
import com.galangaji.bukuwarungassignment.ui.details.UserDetailsActivity
import com.galangaji.bukuwarungassignment.ui.home.adapter.UserAdapter
import com.galangaji.bukuwarungassignment.utils.hide
import com.galangaji.bukuwarungassignment.utils.show
import com.galangaji.bukuwarungassignment.utils.toast
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {


    private lateinit var viewModel: HomeViewModel

    private lateinit var factory: HomeViewModelFactory

    private lateinit var repository: UserRepository

    private lateinit var api: MyApi

    private lateinit var db: AppDatabase

    private lateinit var networkConnectionInterceptor: NetworkConnectionInterceptor

    private lateinit var mUserAdapter: UserAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initDependencies()

        prepareRecycler()

        initViewModel()

    }

    private fun initDependencies() {
        networkConnectionInterceptor = NetworkConnectionInterceptor(context!!)

        api = MyApi.invoke(networkConnectionInterceptor)

        db = AppDatabase.invoke(context!!)

        repository = UserRepositoryImpl(api, db)

        factory = HomeViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
    }

    private fun prepareRecycler() {
        mUserAdapter = UserAdapter(context!!)
        mUserAdapter.onItemClickListener = { user ->
            startActivity(UserDetailsActivity.createInstance(context!!, user))
        }

        val linearLayoutManager = LinearLayoutManager(context)
        rv_user.apply {
            layoutManager = linearLayoutManager
            adapter = mUserAdapter
        }
    }

    private fun initViewModel() {

        viewModel.userList.observe(viewLifecycleOwner, Observer { newUserList ->
            mUserAdapter.addAllUser(newUserList)
        })

        viewModel.showLoading.observe(viewLifecycleOwner, Observer { showLoading ->
            if (showLoading) progress_bar.show() else progress_bar.hide()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { showError ->
            context!!.toast(showError)
        })
    }

}