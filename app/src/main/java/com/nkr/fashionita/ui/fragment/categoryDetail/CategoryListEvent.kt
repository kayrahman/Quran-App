package com.nkr.fashionita.ui.fragment.categoryDetail

import com.nkr.fashionita.ui.Item.notedetail.NoteDetailEvent

sealed class CategoryListEvent {
    data class OnStart(val category_name: String) : CategoryListEvent()
}