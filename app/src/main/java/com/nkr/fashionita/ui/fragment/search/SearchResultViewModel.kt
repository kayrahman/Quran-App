package com.nkr.fashionita.ui.fragment.search

import android.util.Log
import com.nkr.fashionita.common.BaseViewModel
import com.nkr.fashionita.common.GET_NOTES_ERROR
import com.nkr.fashionita.common.Result
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.ui.Item.notelist.NoteListEvent
import com.nkr.fashionita.ui.adapter.searchResult.SearchResultItemGridAdapter
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SearchResultViewModel(val productRepo: IProductRepository,
                            uiContext: CoroutineContext
) : BaseViewModel<NoteListEvent>(uiContext) {

    val search_adapter = SearchResultItemGridAdapter()


    override fun handleEvent(event: NoteListEvent) {


    }




    fun populateSearchItemList(queryString:String) = launch {
        val notesResult = productRepo.getProductItemBySearch(queryString)

        when (notesResult) {
            // is Result.Value -> noteListState.value = notesResult.value
            is Result.Value -> {
                search_adapter.updateHomeItem(notesResult.value)

                Log.d("item_search",notesResult.value.toString())
            }
            is Result.Error -> errorState.value = GET_NOTES_ERROR
        }
    }





}
