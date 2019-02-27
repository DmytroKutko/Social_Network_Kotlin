package com.example.myapplication.model

class Image(val url: String, val timeStamp: Long) {
    constructor() : this("", -1)
}