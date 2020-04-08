package com.nkr.fashionita.ui.fragment.settingsFragment

sealed class SettingsEvent {
    
    object goToEditProfileFragment : SettingsEvent()
    object goToPaymentMethodFragment : SettingsEvent()
    object goToEditAddressFragment : SettingsEvent()
    object goToChangePasswordFragment : SettingsEvent()
    object goToNotificationFragment : SettingsEvent()
    object goToSocialMediaFragment : SettingsEvent()
    object goToAboutFragment : SettingsEvent()
    object logout : SettingsEvent()


}