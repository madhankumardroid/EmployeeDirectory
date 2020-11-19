package com.whiterabbit.employeedirectory.employee_detail

import android.app.Application
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.whiterabbit.employeedirectory.common.EmployeeRepository
import com.whiterabbit.employeedirectory.datasource.models.Employee
import kotlin.concurrent.thread

class EmployeeDetailViewModel(application: Application) : AndroidViewModel(application) {
    val showProgress by lazy { MutableLiveData<Boolean>() }
    private var repository: EmployeeRepository? = null
    var employee = MutableLiveData<Employee>()

    init {
        repository = EmployeeRepository()
    }

    fun getEmployee(employeeId: Int?) {
        showProgress.value = true
        thread {
            val emp: Employee? = employeeId?.let { repository?.getEmployee(it) }
            android.os.Handler(Looper.getMainLooper()).post {
                employee.value = emp
                showProgress.value = false
            }
        }.start()
    }
}
