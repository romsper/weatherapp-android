package com.romsper.weatherapp.screen

import android.annotation.SuppressLint
import com.romsper.weatherapp.network.ApiFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivityPresenter(private val view: MainActivityView) : MainActivityView {

    @SuppressLint("CheckResult")
    fun loadData(city: String) {
        ApiFactory.retrofit().gerForecast(city)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                view.setData(result)
            }, { error -> error.printStackTrace() })
    }
}