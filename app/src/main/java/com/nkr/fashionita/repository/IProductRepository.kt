package com.nkr.fashionita.repository

import com.nkr.fashionita.common.Result
import com.nkr.fashionita.model.*


interface IProductRepository {


    suspend fun getAllProducts(wishKeys:List<String>): Result<Exception, List<Product>>
    suspend fun getProductById(noteId: String): Result<Exception, Product>
    suspend fun getProductItemBySearch(queryString : String): Result<Exception, List<Product>>
    suspend fun getCategories(): Result<Exception, List<CategoryItem>>
    suspend fun getSubCategories(category_uid: String): Result<Exception, List<SubCategoryItem>>
    suspend fun getProductItemsByCategory(category_name : String): Result<Exception, List<Product>>
    suspend fun getBannerItems(): Result<Exception, List<BannerItem>>




    suspend fun postProductInfo(prod : Product):Result<Exception,Unit>
    suspend fun updateProductImages(prod : Product):Result<Exception,Unit>





}