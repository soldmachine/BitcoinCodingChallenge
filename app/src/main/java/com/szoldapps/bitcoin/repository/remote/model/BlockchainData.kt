package com.szoldapps.bitcoin.repository.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Represents a blockchain data response container
 */
data class BlockchainData(

    @SerializedName("status")
    val status: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("unit")
    val unit: String?,

    @SerializedName("period")
    val period: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("values")
    val values: List<Value>?

)

/**
 * Model of a pair of values
 */
data class Value(

    @SerializedName("x")
    val x: Int,

    @SerializedName("y")
    val y: Double

)
