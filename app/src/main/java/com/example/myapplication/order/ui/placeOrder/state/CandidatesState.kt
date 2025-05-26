package com.example.myapplication.order.ui.placeOrder.state

import com.example.myapplication.order.domain.models.Candidate

sealed interface CandidatesState {
    data class Content(val data: List<Candidate>) : CandidatesState
    object Failed: CandidatesState
    object NoInternet: CandidatesState
}
