package com.eight.collection.ui.writing.first

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.*
import com.eight.collection.R
import com.eight.collection.ui.login.LoginFirstActivity
import com.eight.collection.ui.splash.SplashActivity
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.RefreshDialogInterface
import com.eight.collection.utils.saveIntroduceIs

class RefreshDialog(context: Context, anInterface: RefreshDialogInterface) : Dialog(context){

    private var refreshDialogInterface : RefreshDialogInterface = anInterface
    private lateinit var okButton : ImageButton
    private lateinit var cancelButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_refresh)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        okButton = findViewById(R.id.refresh_dialog_ok_ib)
        cancelButton = findViewById(R.id.refresh_dialog_cancel_ib)

        okButton.setOnClickListener {
            refreshDialogInterface.onOkButtonClicked()
            dismiss()
        }

        cancelButton.setOnClickListener {
            refreshDialogInterface.onCancelButtonClicked()
            dismiss()
        }
    }
}