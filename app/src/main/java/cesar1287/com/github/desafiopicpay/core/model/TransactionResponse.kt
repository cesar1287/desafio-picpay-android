package cesar1287.com.github.desafiopicpay.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionResponse(
    @SerializedName("transaction")
    val transaction: Transaction
) : Parcelable