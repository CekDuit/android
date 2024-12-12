package com.cekduit.app.data.remote.response

import com.google.gson.annotations.SerializedName

data class PostTransaction(

	@field:SerializedName("data")
	val data: DataRes,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataRes(

	@field:SerializedName("transaction_id")
	val transactionId: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("amount_added")
	val amountAdded: Int
)
