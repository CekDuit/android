package com.cekduit.app.data.remote.retrofit

import com.cekduit.app.data.remote.response.AuthResponse
import com.cekduit.app.data.remote.response.PostTransaction
import com.cekduit.app.data.remote.response.Transaction
import com.cekduit.app.data.remote.response.TransactionHistory
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class AuthRequest(
    val email: String,
    val token: String
)

enum class TimeFrame(val value: String) {
    MONTHLY("MONTHLY"),
    LAST_30_DAYS("LAST_30_DAYS"),
    LAST_7_DAYS("LAST_7_DAYS"),
    LAST_3_DAYS("LAST_3_DAYS"),
    TODAY("TODAY"),
    ALL_TIME("ALL_TIME")
}

enum class TransactionType(val value: String) {
    INCOME("INCOME"),
    EXPENSE("EXPENSE"),
    ALL("ALL")
}

enum class Category(val value: String) {
    FOOD("FOOD"),
    HEALTH("HEALTH"),
    DRINKS("DRINKS"),
    HOUSEHOLD("HOUSEHOLD"),
    TRANSPORTATION("TRANSPORTATION"),
    GROCERIES("GROCERIES"),
    FAMILY("FAMILY"),
    SUBSCRIPTION("SUBSCRIPTION"),
    APPAREL("APPAREL"),
    EDUCATION("EDUCATION"),
    ENTERTAINMENT("ENTERTAINMENT"),
    UTILITIES("UTILITIES"),
    BEAUTY("BEAUTY"),
    OTHER("OTHER"),
    ALL("ALL")
}

interface ApiService {
    @GET("wallet/history")
    suspend fun getTransactionHistory(): TransactionHistory

    @GET("wallet/history-filter")
    suspend fun getTransactionHistoryFilter(
        @Query("transactionType") transactionType: TransactionType,
        @Query("category") category: Category,
        @Query("timeFrame") timeframe: TimeFrame
    ): TransactionHistory

    @POST("wallet/addbalance")
    suspend fun addTransaction(
        @Body body: Transaction
    ): PostTransaction

    @POST("wallet/deductbalance")
    suspend fun removeTransaction(
        @Body body: Transaction
    ): PostTransaction

    @POST
    fun login(
        @Body body: AuthRequest
    ): Call<AuthResponse>

    @POST("/start-watch")
    suspend fun startWatch(
        @Body body: AuthRequest
    ): Call<AuthResponse>
}