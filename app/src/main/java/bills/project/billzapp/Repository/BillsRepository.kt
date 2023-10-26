package bills.project.billzapp.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bills.project.billzapp.API.ApiClient
import bills.project.billzapp.API.ApiInterface
import bills.project.billzapp.BillzApp
import bills.project.billzapp.database.BillzDb
import bills.project.billzapp.model.Bill
import bills.project.billzapp.model.BillsSummary
import bills.project.billzapp.model.UpcomingBill
import bills.project.billzapp.utils.Constants
import bills.project.billzapp.utils.DateTimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID


// These are the things in the Dao
class BillsRepository {
    var db = BillzDb.getDatabase(BillzApp.appContext)
    val billsDao = db.billDao()
    val upcomingBillsDao = db.upcomingBillsDao()
    val apiClient = ApiClient.buildApiClient(ApiInterface::class.java)



    suspend fun saveBill(bill: Bill){
        withContext(Dispatchers.IO){
            billsDao.insertBill(bill)
        }
    }

    suspend fun insertUpcomingBills(upcomingBill: UpcomingBill){
        withContext(Dispatchers.IO){
            upcomingBillsDao.insertUpcomingBill(upcomingBill)
        }
    }

    suspend fun createRecurringMonthlyBills(){
        withContext(Dispatchers.IO){
            val montlyBills = billsDao.getRecurringBills(Constants.MONTHLY)
            val startDate = DateTimeUtils.getFirstDayOfMonth()
            val endDate = DateTimeUtils.getLastDayOfMonth()

            montlyBills.forEach{bill ->
                val existing = upcomingBillsDao.queryExistingBill(bill.billId, startDate, endDate)
                if (existing.isEmpty()){
                    val newUpcomingBill = UpcomingBill(
                        upcomingBillId = UUID.randomUUID().toString(),
                        billId = bill.billId,
                        name = bill.name,
                        amount = bill.amount,
                        frequency = bill.frequency,
                        dueDate = DateTimeUtils.createDateFromDay(bill.dueDate),
                        userId = bill.userId,
                        paid = false,
                        synced = false
                    )
                    upcomingBillsDao.insertUpcomingBill(newUpcomingBill)
                }
            }
        }
    }

    suspend fun createRecurringWeeklyBills(){

        withContext(Dispatchers.IO){
            val weeklyBills= billsDao.getRecurringBills(Constants.WEEKLY)
            val startDate = DateTimeUtils.getFirstDayOfWeek()
            val endDate = DateTimeUtils.getLastDayOfWeek()
            weeklyBills.forEach { bill ->
                val existing = upcomingBillsDao.queryExistingBill(bill.billId, startDate, endDate)
                if (existing.isEmpty()) {
                    val newUpcomingWeeklyBill = UpcomingBill(
                        upcomingBillId = UUID.randomUUID().toString(),
                        billId = bill.billId,
                        name = bill.name,
                        amount = bill.amount,
                        frequency = bill.frequency,
                        dueDate = DateTimeUtils.getDateOfWeekDay(bill.dueDate),
                        userId = bill.userId,
                        paid = false,
                        synced = false
                    )
                    upcomingBillsDao.insertUpcomingBill(newUpcomingWeeklyBill)
                }
            }
        }
    }

    suspend fun createRecurringQuarterlyBills(){
        withContext(Dispatchers.IO){
            val quartelyBills = billsDao.getRecurringBills(Constants.QUARTELY)
            val currentYear = DateTimeUtils.getCurrentYear()

            for (quarter in 1..4){
                val startDate = DateTimeUtils.getQuarterStartDate(currentYear, quarter)
                val endDate = DateTimeUtils.getQuarterEndDate(currentYear, quarter)

                quartelyBills.forEach{ bill ->
                    val dueDateAsInt = bill.dueDate.toInt()

                    if(dueDateAsInt in 1..31 && quarter * 3 in 1..12){
                        val exisitingBill = upcomingBillsDao.queryExistingBill(bill.billId, startDate, endDate)

                        if(exisitingBill.isEmpty()){
                            val newQuartelyBill = UpcomingBill(
                                upcomingBillId = UUID.randomUUID().toString(),
                                billId = bill.billId,
                                name = bill.name,
                                amount = bill.amount,
                                frequency = bill.frequency,
                                dueDate = DateTimeUtils.createDateFromDayAndMonth(dueDateAsInt, quarter * 3),
                                userId = bill.userId,
                                paid = false,
                                synced = false
                            )
                            upcomingBillsDao.insertUpcomingBill(newQuartelyBill)
                        }
                    }else{
                        Log.e("Bill Repository", "Invalid day-of-month or month value for bill: ${bill.billId}")
                    }
                }
            }
        }
    }

