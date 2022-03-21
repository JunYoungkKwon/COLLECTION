package com.eight.collection.ui.splash

import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivitySplashBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.login.LoginFirstActivity
import com.eight.collection.ui.main.MainActivity
import com.eight.collection.utils.getIntroduceIs
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability


class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate), SplashView {

    var getIntroduceIs : Boolean = getIntroduceIs()

    private val MY_REQUEST_CODE = 100
    private var mAppUpdateManager: AppUpdateManager? = null

    override fun initAfterBinding() {
        Handler(Looper.getMainLooper()).postDelayed({
            mAppUpdateManager = AppUpdateManagerFactory.create(this)
            val appUpdateInfoTask = mAppUpdateManager!!.appUpdateInfo

            appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    // 업데이트 있음
                    Log.e("Update", "AppUpdateManager")
                    try {
                        mAppUpdateManager!!.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.IMMEDIATE,
                            this,
                            MY_REQUEST_CODE)
                    } catch (e: SendIntentException) {
                        Log.e("AppUpdater", "AppUpdateManager Error", e)
                        e.printStackTrace()
                    }
                } else {
                    //업데이트 없음
                    Log.e("NoUpdate", "AppUpdateManager")
                    val  intent= Intent(this, LoginFirstActivity::class.java)
                    startActivity(intent)
                    finish()
                    autoLogin()
                }
            }


        }, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.e("MY_APP", "Update flow failed! Result code: $resultCode")

                Toast(this).showCustomToast("업데이트에 실패했습니다.", this)
                finishAffinity()
            }
        }
    }

    // Checks that the update is not stalled during 'onResume()'.
// However, you should execute this check at all entry points into the app.
    override fun onResume() {
        super.onResume()

        mAppUpdateManager?.appUpdateInfo?.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    // If an in-app update is already running, resume the update.
                    mAppUpdateManager?.startUpdateFlowForResult(
                        appUpdateInfo,
                        IMMEDIATE,
                        this,
                        MY_REQUEST_CODE
                    )
                }
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