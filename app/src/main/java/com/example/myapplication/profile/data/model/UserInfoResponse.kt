package com.example.myapplication.profile.data.model

import com.example.myapplication.authorization.data.dto.Response
import com.google.gson.annotations.Expose

data class UserInfoResponse(
    val email: String?,
    val phoneNumber: String?,
    val firstName: String?,
    val secondName: String?,
    val patronymic: String?,
    val description: String?
) : Response()
{
    override fun toString(): String {
        return "${email} ${phoneNumber} ${firstName} ${secondName} ${patronymic} ${description} "
    }
}
