package com.example.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
class User(val uid: String, val firstname: String, val lastname: String, val profileImage: String) : Parcelable,
    Serializable {
    constructor() : this("", "", "", "")
}