package com.example.chatapplication

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(var id:String? , var username:String?, var status :Boolean = false):Parcelable{}