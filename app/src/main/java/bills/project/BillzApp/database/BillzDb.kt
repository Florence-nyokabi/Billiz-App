package bills.project.BillzApp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import bills.project.BillzApp.model.Bill

@Database(entities = [Bill::class], version = 1)
abstract class BillzDb: RoomDatabase() {
    abstract fun billDao(): BillDao
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
