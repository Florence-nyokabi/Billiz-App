package bills.project.BillzApp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import bills.project.BillzApp.model.Bill
import bills.project.BillzApp.model.UpcomingBill

@Database(entities = [Bill::class, UpcomingBill::class], version = 2)
abstract class BillzDb: RoomDatabase() {
    abstract fun billDao(): BillDao
    abstract  fun upcomingBillsDao(): UpcomingBillsDao

    companion object{
        var database: BillzDb? = null

        fun getDatabase(context: Context):BillzDb{
            if (database == null){
                database=Room
                    .databaseBuilder(context, BillzDb::class.java, "Billzdb")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return database as BillzDb
        }
    }
}
