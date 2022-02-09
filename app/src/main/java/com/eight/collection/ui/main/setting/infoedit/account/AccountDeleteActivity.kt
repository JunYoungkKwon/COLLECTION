package com.eight.collection.ui.main.setting.infoedit.account

import android.view.View
import android.widget.Toast
import com.eight.collection.databinding.ActivityAccountDeleteBinding
import com.eight.collection.databinding.ActivityNicknameEditBinding
import com.eight.collection.ui.BaseActivity

class AccountDeleteActivity: BaseActivity<ActivityAccountDeleteBinding>(ActivityAccountDeleteBinding::inflate), View.OnClickListener{




    override fun initAfterBinding() {
        binding.accountBtnIb.setOnClickListener(this)
        binding.accountBackBtnIv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.accountBtnIb -> {startNextActivity(AccountDeleteSecondActivity::class.java)
                slideRight()}
            binding.accountBackBtnIv -> finishActivity()


        }
    }
}