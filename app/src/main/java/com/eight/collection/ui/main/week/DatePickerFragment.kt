package com.eight.collection.ui.main.week

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.DatePicker
import android.widget.DatePicker.OnDateChangedListener
import androidx.fragment.app.DialogFragment
import com.eight.collection.databinding.WeekDatepickerBinding
import java.util.*


class DatePickerFragment(): DialogFragment(), DatePickerDialog.OnDateSetListener {

    fun DatePickerFragment() {}

    fun getInstance() {
        return DatePickerFragment()
    }
    interface MyFragmentInterfacer {
        fun onButtonClick(input: String?)
    }

    private var fragmentInterfacer: MyFragmentInterfacer? = null


    fun setFragmentInterfacer(fragmentInterfacer: MyFragmentInterfacer) {
        this.fragmentInterfacer = fragmentInterfacer
    }

    interface MyitemClickListener{
        fun getDate(selectdate: String)
    }

    private var mItemClickListener: MyitemClickListener? = null

    //리스너 객체를 전달받는 함수
    fun setMyitemClickListener(itemClickListener: MyitemClickListener){
        mItemClickListener = itemClickListener
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

        val datepickertest:DatePicker = binding.datepicker
        val calendar = Calendar.getInstance()


        val listener = OnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            val strDate = year.toString() + "/" + (monthOfYear + 1) + "/" + dayOfMonth

            binding.datepickerCancleBtnOffIb.setOnClickListener{ dialog?.dismiss() }


            binding.datepickerConfirmBtnOffIb.setOnClickListener{

                val selectdate:String = year.toString() + ((monthOfYear + 1)) + dayOfMonth
                Log.d("select1",selectdate)

                fragmentInterfacer?.onButtonClick(selectdate)
                mItemClickListener?.getDate(selectdate)
                dialog?.dismiss()
            }

        }

        val datePicker: DatePicker =binding.datepicker
        datePicker.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), listener)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //false로 설정해 주면 화면밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss 되지 않는다.
        isCancelable = true
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }
}