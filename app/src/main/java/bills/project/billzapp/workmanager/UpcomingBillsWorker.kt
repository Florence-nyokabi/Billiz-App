package bills.project.billzapp.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import bills.project.billzapp.Repository.BillsRepository

class UpcomingBillsWorker(context: Context, workerParams: WorkerParameters):
    CoroutineWorker(context, workerParams) {
        val billsRepo = BillsRepository()

    override suspend fun doWork(): Result {
        billsRepo.createRecurringWeeklyBills()
        billsRepo.createRecurringAnnualBills()
        billsRepo.createRecurringQuarterlyBills()
        billsRepo.createRecurringMonthlyBills()

        return Result.success()
    }
}