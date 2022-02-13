package com.eight.collection.ui.main.month

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.eight.collection.R
import com.eight.collection.databinding.CalendarDateBinding
import com.eight.collection.databinding.CalendarYearMonthHeaderBinding
import com.eight.collection.databinding.FragmentMonthBinding
import com.eight.collection.ui.BaseFragment
import com.eight.collection.ui.finish.FinishActivity
import com.eight.collection.ui.main.setting.SettingActivity
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.DayOfWeek
import java.time.YearMonth
import java.util.ArrayList

class MonthFragment(): BaseFragment<FragmentMonthBinding>(FragmentMonthBinding::inflate) {

    private  var monthDatas = ArrayList<Month>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initAfterBinding() {
        startMyLook()
        startWrite()
        startSetting()

        //더미 데이터
        monthDatas.apply {
            add(Month(5)) //1
            add(Month(4)) //2
            add(Month(4)) //3
            add(Month(0)) //4
            add(Month(1)) //5
            add(Month(0)) //6
            add(Month(4)) //7
            add(Month(3)) //8
            add(Month(4)) //9
            add(Month(5)) //10
            add(Month(0)) //11
            add(Month(5)) //12
            add(Month(0)) //13
            add(Month(0)) //14
            add(Month(0)) //15
            add(Month(0)) //16
            add(Month(0)) //17
            add(Month(0)) //18
            add(Month(0)) //19
            add(Month(0)) //20
            add(Month(0)) //1
            add(Month(0)) //2
            add(Month(0)) //1
            add(Month(0)) //2
            add(Month(0)) //1
            add(Month(0)) //2
            add(Month(0)) //1
            add(Month(0)) //2
            add(Month(0)) //1
            add(Month(0)) //2
            add(Month(0)) //1
        }

        binding.monthBtnSettingIv.bringToFront()
        binding.monthBtnWriteIv.bringToFront()

        class DayViewContainer(view: View) : ViewContainer(view) {
            val calendarDay = CalendarDateBinding.bind(view).calendarDayTv
            val calendarCell = CalendarDateBinding.bind(view).dateCell
            val rankPoint = CalendarDateBinding.bind(view).calendarRankIv
        }


        //day cell 크기 조정
        val dm = DisplayMetrics()
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)
        binding.calendarView.apply {
            val dayWidth = dm.widthPixels / 7
            val dayHeight = (dayWidth * 1.8).toInt()
            daySize = com.kizitonwose.calendarview.utils.Size(dayWidth, dayHeight)
        }

        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {

            override fun create(view: View) = DayViewContainer(view)

            @RequiresApi(Build.VERSION_CODES.O)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.calendarDay.text = day.date.dayOfMonth.toString()
                container.calendarCell.setOnClickListener{
                    startActivity(Intent(activity, FinishActivity::class.java))
                }
                //container.calendarDate.text= day.date.month.toString()
                if (day.owner == DayOwner.THIS_MONTH) {

                    val month = monthDatas[day.date.dayOfMonth - 1 ]

                    when(month.point){
                        1 -> container.rankPoint.setImageResource(R.drawable.calendar_rank_1_on)
                        2 -> container.rankPoint.setImageResource(R.drawable.calendar_rank_2_on)
                        3 -> container.rankPoint.setImageResource(R.drawable.calendar_rank_3_on)
                        4 -> container.rankPoint.setImageResource(R.drawable.calendar_rank_4_on)
                        5 -> container.rankPoint.setImageResource(R.drawable.calendar_rank_5_on)
                        else -> container.rankPoint.setImageResource(0)
                    }
                    container.calendarDay.setTextColor(Color.BLACK)
                } else {
                    container.calendarDay.setTextColor(Color.LTGRAY)
                    //container.rankPoint.setImageResource(R.drawable.calendar_rank_5_off)
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
        binding.calendarView.scrollToMonth(currentMonth)


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
            Navigation.findNavController(it).navigate(R.id.MyLookFragment)
        }

    }

}