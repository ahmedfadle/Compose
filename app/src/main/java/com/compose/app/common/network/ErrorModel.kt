package com.compose.app.common.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
@Parcelize
data class ErrorModel(
    val error: String?,
    val messageAr: String?,
    val messageEn: String?,
    val statusCode: String?,
    val statusNumber: Int?
):Parcelable,Serializable