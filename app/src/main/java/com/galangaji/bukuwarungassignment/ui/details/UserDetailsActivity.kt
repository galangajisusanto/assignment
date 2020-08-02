package com.galangaji.bukuwarungassignment.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.galangaji.bukuwarungassignment.R
import com.galangaji.bukuwarungassignment.data.db.entities.User
import kotlinx.android.synthetic.main.activity_user_details.*


class UserDetailsActivity : AppCompatActivity() {

    companion object {

        private const val USER_DATA = "user_data"

        fun createInstance(context: Context, user: User): Intent {
            return Intent(context, UserDetailsActivity::class.java).apply {
                putExtra(USER_DATA, user)
            }
        }
    }

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        user = intent.getParcelableExtra(USER_DATA)

        setView(user)

    }

    private fun setView(user: User) {
        Glide.with(baseContext)
            .load(user.avatar)
            .centerCrop()
            .thumbnail()
            .into(img_avatar)
        txt_name.text = "${user.firstName} ${user.lastName}"
        txt_email.text = user.email
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}