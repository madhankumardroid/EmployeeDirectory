package com.whiterabbit.employeedirectory.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.whiterabbit.employeedirectory.datasource.models.Employee

@Database(entities = [Employee::class], version = 1, exportSchema = false)
public abstract class EmployeeDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    companion object {
        @Volatile
        private var INSTANCE: EmployeeDatabase? = null

        fun getDatabase(context: Context): EmployeeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmployeeDatabase::class.java,
                        "employee"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
