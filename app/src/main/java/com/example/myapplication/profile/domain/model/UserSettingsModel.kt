package com.example.myapplication.profile.domain.model

data class UserSettingsModel(
    val email: String?,
    val phoneNumber: String?,
    val rating: Int?,
    val firstName: String?,
    val secondName: String?,
    val patronymic: String?,
    val description: String?,
    val avatarPath: String?
){
    override fun toString(): String {
        return "${email} ${phoneNumber} ${firstName} ${secondName} ${patronymic} ${description} "
    }
}