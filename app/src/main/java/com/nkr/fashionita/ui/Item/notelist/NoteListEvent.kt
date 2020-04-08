package com.nkr.fashionita.ui.Item.notelist

sealed class NoteListEvent {
    data class OnNoteItemClick(val position: Int) : NoteListEvent()
    object OnNewNoteClick : NoteListEvent()
    object OnStart : NoteListEvent()
    object OnBannerItemStart:NoteListEvent()
    data class OnWishListItemClick(val prod_uid:String,val isFavourite:Boolean,val adapter_position:Int) : NoteListEvent()

}