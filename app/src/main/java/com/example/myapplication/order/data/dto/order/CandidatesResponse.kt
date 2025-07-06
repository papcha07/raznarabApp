package com.example.myapplication.order.data.dto.order

import com.example.myapplication.order.data.dto.Response
import com.example.myapplication.order.domain.models.Candidate

data class CandidatesResponse(
    val candidates: List<Candidate>
) : Response()