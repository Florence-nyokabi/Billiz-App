package bills.project.BillzApp.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import bills.project.BillzApp.ViewModel.BillzViewModel
import bills.project.BillzApp.databinding.FragmentSummaryBinding
import bills.project.BillzApp.model.BillsSummary
import bills.project.BillzApp.utils.Utils
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry


class SummaryFragment : Fragment() {

    var binding: FragmentSummaryBinding? = null   // nullable so that the fragment is destoyed,
    val billsViewModel:BillzViewModel by  viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return  binding?.root

    }
    override fun onResume() {
        super.onResume()
        binding?.fabAddBill?.setOnClickListener{
            startActivity(Intent(requireContext(), AddBillActivity::class.java))
        }
        billsViewModel.getMonthlySummary()
        showMonthlySummary()

    }
    fun showMonthlySummary(){
        billsViewModel.summaryLiveData.observe(this){ summary->
            binding?.tvpaid?.text = Utils.formatCurrency(summary.paid)
            binding?.tvAmount?.text = Utils.formatCurrency(summary.upcoming)
            binding?.tvOverdue?.text = Utils.formatCurrency(summary.overdue)
            binding?.tvTotal?.text = Utils.formatCurrency(summary.total)
            showChart(summary)
        }
    }
    fun showChart(summary: BillsSummary){
        val entries = mutableListOf<DataEntry>()
        entries.add(ValueDataEntry("Paid", summary.paid))
        entries.add(ValueDataEntry("Upcoming", summary.upcoming))
        entries.add(ValueDataEntry("Overdue", summary.overdue))

        val pieChart = AnyChart.pie()
        pieChart.data(entries)
        binding?.summaryChart?.setChart(pieChart)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding =  null
    }
}