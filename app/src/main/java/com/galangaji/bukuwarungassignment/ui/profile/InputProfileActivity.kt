package com.galangaji.bukuwarungassignment.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.galangaji.bukuwarungassignment.R
import com.galangaji.bukuwarungassignment.data.db.AppDatabase
import com.galangaji.bukuwarungassignment.data.db.entities.Profile
import com.galangaji.bukuwarungassignment.data.repositories.ProfileRepository
import com.galangaji.bukuwarungassignment.data.repositories.ProfileRepositoryImpl
import com.galangaji.bukuwarungassignment.utils.snackbar
import kotlinx.android.synthetic.main.activity_input_profile.*

class InputProfileActivity : AppCompatActivity() {


    private lateinit var viewModel: ProfileViewModel

    private lateinit var factory: ProfileViewModelFactory

    private lateinit var repository: ProfileRepository

    private lateinit var db: AppDatabase

    private var profile: Profile? = null


    companion object {

        const val PROFILE_DATA = "profile_data"

        const val SUCCESS_INSERT: Long = 0

        fun createInstance(context: Context, profile: Profile): Intent {
            return Intent(context, InputProfileActivity::class.java).apply {
                putExtra(PROFILE_DATA, profile)
            }

        }

        fun createInstance(context: Context): Intent =
            Intent(context, InputProfileActivity::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        profile = intent.getParcelableExtra(PROFILE_DATA)

        profile?.let {
            setView(it)
        }

        initDependencies()

        initViewModel()

        initOnClickListener()

    }

    private fun setView(profile: Profile) {
        edit_text_first_name.setText(profile.firstName)
        edit_text_last_name.setText(profile.lastName)
        edit_text_email.setText(profile.email)
    }

    private fun initDependencies() {

        db = AppDatabase.invoke(this)

        repository = ProfileRepositoryImpl(db)

        factory = ProfileViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

    }

    private fun initViewModel() {
        viewModel.saveProfile.observe(this, Observer {
            if (it == SUCCESS_INSERT) {
                content.snackbar("input data profile success")
            } else {
                content.snackbar("input data profile failed")
            }
        })
    }

    private fun initOnClickListener() {
        btn_submit_profile.setOnClickListener {
            if (isInputValid()) {
                viewModel.saveProfile(
                    edit_text_first_name.text.toString(),
                    edit_text_last_name.text.toString(),
                    edit_text_email.text.toString()
                )
            }
        }
    }

    private fun isInputValid(): Boolean {
        when {
            edit_text_first_name.text.trim().isEmpty() -> {
                edit_text_first_name.error = "your first name is empty"
                return false
            }
            edit_text_last_name.text.trim().isEmpty() -> {
                edit_text_last_name.error = "your last name is empty"
                return false
            }
            edit_text_email.text.trim().isEmpty() -> {
                edit_text_email.error = "your email is empty"
                return false
            }
            else -> {
                return true
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}