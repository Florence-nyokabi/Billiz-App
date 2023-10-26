package bills.project.billzapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bills.project.billzapp.Repository.BillsRepository
import bills.project.billzapp.model.Bill
import bills.project.billzapp.model.BillsSummary
import bills.project.billzapp.model.UpcomingBill
import bills.project.billzapp.utils.Constants
import kotlinx.coroutines.launch

class BillzViewModel: ViewModel() {
    val billsRepo = BillsRepository()
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
    fun fetchRemoteBills(){
        viewModelScope.launch {
            billsRepo.fetchRemoteBills()
            billsRepo.fetchRemoteUpcomingBills()
        }
    }
    fun getMonthlySummary(){
        viewModelScope.launch {
            summaryLiveData.postValue(billsRepo.getMonthlySummary().value)
        }
    }
    fun getUpcomingBillsByFrequency(freq:String):LiveData<List<UpcomingBill>>{
        return billsRepo.getUpcomingBillsByFrequency(freq)
    }

}