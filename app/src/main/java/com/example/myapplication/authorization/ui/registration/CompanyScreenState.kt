package com.example.myapplication.authorization.ui.registration

import com.example.myapplication.authorization.domain.model.Company

sealed interface CompanyScreenState {
    data class Success(val user: Company) : CompanyScreenState
    object AlreadyExist : CompanyScreenState
    object Error : CompanyScreenState
}