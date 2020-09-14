package com.nkr.quran.business.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kaopiz.kprogresshud.KProgressHUD
import com.nkr.quran.business.base.helper.FragmentHelper
import com.nkr.quran.business.base.helper.MsgHelper
import com.nkr.quran.business.base.helper.UIHelper


abstract class BaseActivity : AppCompatActivity() {
    val TAG = this::class.java.simpleName

    val msgHelper by lazy {
        MsgHelper(this)
    }

    /*
    val functionHelper by lazy {
        FunctionalHelper(this)
    }
    */

    val uiHelper by lazy {
        UIHelper(this)
    }

    val fragmentHelper by lazy {
        FragmentHelper(this)
    }

    var hud: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }


/*
    *//**
     * override this for calligraphy default font
     *//*
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


    */

   /*

    fun <T : BaseViewModel> observerLoading(viewModel: T) {
        //show loading observer
        viewModel.showLoading.observe(this, Observer { aBoolean ->
            if (aBoolean!!) {
                showLoadingDialog(getString(R.string.text_loading), null)
            } else {
                dismissLoadingDialog()
            }
        })
    }

    */


    /**
     * show loading dialog
     *
     * @param title
     * @param msg
     */
    fun showLoadingDialog(title: String, msg: String?) {
        if (hud != null) {
            runOnUiThread(Thread(Runnable {
                hud?.setLabel(title)
                if (msg != null) {
                    hud?.setDetailsLabel(msg)
                }
            }))
        } else {
            if (msg != null) {
                hud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(title)
                        .setDetailsLabel(msg)
                        .setCancellable(false)
                        .setDimAmount(0.5f)
                        .show()
            } else {
                hud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(title)
                        .setDimAmount(0.5f)
                        .setCancellable(false)
                        .show()
            }
        }
    }

    /**
     * show loading dialog without Dim
     *
     * @param title
     * @param msg
     */
    fun showLoadingDialogWithoutDim(title: String, msg: String?) {
        if (hud != null) {
            runOnUiThread(Thread(Runnable {
                hud?.setLabel(title)
                if (msg != null) {
                    hud?.setDetailsLabel(msg)
                }
            }))
        } else {
            if (msg != null) {
                hud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(title)
                        .setDetailsLabel(msg)
                        .setCancellable(false)
                        .show()
            } else {
                hud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(title)
                        .setCancellable(false)
                        .show()
            }
        }
    }

    /**
     * Dismiss loading dialog
     */
    fun dismissLoadingDialog() {
        try {
            hud?.dismiss()
            hud = null
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }

    }

    /**
     * function for back button click
     */
    open fun backBtnClick(view: View) {
        onBackPressed()
    }

    /**
     * get data from previous activity/fragment
     */
    open fun getData() {

    }

    /**
     * for init view model
     */
    abstract fun initViewModel()

    /**
     * for setup on click and other listener
     */
    abstract fun setupListener()
}