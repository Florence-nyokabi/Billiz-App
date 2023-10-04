package bills.project.BillzApp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import bills.project.BillzApp.ViewModel.BillzViewModel
import bills.project.BillzApp.databinding.FragmentPaidBillsBinding
import bills.project.BillzApp.model.UpcomingBill


class PaidBillsFragment : Fragment(),OnClickBill {
    var binding: FragmentPaidBillsBinding? = null
    val billsViewModel:BillzViewModel by  viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaidBillsBinding.inflate(layoutInflater,container,false)
        return binding?.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    override fun onResume() {
        super.onResume()
        billsViewModel.getPaidBills().observe(this){paidBills->
            val adapter = UpcomingBillsAdapter(paidBills,this)
            binding?.rvpaid?.layoutManager = LinearLayoutManager(requireContext())
            binding?.rvpaid?.adapter = adapter
        }
    }
    override fun checkPaidBill(upcomingBill: UpcomingBill) {
        upcomingBill.paid = !upcomingBill.paid
        billsViewModel.updateUpcomingBill(upcomingBill)
    }
}