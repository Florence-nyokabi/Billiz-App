package bills.project.BillzApp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bills.project.BillzApp.Repository.BillsRepository
import bills.project.BillzApp.model.Bill
import kotlinx.coroutines.launch

class BillzViewModel: ViewModel() {
    val billsRepo = BillsRepository()

    fun saveBill(bill: Bill){
        viewModelScope.launch {
            billsRepo.saveBill(bill)
        }
    }
}