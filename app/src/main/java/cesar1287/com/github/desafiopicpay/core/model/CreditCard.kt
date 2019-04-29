package cesar1287.com.github.desafiopicpay.core.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "credit_card")
data class CreditCard (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "card_number") val cardNumber: String?,
    @ColumnInfo(name = "cvv") val cvv: String?,
    @ColumnInfo(name = "expiry_date") val expiryDate: String?,
    @ColumnInfo(name = "name") val name: String?
) : Parcelable