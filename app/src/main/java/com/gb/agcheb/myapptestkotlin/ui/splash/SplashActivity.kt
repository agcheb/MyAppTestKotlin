package com.gb.agcheb.myapptestkotlin.ui.splash

import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.gb.agcheb.myapptestkotlin.ui.base.BaseActivity
import com.gb.agcheb.myapptestkotlin.ui.base.BaseViewModel
import com.gb.agcheb.myapptestkotlin.ui.main.MainActivity

class SplashActivity:BaseActivity<Boolean?, SplashViewState>() {
    override val viewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }
    override val layoutRes: Int? = null

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({viewModel.requestUser()}, 1000)
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?. let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        MainActivity.start(this)
        finish()
    }


}