package com.gb.agcheb.myapptestkotlin.ui.splash

import android.os.Handler
import com.gb.agcheb.myapptestkotlin.ui.base.BaseActivity
import com.gb.agcheb.myapptestkotlin.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity:BaseActivity<Boolean?>() {
    override val model:SplashViewModel by viewModel()
    override val layoutRes: Int? = null

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({model.requestUser()}, 1000)
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