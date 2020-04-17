package com.romsper.weatherapp.viewModel

import com.romsper.weatherapp.network.BaseViewModel

class AuthorizationViewModel : BaseViewModel() {

    fun login(login: String, pass: String) = (login == "admin") && (pass == "admin")
}