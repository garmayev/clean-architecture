package ru.amgcompany.clean.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.amgcompany.clean.R
import ru.amgcompany.clean.data.common.utils.WrappedResponse
import ru.amgcompany.clean.data.login.remote.api.LoginApi
import ru.amgcompany.clean.data.login.remote.dto.LoginRequest
import ru.amgcompany.clean.data.login.remote.dto.LoginResponse
import ru.amgcompany.clean.data.login.repository.LoginRepository
import ru.amgcompany.clean.databinding.ActivityLoginBinding
import ru.amgcompany.clean.domain.login.entity.LoginEntity
import ru.amgcompany.clean.domain.login.usecase.LoginUseCase
import ru.amgcompany.clean.presentation.main.MainActivity
import ru.amgcompany.clean.presentation.common.extension.isEmail
import ru.amgcompany.clean.presentation.common.extension.showGenericAlertDialog
import ru.amgcompany.clean.presentation.common.extension.showToast
import ru.amgcompany.clean.utils.SharedPrefs
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPrefs: SharedPrefs
    private val TAG: String = "LoginActivity";
    private val viewModel: LoginViewModel by viewModels()
    private val openRegisterActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                goToMainActivity()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        sharedPrefs = SharedPrefs(applicationContext)
        Log.d(TAG, viewModel.toString())
        login()
        register()
        observe()
    }

    private fun register() {
        findViewById<Button>(R.id.register_button).setOnClickListener { _ ->
            Log.d(TAG, "Register?")
            gotoRegisterActivity()
        }
    }

    private fun login() {
//        var loginButton = findViewById<Button>(R.id.login_button)
        findViewById<Button>(R.id.login_button).setOnClickListener { _ ->
            Log.d("LoginActivity", "Login?")
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            if (validate(email, password)) {
                val loginRequest = LoginRequest(email, password)
//                viewModel.login(loginRequest)
            }
        }
    }

    private fun validate(email: String, password: String): Boolean {
        resetAllInputError()
        if (!email.isEmail()) {
            setEmailError(getString(R.string.error_email_not_valid))
            return false
        }

        if (password.length < 8) {
            setPasswordError(getString(R.string.error_password_not_valid))
            return false
        }

        return true
    }

    private fun resetAllInputError() {
        setEmailError(null)
        setPasswordError(null)
    }

    private fun setEmailError(e: String?) {
        binding.emailInput.error = e
    }

    private fun setPasswordError(e: String?) {
        binding.passwordInput.error = e
    }

    private fun observe() {
//        Log.d(TAG, viewModel.mState.toString());
//        viewModel.mState
//            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
//            .onEach { state -> handleStateChange(state) }
//            .launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: LoginActivityState) {
        when (state) {
            is LoginActivityState.Init -> Unit
            is LoginActivityState.ErrorLogin -> handleErrorLogin(state.rawResponse)
            is LoginActivityState.SuccessLogin -> handleSuccessLogin(state.loginEntity)
            is LoginActivityState.ShowToast -> showToast(state.message)
            is LoginActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleErrorLogin(response: WrappedResponse<LoginResponse>) {
        showGenericAlertDialog(response.message)
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.loginButton.isEnabled = !isLoading
        binding.registerButton.isEnabled = !isLoading
        binding.loadingProgressBar.isIndeterminate = isLoading
        if (!isLoading) {
            binding.loadingProgressBar.progress = 0
        }
    }

    private fun handleSuccessLogin(loginEntity: LoginEntity) {
        sharedPrefs.saveToken(loginEntity.authKey)
        showToast("Welcome ${loginEntity.username}")
        goToMainActivity()
    }

    private fun gotoRegisterActivity() {
//        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
//        finish()
    }

    private fun goToMainActivity() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }
}