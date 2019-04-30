package cesar1287.com.github.desafiopicpay.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
    @SerializedName("destination_user")
    val destinationUser: DestinationUser,
    @SerializedName("id")
    val id: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("timestamp")
    val timestamp: Int,
    @SerializedName("value")
    val value: Double
) : Parcelable