package com.example.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid: String, val firstname: String, val lastname: String, val profileImage: String) : Parcelable {
    constructor() : this("", "", "", "")
}