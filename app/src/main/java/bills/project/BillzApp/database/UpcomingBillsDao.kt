package bills.project.BillzApp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import bills.project.BillzApp.model.UpcomingBill

@Dao
interface UpcomingBillsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpcomingBill(vararg upcomingBill: UpcomingBill)

    @Query("SELECT *FROM UpcomingBills WHERE billId = :billId AND dueDate BETWEEN :startDate AND :endDate LIMIT 1")
    fun queryExistingBill(billId: String, startDate: String, endDate: String ): List<UpcomingBill>

    @Query ("SELECT* FROM UpcomingBills WHERE frequency = :freq AND paid = :paid ORDER BY dueDate")
    fun getUpcomingBillsByFrequency(freq:String, paid:Boolean): LiveData<List<UpcomingBill>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUpcomingBill(upcomingBill: UpcomingBill)

    @Query("SELECT*FROM UpcomingBills WHERE paid= :paid ORDER BY dueDate")
    fun getPaidBills(paid: Boolean=true):LiveData<List<UpcomingBill>>

    @Query("SELECT * FROM UpcomingBills WHERE synced = 0")
    fun getUnsyncedUpcomingBills(): List<UpcomingBill>

}