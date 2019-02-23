package com.example.myapplication.model

class ChatMessage(val id: String, val text: String, val fromid: String, val toId: String, val timeStamp: Long) {
    constructor() : this("", "", "", "", 0)
}