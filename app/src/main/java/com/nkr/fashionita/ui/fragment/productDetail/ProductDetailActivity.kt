package com.nkr.fashionita.ui.fragment.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseActivity

private const val PRODUCT_DETAIL_FRAGMENT = "product_detail_fragment"

class ProductDetailActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)



    /*    val view = supportFragmentManager.findFragmentByTag(PRODUCT_DETAIL_FRAGMENT) as ProductDetailFragment?
            ?: ProductDetailFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.root_activity_product_detail, view,
                PRODUCT_DETAIL_FRAGMENT
            )
            .commitNowAllowingStateLoss()
*/


        val product_detail_fm =  ProductDetailFragment.newInstance()
        product_detail_fm.fragmentCallBack = object : ProductDetailFragment.FragmentCallBack {
            override fun goTo(fragment: Fragment) {
                    fragmentHelper.loadFragment(supportFragmentManager,fragment,"parent",R.id.root_activity_product_detail)
            }

            override fun goBack() {
              fragmentHelper.popFragment(supportFragmentManager)
            }


        }
        fragmentHelper.initFragment(supportFragmentManager,product_detail_fm,R.id.root_activity_product_detail)



    }

    override fun initViewModel() {


    }

    override fun setupListener() {

    }
}
