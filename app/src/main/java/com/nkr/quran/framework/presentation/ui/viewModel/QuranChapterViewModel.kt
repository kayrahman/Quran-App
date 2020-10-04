package com.nkr.quran.framework.presentation.ui.viewModel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.codingwithmitch.daggerhiltplayground.business.domain.state.DataState
import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.business.interactors.QuranInfo
import com.nkr.quran.framework.datasource.network.IQuranApiService
import com.nkr.quran.framework.datasource.network.QuranServiceImpl
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber


class QuranChapterViewModel
@ViewModelInject
constructor( private val quranInfo: QuranInfo,
             @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    val chapterList = MutableLiveData<List<Chapter>>()

    fun getQuranChapters() {
        viewModelScope.launch {

            quranInfo.execute()
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



}