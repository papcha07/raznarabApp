package com.example.myapplication.profile.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserInfoRequest(
    @Expose val email: String? = null,
    @Expose val phoneNumber: String? = null,
    @Expose val rating : Int? = null,
    @Expose val firstName: String? = null,
    @Expose val secondName: String? = null,
    @Expose val patronymic: String? = null,
    @Expose val description: String? = null,
    @Expose val avatarPath: String? = null
)
