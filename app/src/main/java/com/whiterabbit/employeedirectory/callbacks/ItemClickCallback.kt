package com.whiterabbit.employeedirectory.callbacks

import com.whiterabbit.employeedirectory.datasource.models.Employee

interface ItemClickCallback {
    fun onItemClick(employee: Employee)
}