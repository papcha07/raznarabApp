package com.example.myapplication.sharing

import android.content.Context
import android.content.Intent
import android.net.Uri

class ShareRepositoryImpl(private val context: Context) : ShareRepository {
    override fun messageToSupport() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:legoman056@gmail.com")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}