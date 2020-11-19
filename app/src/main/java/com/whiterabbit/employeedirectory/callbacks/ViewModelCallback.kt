package com.whiterabbit.employeedirectory.callbacks

interface ViewModelCallback {
    fun <T> onSuccess(data : T)

    fun onError()

    fun onFailure(failureMessage: String?)
}