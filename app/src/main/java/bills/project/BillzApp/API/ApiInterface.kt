package bills.project.BillzApp.API

import bills.project.BillzApp.model.LoginRequest
import bills.project.BillzApp.model.LoginResponse
import bills.project.BillzApp.model.RegisterRequest
import bills.project.BillzApp.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("/users/register")
    suspend fun  registerUser (@Body registerRequest: RegisterRequest):Response<RegisterResponse>

    @POST("/users/login")
    suspend fun loginUser (@Body loginRequest: LoginRequest):Response<LoginResponse>

}