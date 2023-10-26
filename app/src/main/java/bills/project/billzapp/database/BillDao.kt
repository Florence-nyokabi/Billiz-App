package bills.project.billzapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bills.project.billzapp.model.Bill

@Dao
interface BillDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBill(bill: Bill)

    @Query("SELECT * FROM Bills WHERE frequency = :freq")
    fun getRecurringBills(freq: String): List<Bill>

    @Query("SELECT * FROM Bills")
    fun getAllBills(): LiveData<List<Bill>>

    @Query ("SELECT* FROM Bills")
    fun getUnsyncedBills(): List<Bill>

}