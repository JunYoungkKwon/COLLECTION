package com.eight.collection.ui.main.month

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.eight.collection.R
import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.remote.calendar.CalendarService
import com.eight.collection.databinding.CalendarDateBinding
import com.eight.collection.databinding.CalendarYearMonthHeaderBinding
import com.eight.collection.databinding.FragmentMonthBinding
import com.eight.collection.ui.BaseFragment
import com.eight.collection.ui.finish.FinishActivity
import com.eight.collection.ui.main.setting.SettingActivity
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.temporal.TemporalField
import java.time.temporal.WeekFields
import java.util.*
import kotlin.collections.ArrayList


class MonthFragment(): BaseFragment<FragmentMonthBinding>(FragmentMonthBinding::inflate), MonthView  {

    private var selectedDate: LocalDate? = null

    override fun onResume() {
        super.onResume()
        getMonth()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initAfterBinding() {
        startMyLook()
        startWrite()
        startSetting()

        binding.monthBtnSettingIv.bringToFront()
        binding.monthBtnWriteIv.bringToFront()
        binding.monthBtnRankIv.bringToFront()
    }

    private fun startSetting() {

        binding.monthBtnSettingIv.setOnClickListener {
            startActivity(Intent(activity, SettingActivity::class.java))
        }
    }

    private fun startWrite() {
        binding.monthBtnWriteIv.setOnClickListener {
            startActivity(Intent(activity, WritefirstActivity::class.java))
        }
    }

    private fun startMyLook() {
        binding.monthBtnRankIv.setOnClickListener {
            var layoutInflater = LayoutInflater.from(context).inflate(R.layout.toast_custom,null)
            var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
            text.text="Coming Soon!!!"
            var toast = Toast(context)
            toast.view = layoutInflater
            toast.show()
            //Navigation.findNavController(it).navigate(R.id.MyLookFragment)
        }

    }
    private fun getMonth() {
        CalendarService.getMonth(this)
    }

    override fun onMonthLoading() {
        binding.loginLoadingInIv.visibility = View.VISIBLE
        binding.loginLoadingCircleIv.visibility = View.VISIBLE
        binding.loginLoadingBackgroundIv.visibility = View.VISIBLE
        //로딩이미지 애니메이션
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        binding.loginLoadingCircleIv.startAnimation(animation)
        binding.loginDimBackground.visibility = View.VISIBLE
    }

    override fun onMonthSuccess(month: ArrayList<Calendar>) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        //day cell 크기 조정
        val dm = DisplayMetrics()
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)
        binding.calendarView.apply {
            val dayWidth = dm.widthPixels / 7
            val dayHeight = (dayWidth * 1.8).toInt()
            daySize = com.kizitonwose.calendarview.utils.Size(dayWidth, dayHeight)
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            val calendarDay = CalendarDateBinding.bind(view).calendarDayTv
            val calendarCell = CalendarDateBinding.bind(view).dateCell
            val rankPoint = CalendarDateBinding.bind(view).calendarRankIv
            val todayHighlight = CalendarDateBinding.bind(view).calendarTodayView
        }

        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {

            override fun create(view: View) = DayViewContainer(view)

            @RequiresApi(Build.VERSION_CODES.O)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.calendarDay.text = day.date.dayOfMonth.toString()
                container.calendarCell.setOnClickListener{
                    val currentSelection = selectedDate
                    if(container.rankPoint.drawable == null) {
                        var layoutInflater = LayoutInflater.from(context).inflate(R.layout.toast_custom,null)
                        var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                        text.text="해당 날짜에는 ootd가 존재하지 않습니다."
                        var toast = Toast(context)
                        toast.view = layoutInflater
                        toast.show()
                    }
                    else{
                        if (currentSelection == day.date) {
                            selectedDate = null
                        } else {
                            selectedDate = day.date
                            val intent = Intent(context,FinishActivity::class.java)
                            val date = selectedDate.toString()
                            intent.apply {
                                this.putExtra("date",date)
                            }
                            startActivity(intent)
                        }
                    }
                }

                if (day.owner == DayOwner.THIS_MONTH) {
                    //today highlight
                    val currentDay = LocalDate.now()

                    for(i in 0 .. month.size-1 step (1)){
                        //Date Type -> LocalDate Tyoe
                        val date: Date = month[i].date
                        val localdate:LocalDate = date.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        if (localdate == day.date){
                            when(month[i].lookpoint){
                                1 -> container.rankPoint.setImageResource(R.drawable.calendar_rank_1_on)
                                2 -> container.rankPoint.setImageResource(R.drawable.calendar_rank_2_on)
                                3 -> container.rankPoint.setImageResource(R.drawable.calendar_rank_3_on)
                                4 -> container.rankPoint.setImageResource(R.drawable.calendar_rank_4_on)
                                5 -> container.rankPoint.setImageResource(R.drawable.calendar_rank_5_on)
                                else -> container.rankPoint.setImageResource(0)
                            }
                        }
                    }
                    //오늘 날짜인 것 표시
                    if (currentDay == day.date){
                        container.todayHighlight.visibility = View.VISIBLE
                    }
                    else{
                        container.todayHighlight.visibility = View.GONE
                    }
                    container.calendarDay.setTextColor(Color.BLACK)
                } else {
                    for(i in 0 .. month.size-1 step (1)){
                        //Date Type -> LocalDate Tyoe
                        val date: Date = month[i].date
                        val localdate:LocalDate = date.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        if (localdate == day.date){
                            when(month[i].lookpoint){
                                1 -> container.rankPoint.setImageResource(R.drawable.calendar_rank_1_off)
                                2 -> container.rankPoint.setImageResource(R.drawable.calendar_rank_2_off)
                                3 -> container.rankPoint.setImageResource(R.drawable.calendar_rank_3_off)
                                4 -> container.rankPoint.setImageResource(R.drawable.calendar_rank_4_off)
                                5 -> container.rankPoint.setImageResource(R.drawable.calendar_rank_5_off)
                                else -> container.rankPoint.setImageResource(0)
                            }
                        }
                    }
                    container.calendarDay.setTextColor(Color.LTGRAY)
                }
            }
        }


        class MonthViewContainer(view: View) : ViewContainer(view) {
            val calendarMonth = CalendarYearMonthHeaderBinding.bind(view).calendarMonthTv
            val calendarYear= CalendarYearMonthHeaderBinding.bind(view).calendarYearTv
        }
        binding.calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.calendarYear.text = "${month.year}"
                container.calendarMonth.text = "${month.yearMonth.month.name.toLowerCase().capitalize()}"
                container.calendarMonth.setOnClickListener{
                    val dialog: BottomSheetDialog = BottomSheetDialog(requireContext())
                    dialog.setContentView(R.layout.activity_finish)
                    dialog.show()
                }

            }
        }


        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val daysOfWeek = arrayOf(
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY
        )
        binding.calendarView.setup(firstMonth, lastMonth, daysOfWeek.first())
        if(selectedDate == null){
            binding.calendarView.scrollToMonth(currentMonth)
        }else{
            binding.calendarView.scrollToDate(selectedDate!!)
        }

    }

    override fun onMonthFailure(code: Int, message: String) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE
        when (code) {
            4000,4005 -> {
                Log.d("Month/Data/ERROR", "error")
            }
            else -> {
                Log.d("Month/ERROR", "error")
            }
        }
    }

}

