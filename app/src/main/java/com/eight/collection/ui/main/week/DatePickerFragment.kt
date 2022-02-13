package com.eight.collection.ui.main.week

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eight.collection.R
import com.eight.collection.databinding.ActivityMainBinding
import com.eight.collection.databinding.CalendarYearMonthHeaderBinding
import com.eight.collection.databinding.WeekDatepickerBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.main.lookpoint.MyLookFragment
import com.eight.collection.ui.main.week.WeekFragment
import java.util.*
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.DialogFragmentNavigator
import com.applandeo.materialcalendarview.utils.calendar
import com.eight.collection.databinding.ItemWritefirstPhotoBinding
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import androidx.annotation.NonNull
import com.eight.collection.ui.main.MainActivity








class DatePickerFragment(): DialogFragment(), DatePickerDialog.OnDateSetListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //false로 설정해 주면 화면밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss 되지 않는다.
        isCancelable = true

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding: WeekDatepickerBinding = WeekDatepickerBinding.inflate(inflater, container, false)
        // DatePicker Header 없애기
        val datepickerHeaderid = binding.datepicker.getChildAt(0)
            .resources.getIdentifier("date_picker_header", "id", "android")
        binding.datepicker.findViewById<View>(datepickerHeaderid).visibility = View.GONE
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]

        binding.datepickerCancleBtnOffIb.setOnClickListener {
            dialog?.dismiss()
        }
        binding.datepickerConfirmBtnOffIb.setOnClickListener {
            dialog?.dismiss()
        }

        return binding.root
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }


}