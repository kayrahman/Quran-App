package com.nkr.quran.business.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kaopiz.kprogresshud.KProgressHUD
import com.nkr.quran.R
import com.nkr.quran.business.base.helper.FragmentHelper
import com.nkr.quran.business.base.helper.FunctionalHelper
import com.nkr.quran.business.base.helper.MsgHelper
import com.nkr.quran.business.base.helper.UIHelper
import com.nkr.quran.business.common.BaseViewModel


abstract class BaseFragment<E> : Fragment() {
    val TAG = this::class.java.simpleName

    val msgHelper by lazy {
        MsgHelper(requireActivity())
    }
    val functionHelper by lazy {
        FunctionalHelper(
            requireActivity()
        )
    }
    val uiHelper by lazy {
        UIHelper(requireActivity())
    }
    val fragmentHelper by lazy {
        FragmentHelper(requireActivity())
    }

    var hud: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // getData()
    }

    fun getRootView(inflater: LayoutInflater, container: ViewGroup, layout: Int): ViewGroup {
        val rootView = inflater.inflate(layout, container, false) as ViewGroup

        //bind components to xml
        bindComponents(rootView)

        //setup listener for each components
        setupListener()

        return rootView
    }



    protected open fun setupListener(){}
    protected open fun bindComponents(view: View) {

    }

    //abstract fun <T>getEvent():T


    override fun onDestroyView() {
//        uiHelper.changeStatusBarStatus(true)
       // uiHelper.setStatusBar(App.mResourceProvider?.getColor(R.color.colorStatusBar)!!)
       // uiHelper.setLightStatusBar()
        super.onDestroyView()
    }

    fun showLoadingDialog(title: String, msg: String?) {
        if (hud != null) {
            activity!!.runOnUiThread(Thread(Runnable {
                hud?.setLabel(title)
                if (msg != null) {
                    hud?.setDetailsLabel(msg)
                }
            }))
        } else {
            if (msg != null) {
                hud = KProgressHUD.create(activity!!)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(title)
                        .setDetailsLabel(msg)
                        .setCancellable(false)
                        .setDimAmount(0.5f)
                        .show()
            } else {
                hud = KProgressHUD.create(activity!!)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(title)
                        .setDimAmount(0.5f)
                        .setCancellable(false)
                        .show()
            }
        }
    }

    fun showLoadingDialogWithoutDim(title: String, msg: String?) {
        if (hud != null) {
            activity!!.runOnUiThread(Thread(Runnable {
                hud?.setLabel(title)
                if (msg != null) {
                    hud?.setDetailsLabel(msg)
                }
            }))
        } else {
            if (msg != null) {
                hud = KProgressHUD.create(activity!!)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(title)
                        .setDetailsLabel(msg)
                        .setCancellable(false)
                        .show()
            } else {
                hud = KProgressHUD.create(activity!!)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(title)
                        .setCancellable(false)
                        .show()
            }
        }
    }

    /**
     * Dismiss loading dialog
     *
     * */

    fun dismissLoadingDialog() {
        try {
            hud?.dismiss()
            hud = null
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }

    }


    fun <T : BaseViewModel<Any>> observeLoading(viewModel : T) {
        //show loading observer
        viewModel.showLoading.observe(this, Observer { aBoolean ->
            if (aBoolean!!) {
                showLoadingDialog(getString(R.string.text_loading), null)
            } else {
                dismissLoadingDialog()
            }
        })
    }


    open fun <T : BaseViewModel<E>> observerLoading(viewModel: T) {
        //show loading observer
        viewModel.showLoading.observe(viewLifecycleOwner, Observer { aBoolean ->
            if (aBoolean!!) {
                showLoadingDialog(getString(R.string.text_loading), null)
            } else {
                dismissLoadingDialog()
            }
        })
    }

}