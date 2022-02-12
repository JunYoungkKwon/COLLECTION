package com.eight.collection.ui.writing.second.who

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eight.collection.R
import com.eight.collection.databinding.FragmentWritefirstTopBinding
import com.eight.collection.ui.signup.SignupThirdActivity
import com.eight.collection.ui.writing.CustomDialogInterface

class WritesecondWhoCustomDialog(context: Context, anInterface: CustomDialogInterface) : Dialog(context) {

    private var customDialogInterface : CustomDialogInterface = anInterface
    private lateinit var addButton : Button
    private lateinit var cancelButton : Button
    private lateinit var addEditText : EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_whotag)

        addButton = findViewById(R.id.add_whotag_confirm_button)
        cancelButton = findViewById(R.id.add_whotag_cancel_button)
        addEditText = findViewById(R.id.add_whotag_et)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        addButton.setOnClickListener {
            var addText : String = addEditText.text.toString()
            customDialogInterface.onAddButtonClicked(addText)
            dismiss()
        }

        cancelButton.setOnClickListener {
            customDialogInterface.onCancelButtonClicked()
            dismiss()
        }
    }
}