package com.eight.collection.ui.main.setting.logout

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.eight.collection.R

class DialogLogout(context : Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnOK : Button
    private lateinit var btnCancel : Button
    //private lateinit var listener : MyDialogOKClickedListener

    fun start() {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_logout_custom)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함


        btnOK = dlg.findViewById(R.id.dialog_ok_ib)
        btnOK.setOnClickListener {
            //listener.onOKClicked()
            dlg.dismiss()
        }

        btnCancel = dlg.findViewById(R.id.dialog_cancle_ib)
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

//    fun setOnOKClickedListener() {
//        this.listener = object: MyDialogOKClickedListener {
//            override fun onOKClicked() {
//                listener()
//            }
//        }
//    }
//
//    interface MyDialogOKClickedListener {
//        fun onOKClicked()
//    }

}