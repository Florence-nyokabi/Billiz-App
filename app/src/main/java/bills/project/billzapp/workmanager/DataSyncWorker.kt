package bills.project.billzapp.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import bills.project.billzapp.Repository.BillsRepository

class DataSyncWorker(context: Context, workerParams: WorkerParameters):
CoroutineWorker(context, workerParams){
    val billsRepo = BillsRepository()

    override suspend fun doWork(): Result{
        billsRepo.syncBills()
        billsRepo.syncUpcomingBills()

        return Result.success()
    }
}