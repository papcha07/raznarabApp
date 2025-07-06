package com.example.myapplication.order.data.dto.prof

import com.example.myapplication.order.domain.models.Profession


data class ProfessionResponse(
    val professionsList : MutableList<Profession>
) : com.example.myapplication.order.data.dto.Response()
