package com.romsper.weatherapp.screen.rxjava

import com.romsper.weatherapp.model.ForecastModel

interface MainActivityView {

    fun setData(model: ForecastModel){
        return setData(model)
    }
}