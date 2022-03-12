package com.eight.collection.ui.main.setting.privacy

import android.view.View
import android.widget.Toast
import com.eight.collection.databinding.ActivityNicknameEditBinding
import com.eight.collection.databinding.ActivityPrivacyRuleBinding
import com.eight.collection.ui.BaseActivity

class PrivacyRuleActivity: BaseActivity<ActivityPrivacyRuleBinding>(ActivityPrivacyRuleBinding::inflate), View.OnClickListener{

    override fun initAfterBinding() {
        binding.privacyBackBtnIv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.privacyBackBtnIv -> finishActivity()


        }
    }
}