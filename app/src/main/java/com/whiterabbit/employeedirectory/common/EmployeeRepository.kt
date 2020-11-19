package com.whiterabbit.employeedirectory.common

import android.os.Looper
import com.whiterabbit.employeedirectory.callbacks.ViewModelCallback
import com.whiterabbit.employeedirectory.datasource.ApiManager
import com.whiterabbit.employeedirectory.datasource.models.Employee
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class EmployeeRepository {

    fun getEmployees(viewModelCallback: ViewModelCallback) {
        val thread = Thread {
            val employees = EmployeeApplication.getInstance().getDatabase().employeeDao()
                    .loadAllEmployees()
            android.os.Handler(Looper.getMainLooper()).post { getEmployeesFromAPI(employees, viewModelCallback) }
        }
        thread.start()
    }

    private fun getEmployeesFromAPI(employees: MutableList<Employee>?, viewModelCallback: ViewModelCallback) {
        if (employees.isNullOrEmpty()) {
            ApiManager.retrofitClient.getEmployees()
                    .enqueue(object : Callback<MutableList<Employee>> {
                        override fun onResponse(
                                call: Call<MutableList<Employee>>,
                                response: Response<MutableList<Employee>>
                        ) {
                            if (response.isSuccessful) {
                                val employeeResponse = response.body()
                                employeeResponse?.let {
                                    thread {
                                        EmployeeApplication.getInstance().getDatabase().employeeDao()
                                                .saveEmployees(it)
                                    }
                                }
                                viewModelCallback.onSuccess(employeeResponse)
                            } else {
                                viewModelCallback.onError()
                            }
                        }

                        override fun onFailure(
                                call: Call<MutableList<Employee>>,
                                t: Throwable
                        ) {
                            viewModelCallback.onFailure("API call failed")
                        }
                    })
        } else {
            viewModelCallback.onSuccess(employees)
        }
    }

    fun getEmployee(employeeId: Int): Employee = EmployeeApplication.getInstance().getDatabase().employeeDao()
            .getEmployee(employeeId)
}