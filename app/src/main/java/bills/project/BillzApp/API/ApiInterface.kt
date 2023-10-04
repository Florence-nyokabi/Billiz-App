package bills.project.BillzApp.API

import bills.project.BillzApp.model.Bill
import bills.project.BillzApp.model.LoginRequest
import bills.project.BillzApp.model.LoginResponse
import bills.project.BillzApp.model.RegisterRequest
import bills.project.BillzApp.model.RegisterResponse
import bills.project.BillzApp.model.UpcomingBill
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {
    @POST("/users/register")
    suspend fun  registerUser (@Body registerRequest: RegisterRequest):Response<RegisterResponse>

    @POST("/users/login")
    suspend fun loginUser (@Body loginRequest: LoginRequest):Response<LoginResponse>

    @POST("/bills")
    suspend fun postBill(@Header("Authorization")token:String, @Body bill: Bill):Response<Bill>

    @POST("/upcoming-bills")
    suspend fun postUpcomingBill(@Header("Authorization")token:String, @Body upcomingBill: UpcomingBill): Response<UpcomingBill>
}