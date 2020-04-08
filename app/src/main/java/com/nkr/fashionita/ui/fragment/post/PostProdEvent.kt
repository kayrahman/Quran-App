package com.nkr.fashionita.ui.fragment.post

sealed class PostProdEvent {
    object OnStart : PostProdEvent()
    data class OnClick(val noteId: String) : PostProdEvent()
}
