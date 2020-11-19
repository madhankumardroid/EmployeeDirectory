package com.whiterabbit.employeedirectory.employee_list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.whiterabbit.employeedirectory.employee_detail.EmployeeDetailActivity
import com.whiterabbit.employeedirectory.R
import com.whiterabbit.employeedirectory.callbacks.ItemClickCallback
import com.whiterabbit.employeedirectory.common.Constants
import com.whiterabbit.employeedirectory.databinding.ActivityEmployeeListBinding
import com.whiterabbit.employeedirectory.datasource.models.Employee


class EmployeeListActivity : AppCompatActivity(), ItemClickCallback {
    private lateinit var activityEmployeeListBinding: ActivityEmployeeListBinding

    private lateinit var employeeListViewModel: EmployeeListViewModel

    private var employeeListAdapter: EmployeeListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEmployeeListBinding = ActivityEmployeeListBinding.inflate(layoutInflater)
        setContentView(activityEmployeeListBinding.root)
        setTitle(R.string.employees)

        employeeListViewModel = ViewModelProvider(this).get(EmployeeListViewModel::class.java)

        observeViewModel()

        employeeListViewModel.getEmployees()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.employee_search_menu, menu)
        val searchItem: MenuItem = menu!!.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                employeeListAdapter?.filter?.filter(newText)
                return false
            }
        })
        return true
    }

    private fun observeViewModel() {
        with(employeeListViewModel) {
            employees.observe(this@EmployeeListActivity, {
                setAdapter(it)
            })

            showProgress.observe(this@EmployeeListActivity, {
                activityEmployeeListBinding.progressBar.visibility =
                        if (it) View.VISIBLE else View.GONE
            })

            errorMessage.observe(this@EmployeeListActivity, {
                Toast.makeText(this@EmployeeListActivity, it, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun setAdapter(employees: MutableList<Employee>) {
        if (employeeListAdapter == null) {
            employeeListAdapter = EmployeeListAdapter(employees, this)
            activityEmployeeListBinding.rvEmployees.layoutManager = LinearLayoutManager(this)
            activityEmployeeListBinding.rvEmployees.adapter = employeeListAdapter
        } else {
            employeeListAdapter?.updateEmployees(employees)
        }
    }

    override fun onItemClick(employee: Employee) {
        val employeeDetailIntent = Intent(this, EmployeeDetailActivity::class.java)
        employeeDetailIntent.putExtra(Constants.BUNDLE_KEY_EMPLOYEE_ID, employee.id)
        startActivity(employeeDetailIntent)
    }
}