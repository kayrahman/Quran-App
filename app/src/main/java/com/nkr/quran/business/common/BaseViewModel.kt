package com.nkr.quran.business.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nkr.quran.util.default
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<T>(protected val uiContext: CoroutineContext) : ViewModel(), CoroutineScope {
    abstract fun handleEvent(event: T)

   //cancellation
    protected lateinit var jobTracker: Job

    init {
        jobTracker = Job()
    }

    var showLoading = MutableLiveData<Boolean>().default(false)


    protected val errorState = MutableLiveData<String>()
    val error: LiveData<String> get() = errorState

    protected val loadingState = MutableLiveData<Unit>()
    val loading: LiveData<Unit> get() = loadingState


    /**
     * show loading flag
     */
    fun apiCallStart() {
        showLoading.value = true

    }

    /**
     * dismiss loading function
     */
    fun apiCallFinish() {
        showLoading.value = false

    }


    override val coroutineContext: CoroutineContext
        get() = uiContext + jobTracker

}