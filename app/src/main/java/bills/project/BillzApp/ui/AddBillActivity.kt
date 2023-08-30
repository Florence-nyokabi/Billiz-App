package bills.project.BillzApp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import bills.project.BillzApp.ViewModel.BillzViewModel
import bills.project.BillzApp.databinding.ActivityAddBillBinding
import bills.project.BillzApp.utils.Constants
import com.google.android.material.navigation.NavigationBarView
import java.util.UUID

class AddBillActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBillBinding
    val billzViewModel : BillzViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddBillBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setUpFreqSpinner()
    }

    fun setUpFreqSpinner(){
        val frequencies = arrayOf(Constants.WEEKLY, Constants.MONTHLY, Constants.ANNUAL)
//        val freqAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item)
//        binding.spFrequency.adapter = freqAdapter
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


    }

//    fun setupFreqSpinner(){
//        val frequencies = arrayOf(Constants.WEEKLY, Constants.MONTHLY, Constants.ANNUAL)
//        val freqAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item)
//        binding.spFrequency.adapter = freqAdapter
//
//        binding.spFrequency.onItemSelectedListener = object :
//            NavigationBarView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id:){
//                when(binding.spFrequency.selectedItem.toString() ) {
//                    Constants.WEEKLY -> {
//
//                    }
//                }
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//        }
//    }

    fun setupDueDateSpinner(){
        val weeklyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf(1,2,3,4,5,6))
        weeklyAdapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item))
        binding.spDueDate.adapter = weeklyAdapter
    }

    val buildId = UUID.randomUUID().toString()
}