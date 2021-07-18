package com.example.chatapplication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GroupModel(var id :String?, var name:String?, var usersId:ArrayList<String>):Parcelable {
}