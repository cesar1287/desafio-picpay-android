package cesar1287.com.github.desafiopicpay.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransationResponse(
    @SerializedName("transaction")
    val transaction: Transaction
) : Parcelable