package com.gb.agcheb.myapptestkotlin.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.gb.agcheb.myapptestkotlin.R
import com.gb.agcheb.myapptestkotlin.data.exceptions.NoAuthException
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<T> : AppCompatActivity(), CoroutineScope {

    companion object {
        const val RC_SIGN_IN = 3456
    }

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + job
    }

    val job = Job()
    private lateinit var dataJob: Job
    private lateinit var errorJob: Job

    abstract val model: BaseViewModel<T>
    abstract val layoutRes: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let { setContentView(it) }
    }

    override fun onStart() {
        super.onStart()
        dataJob = launch {
            model.getViewState().consumeEach {
                renderData(it)
            }
        }

        errorJob = launch {
            model.getErrorChannel().consumeEach {
                renderError(it)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        dataJob.cancel()
        errorJob.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }

    abstract fun renderData(data: T)

    private fun renderError(error: Throwable) {
        when(error){
            is NoAuthException -> startLogin()
            else -> error.message?.let { showError(it) }
        }
    }

    private fun startLogin() {
        val providers = listOf(
                AuthUI.IdpConfig.GoogleBuilder().build()
//                AuthUI.IdpConfig.FacebookBuilder().build()
        )

        startActivityForResult(
        AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.android_robot_web)
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(providers)
                .build()
        , RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK) {
            finish()
        }
    }
    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT)
    }
}