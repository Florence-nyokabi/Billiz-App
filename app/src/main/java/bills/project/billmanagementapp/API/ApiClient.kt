package bills.project.billmanagementapp.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiClient {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://13.37.106.218")
        .baseUrl("http://13.37.106.218")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun <T> buildApiClient(apiInterface: Class<T>): T {
        return retrofit.create(apiInterface)
    }
}