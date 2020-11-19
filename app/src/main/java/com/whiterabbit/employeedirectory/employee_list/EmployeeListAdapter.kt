package com.whiterabbit.employeedirectory.employee_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.whiterabbit.employeedirectory.R
import com.whiterabbit.employeedirectory.callbacks.ItemClickCallback
import com.whiterabbit.employeedirectory.databinding.EmployeeItemBinding
import com.whiterabbit.employeedirectory.datasource.models.Employee

class EmployeeListAdapter(
    private var employees: MutableList<Employee>,
    private val itemClickCallback: ItemClickCallback
) :
    RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder>(), Filterable {
    private lateinit var context: Context

    private val allEmployees: MutableList<Employee> = employees.toMutableList()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val itemBinding =
            EmployeeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(employees[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return employees.size
    }

    override fun getFilter(): Filter {
        return employeeFilter
    }

    fun updateEmployees(employees: MutableList<Employee>) {
        this.employees = employees
        notifyDataSetChanged()
    }

    inner class EmployeeViewHolder(private var employeeItemBinding: EmployeeItemBinding) :
        RecyclerView.ViewHolder(employeeItemBinding.root) {
        fun bind(employee: Employee) {
            with(employeeItemBinding) {
                Glide.with(context)
                    .load(employee.profileImage)
                    .apply(
                        RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).circleCrop()
                    )
                    .circleCrop().fallback(R.drawable.ic_fallback_avatar)
                    .placeholder(R.drawable.ic_fallback_avatar)
                    .into(ivAvatar)
                tvEmployeeName.text = employee.employeeName
                tvCompanyName.text =
                    employee.company?.name?.let { context.getString(R.string.working_at, it) }

                root.setOnClickListener { itemClickCallback.onItemClick(employee) }
            }
        }
    }

    private val employeeFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<Employee> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(allEmployees)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in allEmployees) {
                    val employeeName = item.employeeName?.toLowerCase()
                    val employeeEmail = item.email?.toLowerCase()
                    if ((employeeName != null && employeeName.contains(filterPattern)) || (employeeEmail != null && employeeEmail.contains(
                            filterPattern
                        ))
                    ) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            employees.clear()
            employees.addAll(results.values as Collection<Employee>)
            notifyDataSetChanged()
        }
    }
}