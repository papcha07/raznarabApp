package com.example.myapplication.profile.domain.model

import android.net.Uri
import java.io.File

data class UserInfoRequest(
    val email: String? = null,
    val phoneNumber: String? = null,
    val firstName: String? = null,
    val secondName: String? = null,
    val patronymic: String? = null,
    val description: String? = null,
    val image: Uri? = null,
    val deleteAvatar: Boolean? = null
)
