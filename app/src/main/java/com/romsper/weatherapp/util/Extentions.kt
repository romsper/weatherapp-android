package com.romsper.weatherapp.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

//---Hide keyboard---
fun Activity.hideKeyboard() {
    currentFocus?.windowToken?.let {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?)
            ?.hideSoftInputFromWindow(it, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

//---Show keyboard---
fun Activity.showKeyboard(view: View) {
    view.requestFocus()
    currentFocus?.windowToken?.let {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?)
            ?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}