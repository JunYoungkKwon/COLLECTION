package com.eight.collection.ui.main.week

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.eight.collection.R
import com.eight.collection.databinding.CalendarDateBinding
import com.eight.collection.databinding.CalendarYearMonthHeaderBinding
import com.eight.collection.databinding.FragmentWeekBinding
import com.eight.collection.ui.BaseFragment
import com.eight.collection.ui.main.MainActivity
import com.eight.collection.ui.main.lookpoint.MyLook
import com.eight.collection.ui.main.lookpoint.MyLookRVAdapter
import com.eight.collection.ui.main.lookpoint.Photo
import com.eight.collection.ui.writing.WritefirstActivity
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.DayOfWeek
import java.time.YearMonth
import java.util.ArrayList


class WeekFragment(): BaseFragment<FragmentWeekBinding>(FragmentWeekBinding::inflate) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initAfterBinding() {
        setUpRecyclerView()
        startMyLook()
        startWriteFirst()

        class DayViewContainer(view: View) : ViewContainer(view) {
            val calendarDay = CalendarDateBinding.bind(view).calendarDayTv
            val rankPoint = CalendarDateBinding.bind(view).calendarRankIv
        }

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


                if (day.owner == DayOwner.THIS_MONTH) {
                    container.calendarDay.setTextColor(Color.BLACK)
                    container.rankPoint.setImageResource(R.drawable.calendar_rank_5_on)
                } else {
                    container.calendarDay.setTextColor(Color.LTGRAY)
                    container.rankPoint.setImageResource(R.drawable.calendar_rank_5_off)
                }
            }
        }


        class MonthViewContainer(view: View) : ViewContainer(view) {
            val calendarMonth = CalendarYearMonthHeaderBinding.bind(view).calendarMonthTv
            val calendarYear= CalendarYearMonthHeaderBinding.bind(view).calendarYearTv

        }
        binding.calendarView.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
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

    private fun startWriteFirst() {
        binding.weekBtnWriteIv.setOnClickListener {
            startActivity(Intent(activity, WritefirstActivity::class.java))
        }
    }

    private fun startMyLook() {
        binding.weekBtnRankIv.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.MyLookFragment)
        }

    }

    private fun setUpRecyclerView() {
        var diaryList = mutableListOf(
            Diary(R.drawable.example1,1, mutableListOf(
                Mood( "눈"), Mood( "화창"), Mood( "화창"), Mood( "화창"), Mood( "화창"), Mood( "화창"), Mood( "화창"), Mood( "화창")
            ), mutableListOf(
                Top( "티셔츠", "#FFFFFF"), Top( "티셔츠", "#FFFFFF"), Top( "티셔츠", "#FFFFFF")
            ), mutableListOf(
                Bottom( "티셔츠", "#FFFFFF"), Bottom( "티셔츠2", "#FFFF00"), Bottom( "티셔츠3", "#FFFFFF"), Bottom( "티셔츠4", "#FFFFFF")
            ), mutableListOf(
                Shoes( "티셔츠", "#FFFFFF")
            ), mutableListOf(
                Etc( "티셔츠", "#FFFFFF")
            )
            ),

            Diary(R.drawable.example2,2, mutableListOf(
                Mood( "눈"), Mood( "화창"), Mood( "화창"), Mood( "화창"), Mood( "화창"), Mood( "화창"), Mood( "화창"), Mood( "화창")
            ), mutableListOf(
                Top( "티셔츠", "#FFFFFF")
            ), mutableListOf(
                Bottom( "티셔츠", "#FFFFFF")
            ), mutableListOf(
                Shoes( "티셔츠", "#FFFFFF")
            ), mutableListOf(
                Etc( "티셔츠", "#FFFFFF")
            )
            ),

            Diary(R.drawable.example3,3, mutableListOf(
                Mood( "눈"), Mood( "화창")
            ), mutableListOf(
                Top( "티셔츠", "#FFFFFF")
            ), mutableListOf(
                Bottom( "티셔츠", "#FFFFFF")
            ), mutableListOf(
                Shoes( "티셔츠", "#FFFFFF")
            ), mutableListOf(
                Etc( "티셔츠", "#FFFFFF")
            )
            ),

            Diary(R.drawable.example4,4, mutableListOf(
                Mood( "눈"), Mood( "화창"), Mood( "화창"), Mood( "화창"), Mood( "화창")
            ), mutableListOf(
                Top( "티셔츠", "#FFFFFF")
            ), mutableListOf(
                Bottom( "티셔츠", "#FFFFFF")
            ), mutableListOf(
                Shoes( "티셔츠", "#FFFFFF")
            ), mutableListOf(
                Etc( "티셔츠", "#FFFFFF")
            )
            ),

            Diary(0,5, mutableListOf(
                Mood( "눈"), Mood( "화창"), Mood( "화창"), Mood( "화창")
            ), mutableListOf(
                Top( "티셔츠", "#FFFFFF")
            ), mutableListOf(
                Bottom( "티셔츠", "#FFFFFF"),Bottom( "티셔츠", "#FFFFFF"),Bottom( "티셔츠", "#FFFFFF"),Bottom( "티셔츠", "#FFFFFF"),Bottom( "티셔츠", "#FFFFFF")
            ), mutableListOf(
                Shoes( "티셔츠", "#FFFFFF")
            ), mutableListOf(
                Etc( "티셔츠", "#FFFFFF"),Etc( "티셔츠", "#FFFFFF"),Etc( "티셔츠", "#FFFFFF"),Etc( "티셔츠", "#FFFFFF"),Etc( "티셔츠", "#FFFFFF"),Etc( "티셔츠", "#FFFFFF"),Etc( "티셔츠", "#FFFFFF")
            )
            ),
        )
        binding.weekDiaryRecyclerView.adapter = DiaryRVAdapter(diaryList)
    }
}
