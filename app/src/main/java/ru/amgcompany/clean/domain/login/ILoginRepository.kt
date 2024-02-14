package ru.amgcompany.clean.domain.login

import kotlinx.coroutines.flow.Flow
import ru.amgcompany.clean.data.common.utils.WrappedResponse
import ru.amgcompany.clean.data.login.remote.dto.LoginRequest
import ru.amgcompany.clean.data.login.remote.dto.LoginResponse
import ru.amgcompany.clean.domain.common.base.BaseResult
import ru.amgcompany.clean.domain.login.entity.LoginEntity

interface ILoginRepository {
    suspend fun signIn(loginRequest: LoginRequest) : Flow<BaseResult<LoginEntity, WrappedResponse<LoginResponse>>>
}