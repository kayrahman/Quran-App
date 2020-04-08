package com.nkr.fashionita.ui.fragment.post

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkr.fashionita.model.App
import com.nkr.fashionita.model.CategoryItem

import com.nkr.fashionita.ui.adapter.CategoryPostAdapter
import com.nkr.fashionita.ui.adapter.ChooseDesignCategoryImageAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ChooseDesignCategoryViewModel(imagePaths:ArrayList<String>) : ViewModel(),CoroutineScope {

    // var allImageList: MutableList<String> = mutableListOf()
    var adapter: ChooseDesignCategoryImageAdapter = ChooseDesignCategoryImageAdapter()
    var categoryListAdapter = CategoryPostAdapter()


    var category_list = arrayListOf<CategoryItem>()

    lateinit var jobTracker: Job


    init {

        jobTracker = Job()
        adapter.updateGalleryList(imagePaths)
        categoryListAdapter.updateGalleryList(category_list)
        Log.d("img_selected_size",imagePaths.size.toString())
    }


    fun handleEvent(event: PostProdEvent){

        when (event){
            is PostProdEvent.OnStart -> populateCategoryList()
        }

    }

    private fun populateCategoryList(){
        // fetch the category list
         App.categoryItems.forEach {
             category_list.add(it)
        }

    }


    override val coroutineContext: CoroutineContext
        get() = jobTracker + Dispatchers.IO





}
