package com.galangaji.bukuwarungassignment.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val email: String = "",
    @SerializedName("first_name")
    val firstName: String = "",
    @SerializedName("last_name")
    val lastName: String = "",
    val avatar: String = ""
) : Parcelable {
}