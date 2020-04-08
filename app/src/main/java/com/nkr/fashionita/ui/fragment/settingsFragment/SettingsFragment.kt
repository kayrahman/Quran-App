package com.nkr.fashionita.ui.fragment.settingsFragment

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI

import com.nkr.fashionita.R
import com.nkr.fashionita.databinding.SettingsFragmentBinding
import com.nkr.fashionita.ui.fragment.updateProfile.UpdateProfileFragment
import com.nkr.fashionita.ui.login.LoginActivity

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: SettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.settings_fragment, container, false)

        //initViewModel()

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)


        setupListener()


    }

    private fun setupListener() {

        binding.ivBack.setOnClickListener {
            settingfragmentCallBack.goBack()
        }

        binding.personalSettings.llEditProfile.setOnClickListener {
            val editProfileFragment = UpdateProfileFragment.newInstance()
             editProfileFragment.updateProfilefragmentCallBack = object : UpdateProfileFragment.UpdateProfileFragmentCallBack{
                override fun goTo(fragment: Fragment) {

                }

                override fun goBack() {
                    settingfragmentCallBack.goBack()

                }


            }
            settingfragmentCallBack.goTo(editProfileFragment)
        }


        binding.privateSettings.llLogout.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this@SettingsFragment.context!!)
                .addOnCompleteListener {
                    Intent(
                        activity,
                        LoginActivity::class.java
                    )
                }
        }


    }


    /**
     * listener related
     */
    lateinit var settingfragmentCallBack: SettingsFragmentCallBack

    interface SettingsFragmentCallBack {
        fun goTo(fragment: Fragment)
        fun goBack()


    }


}
