package com.nkr.fashionita.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kaopiz.kprogresshud.KProgressHUD
import com.nkr.fashionita.R
import com.nkr.fashionita.base.helper.FragmentHelper
import com.nkr.fashionita.base.helper.FunctionalHelper
import com.nkr.fashionita.base.helper.MsgHelper
import com.nkr.fashionita.base.helper.UIHelper
import com.nkr.fashionita.common.BaseViewModel
import com.nkr.fashionita.model.App


abstract class BaseFragment : Fragment() {
    val TAG = this::class.java.simpleName

    val msgHelper by lazy {
        MsgHelper(requireActivity())
    }
    val functionHelper by lazy {
         FunctionalHelper(requireActivity())
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


    override fun onDestroyView() {
//        uiHelper.changeStatusBarStatus(true)
       // uiHelper.setStatusBar(App.mResourceProvider?.getColor(R.color.colorStatusBar)!!)
       // uiHelper.setLightStatusBar()
        super.onDestroyView()
    }

    /**
     * show loading dialog
     *
     * @param title
     * @param msg

     **/



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

   /***
     * show loading dialog without Dim
     *
     * @param title
     * @param msg

     */


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



    fun <T : BaseViewModel<T>> observerLoading(viewModel: T) {
        //show loading observer
        viewModel.showLoading.observe(this, Observer { aBoolean ->
            if (aBoolean!!) {
                showLoadingDialog(getString(R.string.text_loading), null)
            } else {
                dismissLoadingDialog()
            }
        })
    }

}