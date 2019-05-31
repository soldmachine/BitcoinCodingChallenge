package com.szoldapps.bitcoin.repository.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Represents a blockchain data response container
 */
data class BlockchainDto(

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
    val valueDtos: List<ValueDto>?

)

/**
 * Model of a pair of valueDtos
 */
data class ValueDto(

    @SerializedName("x")
    val x: Int,

    @SerializedName("y")
    val y: Double

)
