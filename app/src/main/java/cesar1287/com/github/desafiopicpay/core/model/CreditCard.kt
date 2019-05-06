package cesar1287.com.github.desafiopicpay.core.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "credit_card")
data class CreditCard (
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "card_number") var cardNumber: String? = null,
    @ColumnInfo(name = "cvv") var cvv: String? = null,
    @ColumnInfo(name = "expiry_date") var expiryDate: String? = null,
    @ColumnInfo(name = "name") var name: String? = null
) : Parcelable