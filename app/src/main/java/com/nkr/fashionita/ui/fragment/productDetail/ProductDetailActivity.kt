package com.nkr.fashionita.ui.fragment.productDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nkr.fashionita.R

private const val PRODUCT_DETAIL_FRAGMENT = "product_detail_fragment"

class ProductDetailActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)



        val view = supportFragmentManager.findFragmentByTag(PRODUCT_DETAIL_FRAGMENT) as ProductDetailFragment?
            ?: ProductDetailFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.root_activity_product_detail, view,
                PRODUCT_DETAIL_FRAGMENT
            )
            .commitNowAllowingStateLoss()



    }
}
