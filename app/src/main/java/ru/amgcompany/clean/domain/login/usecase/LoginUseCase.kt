package ru.amgcompany.clean.domain.login.usecase

import kotlinx.coroutines.flow.Flow
import ru.amgcompany.clean.data.common.utils.WrappedResponse
import ru.amgcompany.clean.data.login.remote.dto.LoginRequest
import ru.amgcompany.clean.data.login.remote.dto.LoginResponse
import ru.amgcompany.clean.domain.common.base.BaseResult
import ru.amgcompany.clean.domain.login.ILoginRepository
import ru.amgcompany.clean.domain.login.entity.LoginEntity
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val ILoginRepository: ILoginRepository
) {
    suspend fun execute(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, WrappedResponse<LoginResponse>>> {
        return ILoginRepository.signIn(loginRequest)
    }
}