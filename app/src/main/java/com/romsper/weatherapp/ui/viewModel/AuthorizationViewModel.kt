package com.romsper.weatherapp.ui.viewModel

import com.romsper.weatherapp.data.api.BaseViewModel

class AuthorizationViewModel : BaseViewModel() {

    fun login(login: String, pass: String) = (login == "admin") && (pass == "admin")
}