package com.nkr.fashionita.ui.Item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nkr.fashionita.common.BaseViewModel
import com.nkr.fashionita.model.Product
import com.nkr.fashionita.common.Result
import com.nkr.fashionita.model.BannerItem
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.ui.Item.notelist.NoteListEvent
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NoteListViewModel(

    val productRepo: IProductRepository,
    uiContext: CoroutineContext
) : BaseViewModel<NoteListEvent>(uiContext) {

    private val noteListState = MutableLiveData<List<Product>>()
    val noteList: LiveData<List<Product>> get() = noteListState

    private val editNoteState = MutableLiveData<String>()
    val editNote: LiveData<String> get() = editNoteState


    private val bannerListState = MutableLiveData<List<BannerItem>>()
    val bannerList:LiveData<List<BannerItem>> get() = bannerListState

    var listOfBannerItem = listOf<BannerItem>()



    override fun handleEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.OnStart -> {
              //  getNotes()


            }
            is NoteListEvent.OnNoteItemClick -> goToItemDetailView(event.position)

            is NoteListEvent.OnBannerItemStart ->  getbanners()
        }
    }




    private fun goToItemDetailView(position: Int) {
        editNoteState.value = noteList.value!![position].creationDate


       // Log.d("note_id",editNoteState.value)


    }


/*
    private fun getNotes() = launch {
        val notesResult = noteRepo.getNotes()

        when (notesResult) {
            is Result.Value -> noteListState.value = notesResult.value
            is Result.Error -> errorState.value = GET_NOTES_ERROR
        }
    }

    */


    private fun getbanners() = launch {
       // FirestoreUtil.getBannerImagesListener(this::listOfBannerImages)

            val banner_items = productRepo.getBannerItems()


            Log.d("call_banner", "true")

            when (banner_items) {
                is Result.Value ->
                {
                    bannerListState.value = banner_items.value
                    listOfBannerItem = banner_items.value
                }
                is Result.Error -> errorState.value = "Banner Error"
            }

        }



}