package com.galangaji.bukuwarungassignment.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.galangaji.bukuwarungassignment.R
import com.galangaji.bukuwarungassignment.data.db.entities.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private val context: Context) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val users: MutableList<User> = mutableListOf()
    lateinit var onItemClickListener: (User) -> Unit

    private fun addUser(user: User) {
        if (!users.contains(user)) {
            users.add(user)
            notifyItemChanged(users.size - 1)
        }
    }

    fun addAllUser(data: List<User>) {
        for (user in data) {
            addUser(user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(
            view
        )

    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        holder.bindView(users[position])
        holder.setOnItemClick(users[position], onItemClickListener)
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(user: User) {
            Glide.with(itemView.context)
                .load(user.avatar)
                .centerCrop()
                .thumbnail()
                .into(itemView.img_avatar)
            itemView.apply {
                txt_name.text = "${user.firstName} ${user.lastName}"
                txt_email.text = user.email
            }
        }

        fun setOnItemClick(
            user: User,
            onItemClick: (User) -> Unit
        ) {
            itemView.setOnClickListener {
                onItemClick(user)
            }
        }
    }


}