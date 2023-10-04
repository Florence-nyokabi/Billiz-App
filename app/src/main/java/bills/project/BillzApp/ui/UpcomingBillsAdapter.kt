package bills.project.BillzApp.ui
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import bills.project.BillzApp.databinding.UpcomingBillsListItemBinding
import bills.project.BillzApp.model.UpcomingBill
import bills.project.BillzApp.utils.DateTimeUtils
class UpcomingBillsAdapter(var upcomingBills:List<UpcomingBill>, val onClickBill: OnClickBill):
    Adapter<UpcomingBillsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingBillsViewHolder {
        val binding = UpcomingBillsListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UpcomingBillsViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return upcomingBills.size
    }
    override fun onBindViewHolder(holder: UpcomingBillsViewHolder, position: Int) {
        val upcomingBill= upcomingBills.get(position)
        holder.binding.apply {
            cbUpcoming.isChecked=upcomingBill.paid
            cbUpcoming.text=upcomingBill.name
            tvAmount.text = upcomingBill.amount.toString()
            tvAmount.text = DateTimeUtils.formatCurrency(upcomingBill.amount)
            tvDuedate.text=DateTimeUtils.formatDateReadable(upcomingBill.dueDate)
        }
        holder.binding.cbUpcoming.setOnClickListener {
            onClickBill.checkPaidBill(upcomingBill)
        }
    }
}

class UpcomingBillsViewHolder(var binding: UpcomingBillsListItemBinding) :ViewHolder(binding.root)
interface  OnClickBill{
    fun checkPaidBill(upcomingBill: UpcomingBill)
}