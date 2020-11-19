package com.whiterabbit.employeedirectory.employee_detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.whiterabbit.employeedirectory.R
import com.whiterabbit.employeedirectory.common.Constants
import com.whiterabbit.employeedirectory.databinding.ActivityEmployeeDetailBinding
import com.whiterabbit.employeedirectory.datasource.models.Employee

class EmployeeDetailActivity : AppCompatActivity() {

    private lateinit var activityEmployeeDetailBinding: ActivityEmployeeDetailBinding

    private lateinit var employeeDetailViewModel: EmployeeDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEmployeeDetailBinding = ActivityEmployeeDetailBinding.inflate(layoutInflater)
        setContentView(activityEmployeeDetailBinding.root)
        setTitle(R.string.profile)

        employeeDetailViewModel = ViewModelProvider(this).get(EmployeeDetailViewModel::class.java)

        val employeeId = intent.getIntExtra(Constants.BUNDLE_KEY_EMPLOYEE_ID, 0)
        employeeDetailViewModel.getEmployee(employeeId)

        observeViewModel()
    }

    private fun observeViewModel() {
        with(employeeDetailViewModel) {
            employee.observe(this@EmployeeDetailActivity, {
                updateUI(it)
            })
            showProgress.observe(this@EmployeeDetailActivity, {
                activityEmployeeDetailBinding.progressbarDetail.visibility =
                    if (it) View.VISIBLE else View.GONE
            })

        }
    }

    private fun updateUI(employee: Employee?) {
        if (employee != null) {
            with(employee) {
                with(activityEmployeeDetailBinding) {
                    Glide.with(this@EmployeeDetailActivity)
                        .load(profileImage)
                        .apply(
                            RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .circleCrop()
                        )
                        .circleCrop().fallback(R.drawable.ic_fallback_avatar)
                        .placeholder(R.drawable.ic_fallback_avatar)
                        .into(ivEmployeeAvatar)
                    tvName.setValue(employeeName)
                    tvUserName.setValue(username)
                    tvEmailAddress.setValue(email)
                    tvAddress.setValue("""${address?.street}, ${address?.suite}, ${address?.city} ,${address?.zipcode}""")
                    tvPhone.setValue(phone)
                    tvWebsite.setValue(website)
                    tvCompanyName.setValue(company?.let { """${company?.name ?: ""}(${company?.bs ?: ""})""" })
                }
            }
        } else {
            Toast.makeText(
                this@EmployeeDetailActivity,
                R.string.employee_detail_not_found,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}