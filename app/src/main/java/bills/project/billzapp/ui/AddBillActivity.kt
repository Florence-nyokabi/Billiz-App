package bills.project.billzapp.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import bills.project.billzapp.ViewModel.BillzViewModel
import bills.project.billzapp.databinding.ActivityAddBillBinding
import bills.project.billzapp.model.Bill
import bills.project.billzapp.R.string

import bills.project.billzapp.utils.Constants
import bills.project.billzapp.utils.DateTimeUtils
import java.util.Calendar
import java.util.UUID

class AddBillActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBillBinding
    val billsViewModel: BillzViewModel by viewModels()
    var selectedDate = 0
    var selectedMonth = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBillBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        setupFreqSpinner()
        binding.btnSaveBill.setOnClickListener {
            validateBill()
        }
    }

    fun setupFreqSpinner() {
        val frequencies =
            arrayOf(Constants.WEEKLY, Constants.MONTHLY, Constants.QUARTELY, Constants.ANNUAL)
        val freqAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, frequencies)
        freqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spfrequency.adapter = freqAdapter
        binding.spfrequency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (binding.spfrequency.selectedItem.toString()) {
                    Constants.WEEKLY -> {
                        showSpinner()
                        setUpDueDateSpinner(Array(7) { it + 1 })
                    }

                    Constants.MONTHLY -> {
                        showSpinner()
                        setUpDueDateSpinner(Array(31) { it + 1 })
                    }

                    Constants.QUARTELY -> {
                        showSpinner()
                        setUpDueDateSpinner(Array(31) { it + 1 })
                    }

                    Constants.ANNUAL -> {
                        showDatePicker()
                        setupDpDueDate()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    fun showSpinner() {
        binding.dpDueDateAnnual.hide()
        binding.spduedate.show()
    }

    fun showDatePicker() {
        binding.dpDueDateAnnual.show()
        binding.spduedate.hide()
    }

    fun setUpDueDateSpinner(dates: Array<Int>) {
        val dueDateAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, dates)
        dueDateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spduedate.adapter = dueDateAdapter
    }

    fun setupDpDueDate() {
        val cal = Calendar.getInstance()
        binding.dpDueDateAnnual.init(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ) { _, _, month, date ->
            selectedDate = date
            selectedMonth = month + 1
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun validateBill() {
        val name = binding.etName.text.toString()
        val amount = binding.etAmount.text.toString()
        val frequency = binding.spfrequency.selectedItem.toString()
        val dueDate = if (frequency == Constants.ANNUAL) {
            DateTimeUtils.
            createDateFromDayAndMonth(selectedDate,selectedMonth)
                .substring(5)

        } else {
            binding.spduedate.selectedItem.toString()
        }
        var error = false
        if (name.isBlank()) {
            error = true
            binding.etName.error = "Bill name is required"
        }
        if (amount.isBlank()) {
            error = true
            binding.etName.error = "Bill amount is required"
        }
        if (!error) {
            val prefs = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
            val userId = prefs.getString(Constants.USER_ID, Constants.EMPTY_STRING)
            val newBill = Bill(
                name = name,
                amount = amount.toDouble(),
                frequency = frequency,
                dueDate = dueDate,
                billId = UUID.randomUUID().toString(),
                userId = userId.toString(),
                synched = false
            )
            billsViewModel.saveBill(newBill)
            clearForm()

        }
    }

    fun clearForm() {
        binding.etName.setText(Constants.EMPTY_STRING)
        binding.etAmount.setText(Constants.EMPTY_STRING)
        binding.spfrequency.setSelection(0)
        showSpinner()
        binding.spduedate.setSelection(0)
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

