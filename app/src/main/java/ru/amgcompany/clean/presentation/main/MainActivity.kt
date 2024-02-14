package ru.amgcompany.clean.presentation.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.ui.AppBarConfiguration
import dagger.hilt.android.AndroidEntryPoint
import ru.amgcompany.clean.R
import ru.amgcompany.clean.databinding.ActivityMainBinding
import ru.amgcompany.clean.presentation.login.LoginActivity
import ru.amgcompany.clean.utils.SharedPrefs
import javax.inject.Inject

class MainActivity : Activity() {
    private lateinit var appBarConfirguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPrefs = SharedPrefs(applicationContext)
    }

    override fun onStart() {
        super.onStart()
        checkIsLoggedIn()
    }

    private fun checkIsLoggedIn() {
        if (sharedPrefs.getToken().isEmpty()) {
            goToLoginActivity()
        }
    }

    private fun goToLoginActivity() {
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        finish()
    }

    private fun signOut() {
        sharedPrefs.clear()
        goToLoginActivity()
    }
}