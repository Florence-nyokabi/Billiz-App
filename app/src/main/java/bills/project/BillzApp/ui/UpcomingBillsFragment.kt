package bills.project.BillzApp.ui
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import bills.project.BillzApp.ViewModel.BillzViewModel
import bills.project.BillzApp.databinding.FragmentUpcomingBillsBinding
import bills.project.BillzApp.model.UpcomingBill


class UpcomingBillsFragment : Fragment(),OnClickBill {
    var binding:FragmentUpcomingBillsBinding? = null
    val billsViewHolder: BillzViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpcomingBillsBinding.inflate(layoutInflater,container,false)
        return binding?.root
    }
    override fun onResume() {
        super.onResume()
        billsViewHolder.getWeeklyUpcoming().observe(this){upcomingWeekly->
            val adapter = UpcomingBillsAdapter(upcomingWeekly,this)
            binding?.rvWeekly?.layoutManager = LinearLayoutManager(requireContext())
            binding?.rvWeekly?.adapter= adapter
        }
        billsViewHolder.getMonthlyUpcoming().observe(this){upcomingMonthly->
            val adapter = UpcomingBillsAdapter(upcomingMonthly,this)
//            binding?.rvMonthly?.layoutManager = LinearLayoutManager(requireContext())
//            binding?.rvMonthly?.adapter= adapter
        }
        billsViewHolder.getMonthlyUpcoming().observe(this){upcomingMonthly->
            val adapter = UpcomingBillsAdapter(upcomingMonthly,this)
            binding?.rvAnnually?.layoutManager = LinearLayoutManager(requireContext())
            binding?.rvAnnually?.adapter= adapter
        }
        billsViewHolder.getAnnualUpcoming().observe(this){upcomingAnnual->
            val adapter = UpcomingBillsAdapter(upcomingAnnual,this)
            binding?.rvAnnually?.layoutManager = LinearLayoutManager(requireContext())
            binding?.rvAnnually?.adapter= adapter

        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
    override fun checkPaidBill(upcomingBill: UpcomingBill) {
        upcomingBill.paid =!upcomingBill.paid
        upcomingBill.synced= false
        billsViewHolder.updateUpcomingBill(upcomingBill)
    }
}