package bills.project.billzapp.ui
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import bills.project.billzapp.ViewModel.BillzViewModel
import bills.project.billzapp.databinding.FragmentUpcomingBillsBinding
import bills.project.billzapp.model.UpcomingBill
import bills.project.billzapp.utils.Constants


class UpcomingBillsFragment : Fragment(), onClickBill {
    private var binding:FragmentUpcomingBillsBinding? = null
    private val billsViewModel:BillzViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpcomingBillsBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        getUpcomingBills()
    }
    private fun getUpcomingBills(){
        billsViewModel.getUpcomingBillsByFrequency(Constants.WEEKLY)
            .observe(this){weeklyBills->
                val adapter = UpcomingBillsAdapter(weeklyBills,this)
                binding?.rvweekly?.layoutManager=LinearLayoutManager(requireContext())
                binding?.rvweekly?.adapter = adapter
            }

        billsViewModel.getUpcomingBillsByFrequency(Constants.MONTHLY)
            .observe(this){monthlyBills->
                val adapter = UpcomingBillsAdapter(monthlyBills,this)
                binding?.rvmonthly?.layoutManager=LinearLayoutManager(requireContext())
                binding?.rvmonthly?.adapter=adapter
            }
        billsViewModel.getUpcomingBillsByFrequency(Constants.QUARTELY)
            .observe(this){quarterlyBills->
                val adapter = UpcomingBillsAdapter(quarterlyBills,this)
                binding?.rvquartely?.layoutManager=LinearLayoutManager(requireContext())
                binding?.rvquartely?.adapter=adapter
            }

        billsViewModel.getUpcomingBillsByFrequency(Constants.ANNUAL)
            .observe(this){annualBills->
                val adapter = UpcomingBillsAdapter(annualBills,this)
                binding?.rvannual?.layoutManager=LinearLayoutManager(requireContext())
                binding?.rvannual?.adapter=adapter
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onCheckBoxMarked(upcomingBill: UpcomingBill) {
        upcomingBill.paid=!upcomingBill.paid
        upcomingBill.synced=false

        billsViewModel.updateUpcomingBill(upcomingBill)
    }
}