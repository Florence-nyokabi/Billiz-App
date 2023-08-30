package bills.project.BillzApp.Repository

import bills.project.BillzApp.BillzApp
import bills.project.BillzApp.database.BillzDb
import bills.project.BillzApp.model.Bill
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BillsRepository {
    var db = BillzDb.getDatabase(BillzApp.appContext)
    val billDao = db.billDao()

   suspend fun saveBill(bill: Bill){
        withContext(Dispatchers.IO){
            billDao.insertBill(bill)
        }
   }
}