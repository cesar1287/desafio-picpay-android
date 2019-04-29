package cesar1287.com.github.desafiopicpay.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cesar1287.com.github.desafiopicpay.core.model.CreditCard

object PicPayDatabase {

    @Database(entities = [CreditCard::class], version = 1)
    abstract class PicPayRoomDatabase : RoomDatabase() {
        abstract fun creditCardDao(): CreditCardDao
    }

    fun getDatabase(context: Context) : PicPayRoomDatabase {
        return Room.databaseBuilder(
            context,
            PicPayRoomDatabase::class.java, "picpay_db"
        ).build()
    }
}