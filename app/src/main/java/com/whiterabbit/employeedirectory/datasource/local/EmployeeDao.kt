package com.whiterabbit.employeedirectory.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.whiterabbit.employeedirectory.datasource.models.Employee

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee")
    fun loadAllEmployees(): MutableList<Employee>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveEmployees(employees: MutableList<Employee>)

    @Query("SELECT * FROM employee WHERE id = :employeeId")
    fun getEmployee(employeeId: Int) : Employee
}
