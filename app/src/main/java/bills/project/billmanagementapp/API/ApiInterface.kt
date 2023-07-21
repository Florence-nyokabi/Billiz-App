package bills.project.billmanagementapp.API

import bills.project.billmanagementapp.model.LoginRequest
import bills.project.billmanagementapp.model.LoginResponse
import bills.project.billmanagementapp.model.RegisterRequest
import bills.project.billmanagementapp.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("/users/register")
    suspend fun  registerUser (@Body registerRequest: RegisterRequest):Response<RegisterResponse>

    @POST("/users/login")
    suspend fun loginUser (@Body loginRequest: LoginRequest):Response<LoginResponse>

}