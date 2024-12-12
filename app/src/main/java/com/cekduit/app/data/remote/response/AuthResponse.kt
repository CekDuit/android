package com.cekduit.app.data.remote.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("walletCreated")
	val walletCreated: Boolean,

	@field:SerializedName("firstname")
	val firstname: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("lastname")
	val lastname: String
)
