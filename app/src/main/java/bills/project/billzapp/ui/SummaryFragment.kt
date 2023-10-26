package bills.project.billzapp.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import bills.project.billzapp.ViewModel.BillzViewModel
import bills.project.billzapp.databinding.FragmentSummaryBinding
import bills.project.billzapp.model.BillsSummary
import bills.project.billzapp.utils.Utils
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.graphics.vector.SolidFill


class SummaryFragment : Fragment(),  LifecycleObserver {
    var binding: FragmentSummaryBinding?=null
    val billsViewModel:BillzViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSummaryBinding.inflate(layoutInflater,container,false)
        return binding?.root
    }
    override fun onResume() {
        super.onResume()
        binding?.fabAddBill?.setOnClickListener {
            startActivity(Intent(requireContext(),AddBillActivity::class.java))
        }
        billsViewModel.getMonthlySummary()
        showMonthlySummary()
    }
    fun showMonthlySummary(){
        billsViewModel.summaryLiveData.observe(this){summary->
            binding?.tvPaidAmt?.text=Utils.formatCurrency(summary.paid)
            binding?.tvOverdueAmt?.text=Utils.formatCurrency(summary.overdue)
            binding?.tvPendingAmt?.text=Utils.formatCurrency(summary.upcoming)
            binding?.tvTotalAmt?.text=Utils.formatCurrency(summary.total)
            ShowChart(summary)
        }
    }
    fun ShowChart(summary:BillsSummary){
        val entries = mutableListOf<DataEntry>()
        entries.add(ValueDataEntry("Paid",summary.paid))
        entries.add(ValueDataEntry("Upcoming",summary.upcoming))
        entries.add(ValueDataEntry("Overdue",summary.overdue))

        val pieChart = AnyChart.pie()
        pieChart.data(entries)
        pieChart.innerRadius(75)
        pieChart.palette().itemAt(0, SolidFill("#07C50F",100))
        pieChart.palette().itemAt(1, SolidFill("#009688",100))
        pieChart.palette().itemAt(2, SolidFill("#E91E63",100))
        binding?.summaryChart?.setChart(pieChart)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}