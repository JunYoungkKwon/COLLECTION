package com.eight.collection.ui.writing

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import com.eight.collection.R

class WritefirstTopCustomDialog(context: Context, anInterface: CustomDialogInterface) : Dialog(context) {

    private var customDialogInterface : CustomDialogInterface = anInterface

    private lateinit var addButton : Button
    private lateinit var cancelButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_toptag)

        addButton = findViewById(R.id.add_toptag_confirm_button)
        cancelButton = findViewById(R.id.add_toptag_cancel_button)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        addButton.setOnClickListener {
            customDialogInterface.onAddButtonClicked()
            dismiss()
        }

        cancelButton.setOnClickListener {
            customDialogInterface.onCancelButtonClicked()
            dismiss()
        }
    }
}