package com.eight.collection.ui.main.setting.infoedit

import android.view.View
import com.eight.collection.databinding.*
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.login.LoginSecondActivity
import com.eight.collection.ui.main.setting.infoedit.account.AccountDeleteActivity
import com.eight.collection.ui.main.setting.infoedit.nickname.NickNameEditActivity
import com.eight.collection.ui.main.setting.infoedit.password.PwEditActivity
import com.eight.collection.ui.main.setting.infoedit.phonenumber.PhoneNumberEditActivity
import com.eight.collection.utils.getNickName
import com.eight.collection.utils.getUserId

class InfoEditActivity: BaseActivity<ActivityInfoEditBinding>(ActivityInfoEditBinding::inflate), View.OnClickListener{

    override fun initAfterBinding() {
        binding.infoEditNicknameIb.setOnClickListener(this)
        binding.infoEditPwEditIb.setOnClickListener(this)
        binding.infoEditAccountIb.setOnClickListener(this)
        binding.infoEditPhoneNumberBtnIb.setOnClickListener(this)
        binding.infoEditBackBtnIv.setOnClickListener(this)

        //닉네임,아이디 설정
        binding.infoEditNicknameTv.text = getNickName()
        binding.infoEditIdTv.text = getUserId()


    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.infoEditNicknameIb ->  {startNextActivity(NickNameEditActivity::class.java)
                slideRight()}
            binding.infoEditPwEditIb ->  {startNextActivity(PwEditActivity::class.java)
                slideRight()}
            binding.infoEditAccountIb ->  {startNextActivity(AccountDeleteActivity::class.java)
                slideRight()}
            binding.infoEditPhoneNumberBtnIb ->  {startNextActivity(PhoneNumberEditActivity::class.java)
                slideRight()}
            binding.infoEditBackBtnIv -> finishActivity()


        }
    }
}