    suspend fun createRecurringAnnualBills(){
        withContext(Dispatchers.IO){
            val annualBills= billsDao.getRecurringBills(Constants.ANNUAL)
            val currentYear=DateTimeUtils.getCurrentYear()
            val startDate="$currentYear-01-01"
            val endDate="$currentYear-12-31"
            annualBills.forEach{ bill ->
                val existingBill=upcomingBillsDao.queryExistingBill(bill.billId,startDate,endDate)
                if (existingBill.isEmpty()){
                    val newAnnualBill = UpcomingBill(
                        upcomingBillId = UUID.randomUUID().toString(),
                        billId = bill.billId,
                        name = bill.name,
                        amount = bill.amount,
                        frequency = bill.frequency,
                        dueDate = "$currentYear-${bill.dueDate}",
                        userId = bill.userId,
                        paid = false,
                        synced = false
                    )
                    upcomingBillsDao.insertUpcomingBill(newAnnualBill)
                }
            }
        }
    }

    fun getUpcomingBillsByFrequency(frequency:String):LiveData<List<UpcomingBill>>{
        return upcomingBillsDao.getUpcomingBillsByFrequency(frequency, false)
    }

    suspend fun updateUpcomingBill(upcomingBill: UpcomingBill){
        withContext(Dispatchers.IO){
            upcomingBillsDao.updateUpcomingBill(upcomingBill)
        }
    }

    fun getPaidBills():LiveData<List<UpcomingBill>>{
        return upcomingBillsDao.getPaidBills()
    }

    fun getAuthToken():String{
        val prefs= BillzApp.appContext
            .getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        var token = prefs.getString(Constants.ACCESS_TOKEN,Constants.EMPTY_STRING)

        token = "Bearer $token"
        return token
    }
    suspend fun syncBills() {
        withContext(Dispatchers.IO){
            val accessToken = getAuthToken()
            val unsyncedBills=billsDao.getUnsyncedBills()
            unsyncedBills.forEach {bill->
                val response= apiClient.postBill(accessToken,bill)
                if (response.isSuccessful){
                    bill.synched=true
                    billsDao.insertBill(bill)
                }
            }
        }
    }

    suspend fun synchedUpcomingBills(){
        withContext(Dispatchers.IO){
            var token = getAuthToken()
            upcomingBillsDao.getUnsyncedUpcomingBills().forEach { upcomingBill ->
                val response = apiClient.postUpcomingBill(token,upcomingBill)

                if (response.isSuccessful){
                    upcomingBill.synced = true
                    upcomingBillsDao.updateUpcomingBill(upcomingBill)
                }
            }
        }
    }
    suspend fun fetchRemoteBills(){
        withContext(Dispatchers.IO){
//            val token = getAuthToken()
            val response = apiClient.fetchRemoteBills(getAuthToken())
            if (response.isSuccessful){
                response.body()?.forEach { bill ->
                    bill.synched=true
                    billsDao.insertBill(bill)
                }
            }
        }
    }
    suspend fun fetchRemoteUpcomingBills(){
        withContext(Dispatchers.IO){
            val response = apiClient.fetchRemoteUpcomingBills(getAuthToken())
            if (response.isSuccessful){
                response.body()?.forEach { upcomingBill ->
                    upcomingBill.synced=true
                    upcomingBillsDao.insertUpcomingBill(upcomingBill) }
            }
        }
    }
    suspend fun syncUpcomingBills() {
        withContext(Dispatchers.IO){
            val accessToken = getAuthToken()
            upcomingBillsDao.getUnsyncedUpcomingBills().forEach { upcomingBill ->
                val response=apiClient.postUpcomingBill(accessToken, upcomingBill)
                if (response.isSuccessful){
                    upcomingBill.synced=true
                    upcomingBillsDao.updateUpcomingBill(upcomingBill)
                }
            }
        }
    }
    suspend fun getMonthlySummary():MutableLiveData<BillsSummary>{
        return withContext(Dispatchers.IO){
            val startDate = DateTimeUtils.getFirstDayOfMonth()
            val endDate = DateTimeUtils.getLastDayOfMonth()
            val today = DateTimeUtils.getDateToday()
            val paid = upcomingBillsDao.getPaidMonthlyBillsSum(startDate, endDate)
            val upcoming = upcomingBillsDao.getUpcomingBillsThisMonth(startDate, endDate, today)
            val total = upcomingBillsDao.getTotalMonthlyBills(startDate, endDate)
            val overdue = upcomingBillsDao.getOverdueBillsThisMonth(startDate, endDate, today)
            val summary = BillsSummary(paid=paid, upcoming=upcoming, overdue=overdue, total=total)
            MutableLiveData(summary)
        }
    }
}