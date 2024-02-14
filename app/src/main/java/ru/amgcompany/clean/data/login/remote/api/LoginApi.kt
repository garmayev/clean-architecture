package ru.amgcompany.clean.data.login.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.amgcompany.clean.data.common.utils.WrappedResponse
import ru.amgcompany.clean.data.login.remote.dto.LoginRequest
import ru.amgcompany.clean.data.login.remote.dto.LoginResponse

interface LoginApi {
    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<WrappedResponse<LoginResponse>>
}