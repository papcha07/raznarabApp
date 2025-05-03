package com.example.myapplication.sharing

class ShareInteractorImpl(private val shareRepository: ShareRepository) : ShareInteractor {
    override fun messageToSupport() {
        shareRepository.messageToSupport()
    }
}