package com.eight.collection.ui.main.week

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.eight.collection.R
import com.eight.collection.databinding.CalendarDateBinding
import com.eight.collection.databinding.CalendarYearMonthHeaderBinding
import com.eight.collection.databinding.FragmentWeekBinding
import com.eight.collection.ui.BaseFragment
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

    private  var diaryDatas = ArrayList<Diary>()
    private  var MoodDatas = ArrayList<Mood>()
    private  var BottomDatas = ArrayList<Bottom>()
    private  var TopDatas = ArrayList<Top>()
    private  var ShoesDatas = ArrayList<Shoes>()
    private  var EtcDatas = ArrayList<Etc>()



    @RequiresApi(Build.VERSION_CODES.O)
    override fun initAfterBinding() {

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

        //더미 데이터
        diaryDatas.apply {
            add(Diary( R.drawable.example1,1))
            add(Diary( R.drawable.example1,2))
            add(Diary( R.drawable.example1,333))
            add(Diary( R.drawable.example1,444))
            add(Diary( 0,55555))
        }
        MoodDatas.apply {
            add(Mood( "눈"))
            add(Mood( "매우추움"))
            add(Mood( "회사"))
            add(Mood( "화창"))
            add(Mood( "구름"))
            add(Mood( "구름1"))
            add(Mood( "구름2"))
            add(Mood( "매우추움"))
            add(Mood( "회사"))
            add(Mood( "화창"))
            add(Mood( "구름"))
            add(Mood( "구름1"))
            add(Mood( "구름2"))
        }

        TopDatas.apply {
            add(Top( "티셔츠", R.drawable.item_color_orange))
            add(Top( "와이셔츠", R.drawable.item_color_orange))
        }

        BottomDatas.apply {
            add(Bottom( "티셔츠", R.drawable.item_color_orange))
            add(Bottom( "와이셔츠", R.drawable.item_color_orange))
            add(Bottom( "티샤스", R.drawable.item_color_orange))
            add(Bottom( "티셔스2", R.drawable.item_color_orange))
        }

        ShoesDatas.apply {
            add(Shoes( "티셔츠", R.drawable.item_color_orange))
            add(Shoes( "와이셔츠", R.drawable.item_color_orange))
            add(Shoes( "티샤스", R.drawable.item_color_orange))
            add(Shoes( "티셔스2", R.drawable.item_color_orange))
            add(Shoes( "티샤스", R.drawable.item_color_orange))
            add(Shoes( "티셔스2", R.drawable.item_color_orange))
        }

        EtcDatas.apply {
            add(Etc( "티셔츠", R.drawable.item_color_orange))
            add(Etc( "와이셔츠", R.drawable.item_color_orange))
            add(Etc( "티샤스", R.drawable.item_color_orange))
            add(Etc( "티셔스2", R.drawable.item_color_orange))
            add(Etc( "티셔츠", R.drawable.item_color_orange))
            add(Etc( "와이셔츠", R.drawable.item_color_orange))
            add(Etc( "티샤스", R.drawable.item_color_orange))
            add(Etc( "티셔스2", R.drawable.item_color_orange))
        }
        //더미데이터와 어댑터 연결
        val diaryRVAdapter = DiaryRVAdapter(diaryDatas, MoodDatas,TopDatas, BottomDatas, ShoesDatas, EtcDatas)

        //리사이클러뷰와 어댑터 연결
        binding.weekDiaryRecyclerView.adapter = diaryRVAdapter
    }
}
