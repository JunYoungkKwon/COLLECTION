package com.eight.collection.ui.main.setting

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import com.eight.collection.R
import com.eight.collection.databinding.ActivitySettingBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.main.setting.infoedit.InfoEditActivity
import com.eight.collection.ui.main.setting.privacy.PrivacyRule

class SettingActivity: BaseActivity<ActivitySettingBinding>(ActivitySettingBinding::inflate), View.OnClickListener {

    override fun initAfterBinding() {
        binding.settingBackBtnIv.setOnClickListener(this)
        binding.settingInfoBtnIb.setOnClickListener(this)
        //binding.settingDeveloperInfoIb.setOnClickListener(this)
        binding.settingPrivacyRuleIb.setOnClickListener(this)
        binding.settingLogoutTv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if (v == null) return

        when (v) {
            binding.settingBackBtnIv -> finishActivity()
            binding.settingInfoBtnIb -> {startNextActivity(InfoEditActivity::class.java)
            slideRight()}
            binding.settingDeveloperInfoIb -> {startNextActivity(InfoEditActivity::class.java)
                slideRight()}
            binding.settingPrivacyRuleIb -> {startNextActivity(PrivacyRule::class.java)
                slideRight()}
            binding.settingLogoutTv -> {
                // Dialog만들기
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_logout_custom, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setCancelable(false)
                val  mAlertDialog = mBuilder.show()
                mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val okButton = mDialogView.findViewById<ImageButton>(R.id.dialog_ok_ib)
                okButton.setOnClickListener {
                    showToast("로그아웃")
                    mAlertDialog.dismiss()
                }

                val noButton = mDialogView.findViewById<ImageButton>(R.id.dialog_cancle_ib)
                noButton.setOnClickListener {
                    mAlertDialog.dismiss()
                }
            }
        }
    }
}