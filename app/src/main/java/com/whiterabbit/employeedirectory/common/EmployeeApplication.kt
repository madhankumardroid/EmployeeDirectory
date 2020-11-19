package com.whiterabbit.employeedirectory.common

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.whiterabbit.employeedirectory.datasource.local.EmployeeDatabase

class EmployeeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = EmployeeDatabase.getDatabase(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    fun getDatabase(): EmployeeDatabase = database

    companion object {
        private lateinit var instance: EmployeeApplication
        private lateinit var database: EmployeeDatabase

        fun getInstance(): EmployeeApplication = instance
    }
}