package com.nkr.quran.framework.presentation.ui.viewModel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.codingwithmitch.daggerhiltplayground.business.domain.state.DataState
import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.business.domain.models.Verse
import com.nkr.quran.business.interactors.QuranInfoRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber


class QuranChapterViewModel
@ViewModelInject
constructor(private val quranInfoRepo: QuranInfoRepository,
            @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    val chapterList = MutableLiveData<List<Chapter>>()
    val verseList = MutableLiveData<List<Verse>>()

    fun getQuranChapters() {
        viewModelScope.launch {
            quranInfoRepo.execute()
               .onEach {
                   when(it){
                      is DataState.Success ->{
                          chapterList.value = it.data
                      }
                   }

                   Timber.d("Quran Chapters$it")
                   Log.d("chapters",it.toString())
               }.launchIn(viewModelScope)
        }
    }


    fun getVersesByChapterNumber(){
        viewModelScope.launch {
          val response =  quranInfoRepo.getVersesByChapterNumber("1","21","bn")
            verseList.value = response
        }
    }

}