package com.eight.collection.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivitySplashBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.introduce.IntroduceFirstDialog
import com.eight.collection.ui.introduce.IntroduceSecondDialog
import com.eight.collection.ui.login.LoginFirstActivity
import com.eight.collection.ui.main.MainActivity
import com.eight.collection.utils.getIntroduceIs

class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate), SplashView {

    var getIntroduceIs : Boolean = getIntroduceIs()

    override fun initAfterBinding() {
        if(getIntroduceIs == true){
            Handler(Looper.getMainLooper()).postDelayed({
                val intent= Intent(this, LoginFirstActivity::class.java)
                startActivity(intent)
                finish()
                autoLogin()
            }, 2000)
        }

        else {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent= Intent(this, LoginFirstActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }
    }

    private fun autoLogin() {
        AuthService.autoLogin(this)
    }

    override fun onAutoLoginLoading() {
    }

    override fun onAutoLoginSuccess() {
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onAutoLoginFailure(code: Int, message: String) {
        startActivityWithClear(LoginFirstActivity::class.java)
    }
}