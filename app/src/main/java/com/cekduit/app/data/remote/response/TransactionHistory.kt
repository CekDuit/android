package com.cekduit.app.data.remote.response

import com.google.gson.annotations.SerializedName

data class TransactionHistory(
	@field:SerializedName("data")
	val data: List<Transaction>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Transaction(

	@field:SerializedName("transaction_id")
	val transactionId: String,

	@field:SerializedName("datetime")
	val datetime: String,

	@field:SerializedName("subCategory")
	val subCategory: String,

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("notes")
	val notes: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("merchant_name")
	val merchantName: String,

	@field:SerializedName("currency")
	val currency: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("transaction_type")
	val transactionType: String,

	@field:SerializedName("payment_method")
	val paymentMethod: String
)
