package com.whiterabbit.employeedirectory.employee_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.whiterabbit.employeedirectory.common.EmployeeRepository
import com.whiterabbit.employeedirectory.R
import com.whiterabbit.employeedirectory.callbacks.ViewModelCallback
import com.whiterabbit.employeedirectory.datasource.models.Employee

open class EmployeeListViewModel(application: Application) : AndroidViewModel(application) {
    var employees = MutableLiveData<MutableList<Employee>>()
    private var repository: EmployeeRepository? = null
    val showProgress by lazy { MutableLiveData<Boolean>() }
    val errorMessage by lazy { MutableLiveData<Int>() }

    init {
        repository = EmployeeRepository()
        showProgress.value = false
    }

    fun getEmployees() {
        showProgress.value = true
        repository?.getEmployees(object : ViewModelCallback {
            override fun <T> onSuccess(data: T) {
                employees.value = data as MutableList<Employee>
                showProgress.value = false
            }

            override fun onError() {
                showProgress.value = false
                errorMessage.value = R.string.api_error_occurred
            }

            override fun onFailure(failureMessage: String?) {
                showProgress.value = false
                errorMessage.value = R.string.api_failure_occurred
            }
        })
    }
}