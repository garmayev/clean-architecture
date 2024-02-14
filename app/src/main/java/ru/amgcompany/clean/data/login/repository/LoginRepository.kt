package ru.amgcompany.clean.data.login.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.amgcompany.clean.data.common.utils.WrappedResponse
import ru.amgcompany.clean.data.login.remote.api.LoginApi
import ru.amgcompany.clean.data.login.remote.dto.LoginRequest
import ru.amgcompany.clean.data.login.remote.dto.LoginResponse
import ru.amgcompany.clean.domain.common.base.BaseResult
import ru.amgcompany.clean.domain.login.ILoginRepository
import ru.amgcompany.clean.domain.login.entity.LoginEntity
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginApi: LoginApi
) : ILoginRepository {
    override suspend fun signIn(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, WrappedResponse<LoginResponse>>> {
        return flow {
            val response = loginApi.login(loginRequest)
            if (response.isSuccessful) {
                val body = response.body()!!
                val loginEntity = LoginEntity(body.data?.id!!, body.data?.username!!, body.data?.email!!, body.data?.authKey!!)
                emit(BaseResult.Success(loginEntity))
            } else {
                val type = object : TypeToken<WrappedResponse<LoginResponse>>(){}.type
                val err : WrappedResponse<LoginResponse> = Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code()
                emit(BaseResult.Error(err))
            }
        }
    }
}