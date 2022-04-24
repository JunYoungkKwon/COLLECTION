package com.eight.collection.ui.main.match

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import androidx.core.widget.NestedScrollView
import com.eight.collection.R
import com.eight.collection.data.entities.Cloth
import com.eight.collection.data.entities.Diary
import com.eight.collection.data.entities.Photo
import com.eight.collection.data.remote.finish.Finish
import com.eight.collection.data.remote.finish.FinishService
import com.eight.collection.data.remote.setting.SettingService
import com.eight.collection.databinding.*
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.finish.FinishActivity
import com.eight.collection.ui.introduce.IntroduceDialog
import com.eight.collection.ui.main.mylook.MyLookDetailActivity
import com.eight.collection.ui.main.mylook.MyLookOOTD
import com.eight.collection.ui.main.mylook.MyLookRVAdapter
import com.eight.collection.ui.main.setting.SettingActivity
import com.eight.collection.ui.main.week.DeleteView
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import java.io.Serializable
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class CalendarBSActivity :BaseActivity <ActivityMatchCalendarBsBinding>(ActivityMatchCalendarBsBinding::inflate) {

    override fun initAfterBinding() {
        overridePendingTransition(R.anim.slide_up, R.anim.none)
        //scrollFinsh()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isFinishing) {
            overridePendingTransition(R.anim.none, R.anim.slide_down)
        }
    }

    private fun initCalendar(){
        //day cell 크기 조정
        val dm = DisplayMetrics()
        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager //requireContext() -> this
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
                container.calendarCell.setOnClickListener{}

//                if (day.owner == DayOwner.THIS_MONTH) {
//                    //today highlight
//                    val currentDay = LocalDate.now()
//
//                    //오늘 날짜인 것 표시
//                    if (currentDay == day.date){
//                        container.todayHighlight.visibility = View.VISIBLE
//                    }
//                    else{
//                        container.todayHighlight.visibility = View.GONE
//                    }
//                    container.calendarDay.setTextColor(Color.BLACK)
//                } else {
//
//                    container.calendarDay.setTextColor(Color.LTGRAY)
//                }
            }
        }








        class MonthViewContainer(view: View) : ViewContainer(view) {
            val calendarYear = CalendarBsYearMonthHeaderBinding.bind(view).calendarYearTv
            val calendarMonth= CalendarBsYearMonthHeaderBinding.bind(view).calendarMonthTv
        }
        binding.calendarView.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.calendarYear.text = "${month.year}"
                container.calendarMonth.text = "${month.yearMonth.month.name.toLowerCase().capitalize()}"
                container.calendarMonth.setOnClickListener{}
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

    }

//    private fun scrollFinsh() {
//        val bottomSheetBehavior = BottomSheetBehavior.from(binding.finishNs)
//        bottomSheetBehavior.setGestureInsetBottomIgnored(true)
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//
//        bottomSheetBehavior.addBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                if (newState == STATE_HIDDEN) {
//                    finish()
//                }
//            }
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//            }
//
//        })
//    }

}

