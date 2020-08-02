package com.galangaji.bukuwarungassignment.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.galangaji.bukuwarungassignment.R
import com.galangaji.bukuwarungassignment.data.db.AppDatabase
import com.galangaji.bukuwarungassignment.data.db.entities.Profile
import com.galangaji.bukuwarungassignment.data.repositories.ProfileRepository
import com.galangaji.bukuwarungassignment.data.repositories.ProfileRepositoryImpl
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {


    private lateinit var viewModel: ProfileViewModel

    private lateinit var factory: ProfileViewModelFactory

    private lateinit var repository: ProfileRepository

    private lateinit var db: AppDatabase

    private lateinit var profile: Profile


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initDependencies()

        initViewModel()

        initOnClickListener()
    }

    private fun initDependencies() {

        db = AppDatabase.invoke(context!!)

        repository = ProfileRepositoryImpl(db)

        factory = ProfileViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)


    }

    private fun initViewModel() {

        viewModel.showLoading.observe(viewLifecycleOwner, Observer { showLoading ->
            progress_bar.visibility = if (showLoading) View.VISIBLE else View.GONE
        })

        viewModel.emptyProfile.observe(viewLifecycleOwner, Observer { isProfileEmpty ->
            if (isProfileEmpty) {
                group_not_empty_layout.visibility = View.GONE
                group_empty_layout.visibility = View.VISIBLE
            } else {
                group_not_empty_layout.visibility = View.VISIBLE
                group_empty_layout.visibility = View.GONE
            }
        })

        viewModel.profile.observe(viewLifecycleOwner, Observer { myProfile ->
            setView(myProfile)
            profile = myProfile
        })
    }

    private fun initOnClickListener() {
        btn_add_profile.setOnClickListener {
            startActivity(InputProfileActivity.createInstance(context!!))
        }

        btn_edit_profile.setOnClickListener {
            startActivity(InputProfileActivity.createInstance(context!!, profile))
        }
    }

    private fun setView(profile: Profile) {
        txt_name.text = "${profile.firstName} ${profile.lastName}"
        txt_email.text = profile.email
    }

}