package com.example.myapplication.profile.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserInfoRequest(
    @Expose val email: String? = null,
    @Expose val phoneNumber: String? = null,
    @Expose val firstName: String? = null,
    @Expose val secondName: String? = null,
    @Expose val patronymic: String? = null,
    @Expose val description: String? = null
)
