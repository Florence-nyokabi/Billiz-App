package bills.project.BillzApp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bills.project.BillzApp.model.Bill
import bills.project.BillzApp.model.BillsSummary
import bills.project.BillzApp.model.UpcomingBill
import bills.project.BillzApp.utils.Constants
import com.kevine.billzapplication.repository.BillsRepo
import kotlinx.coroutines.launch

class BillzViewModel: ViewModel() {
    val billsRepo = BillsRepo()
    val summaryLiveData = MutableLiveData<BillsSummary>()
    fun saveBill(bill: Bill){
        viewModelScope.launch {
            billsRepo.saveBill(bill)
        }
    }
    fun createRecurringBills(){
        viewModelScope.launch {
            billsRepo.createRecurringMonthlyBills()
            billsRepo.createRecurringWeeklyBills()
            billsRepo.createRecurringAnnualBills()
        }
    }
    fun createUpcomingBills(){
        viewModelScope.launch {
            billsRepo.createRecurringWeeklyBills()
            billsRepo.createRecurringMonthlyBills()
            billsRepo.createRecurringAnnualBills()
        }
    }

    fun getWeeklyUpcoming(): LiveData<List<UpcomingBill>>{
        return billsRepo.getUpcomingBillsByFrequency(Constants.WEEKLY)
    }

    fun getMonthlyUpcoming(): LiveData<List<UpcomingBill>>{
        return billsRepo.getUpcomingBillsByFrequency(Constants.MONTHLY)
    }

    fun getAnnualUpcoming(): LiveData<List<UpcomingBill>>{
        return billsRepo.getUpcomingBillsByFrequency(Constants.ANNUAL)
    }
    fun updateUpcomingBill(upcomingBill: UpcomingBill) {
        viewModelScope.launch {
            billsRepo.updateUpcomingBill(upcomingBill)
        }
    }
    fun getPaidBills():LiveData<List<UpcomingBill>>{
        return billsRepo.getPaidBills()
    }
//    fun fetchRemoteBills(){
//        viewModelScope.launch {
//            billsRepo.fetchRemoteBills()
//        }
//    }
    fun getMonthlySummary(){
        viewModelScope.launch {
            summaryLiveData.postValue(billsRepo.getMonthlySummary().value)
        }
    }

}