package bills.project.BillzApp.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bills.project.BillzApp.databinding.FragmentSummaryBinding


class SummaryFragment : Fragment() {

    var binding: FragmentSummaryBinding? = null   // nullable so that the fragment is destoyed,

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding =  null
    }

}