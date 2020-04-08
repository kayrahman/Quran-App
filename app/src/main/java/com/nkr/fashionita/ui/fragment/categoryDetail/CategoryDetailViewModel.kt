package com.nkr.fashionita.ui.fragment.categoryDetail

import com.nkr.fashionita.common.BaseViewModel
import com.nkr.fashionita.common.GET_NOTES_ERROR
import com.nkr.fashionita.common.Result
import com.nkr.fashionita.repository.IProductRepository
import com.nkr.fashionita.ui.adapter.categoryDetail.CategoryItemsGridAdapter
import com.nkr.fashionita.ui.adapter.categoryDetail.SubCategoryAdapter
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CategoryDetailViewModel(val productRepo: IProductRepository,
                              uiContext: CoroutineContext
) : BaseViewModel<CategoryListEvent>(uiContext){

    var categoryItemGridAdapter = CategoryItemsGridAdapter()
    var subCategoryHorizontalAdapter = SubCategoryAdapter()


    override fun handleEvent(event: CategoryListEvent) {
        when (event){
            is CategoryListEvent.OnStart ->
            {
                getCategoryDetailItems(event.category_name)
                getSubCategories(event.category_name)
            }
        }

    }

    private fun getSubCategories(category_name: String) =launch {

       when(val subCategoryResult = productRepo.getSubCategories(category_name)){
            is Result.Value ->  subCategoryHorizontalAdapter.updateSubCategoryItem(subCategoryResult.value)
            is Result.Error -> errorState.value = "Category Error"

        }


    }

    private fun getCategoryDetailItems(category_name:String) = launch{
        val categoryResult = productRepo.getProductItemsByCategory(category_name.toLowerCase())

        when (categoryResult){

            is Result.Value -> {
                categoryItemGridAdapter.updateCategoryItem(categoryResult.value)
            }

            is Result.Error -> {
                errorState.value = GET_NOTES_ERROR
            }
        }

    }


}
