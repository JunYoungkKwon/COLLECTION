package com.eight.collection.ui.main.week

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import com.eight.collection.R
import com.eight.collection.databinding.CalendarDateBinding
import com.eight.collection.databinding.CalendarYearMonthHeaderBinding
import com.eight.collection.databinding.FragmentWeekBinding
import com.eight.collection.ui.BaseFragment
import com.eight.collection.ui.main.month.Month
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.DayOfWeek
import java.time.YearMonth
import com.eight.collection.ui.main.setting.SettingActivity
import com.eight.collection.ui.writing.first.WritefirstActivity
import java.time.LocalDate
import android.widget.Toast
import java.util.*


class WeekFragment(): BaseFragment<FragmentWeekBinding>(FragmentWeekBinding::inflate){

    private  var monthDatas = ArrayList<Month>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initAfterBinding() {
        startMyLook()
        startWriteFirst()
        startSetting()
        binding.weekBtnSettingIv.bringToFront()

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
            add(Month(0)) //21
            add(Month(0)) //22
            add(Month(0)) //23
            add(Month(0)) //24
            add(Month(0)) //25
            add(Month(0)) //26
            add(Month(0)) //27
            add(Month(0)) //28
            add(Month(0)) //29
            add(Month(0)) //30
            add(Month(0)) //31
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            val calendarDay = CalendarDateBinding.bind(view).calendarDayTv
            val rankPoint = CalendarDateBinding.bind(view).calendarRankIv
            val calendarCell = CalendarDateBinding.bind(view).dateCell
            val todayHighlight = CalendarDateBinding.bind(view).calendarTodayView
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
                container.calendarCell.setOnClickListener{

                }

                if (day.owner == DayOwner.THIS_MONTH) {
                    val currentDay = LocalDate.now()
                    if (currentDay == day.date){
                        container.todayHighlight.visibility = View.VISIBLE
                    }
                    else{
                        container.todayHighlight.visibility = View.GONE
                    }


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
        binding.calendarView.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.calendarYear.text = "${month.year}"
                container.calendarMonth.text = "${month.yearMonth.month.name.toLowerCase().capitalize()}"
                container.calendarYear.setOnClickListener{
                    view?.let { Navigation.findNavController(it).navigate(R.id.datePickerActivity) }
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
        val currentDay = LocalDate.now()
        binding.calendarView.setup(firstMonth, lastMonth, daysOfWeek.first())
        //binding.calendarView.scrollToMonth(currentMonth)
        binding.calendarView.scrollToDate(currentDay)

        Log.d("currentDay",currentDay.toString())
        var diaryList = mutableList()
        val diaryRVAdapter = DiaryRVAdapter(diaryList)
        binding.weekDiaryRecyclerView.adapter = diaryRVAdapter

        diaryRVAdapter.setMyitemClickListener(object : DiaryRVAdapter.MyitemClickListener{

            override fun onRemoveAlbum(position: Int) {
                clickOption(position)
            }

            private fun clickOption(position: Int) {
                val popupMenu = PopupMenu(activity, binding.weekDiaryRecyclerView[position].findViewById(R.id.item_diary_edit_iv))
                popupMenu.inflate(R.menu.menu_week_option)
                popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        when (item?.itemId) {
                            R.id.menu_item_edit -> {
                                startActivity(Intent(activity, WritefirstActivity::class.java))
                                return true
                            }
                            R.id.menu_item_delete -> {
                                diaryRVAdapter.removeItem(position)
                                return true
                            }
                        }
                        return false
                    }
                })
                popupMenu.show()
            }
        })

    }

    private fun mutableList(): MutableList<Diary> {
        var diaryList = mutableListOf(
            Diary(R.drawable.ic_diary_point_4,"2022/02/07", R.drawable.example_0207, 4,
                mutableListOf(
                    Mood("카페"),
                    Mood("매우추움"),
                    Mood("친구"),
                ), mutableListOf(
                    Top("코트", "#888888"),
                    Top("니트", "#000000"),
                    Top("목폴라", "#000000"),
                ), mutableListOf(
                    Bottom("슬랙스", "#273e88"),
                ), mutableListOf(
                    Shoes("구두", "#000000"),
                ), mutableListOf(
                    Etc("모자", "#000000"),
                    Etc("크로스백", "#000000"),
                )
            ),

            Diary(
                R.drawable.ic_diary_point_3,"2022/02/08", R.drawable.example_0208, 1,
                mutableListOf(
                    Mood("핫플레이스"),
                    Mood("적당함"),
                    Mood("친구"),
                    Mood("선생님"),
                ), mutableListOf(
                    Top("패딩", "#888888"),
                    Top("후드티", "#d60f0f"),
                ), mutableListOf(
                    Bottom("슬랙스", "#000000"),
                ), mutableListOf(
                    Shoes("스니커즈", "#FFFFFF"),
                ), mutableListOf(
                    Etc("모자", "#000000"),
                    Etc("크로스백", "#000000"),
                )
            ),

            Diary(
                R.drawable.ic_diary_point_4,"2022/02/09", R.drawable.example_0209, 2,
                mutableListOf(
                    Mood("결혼식장"), Mood("적당함"), Mood("비"), Mood("가족"), Mood("동료"),
                ), mutableListOf(
                    Top("코트", "#f5f5dc"), Top("가디건", "#000000"),
                ), mutableListOf(
                    Bottom("미니스커트", "#71a238"),
                ), mutableListOf(
                    Shoes("로퍼", "#f5f5dc"),
                ), mutableListOf(
                    Etc("스카프", "#273e88"),
                    Etc("숄더백", "#f5f5dc"),
                )
            ),

            Diary(
                R.drawable.ic_diary_point_5,"2022/02/10", R.drawable.example_0210, 2,
                mutableListOf(
                    Mood("휴양지"), Mood("적당함"), Mood("비"), Mood("애인"),
                ), mutableListOf(
                    Top("티셔츠", "#FFFFFF"), Top("나시", "#FFFFFF"),
                ), mutableListOf(
                    Bottom("청바지", "#273e88"),
                ), mutableListOf(
                    Shoes("단화", "#000000"),
                ), mutableListOf(
                    Etc("주얼리", "#888888"),Etc("시계", "#74461f"),
                )
            ),

            Diary(
                R.drawable.ic_diary_point_5,"2022/02/12", R.drawable.example_0213_1, 3,
                mutableListOf(
                    Mood("핫플레이스"), Mood("매우추움"),Mood("애인"),
                ), mutableListOf(
                    Top("크롭티", "#FFFFFF"),
                ), mutableListOf(
                    Bottom("데님팬츠", "#273e88"),
                ), mutableListOf(
                    Shoes("스니커즈", "#FFFFFF"),
                ), mutableListOf(
                    Etc("주얼리", "#888888"),
                )
            ),
        )
        return diaryList


    }
    private fun startDatePicker() {
        startActivity(Intent(activity, DatePickerFragment::class.java))
//        binding.weekBtnWriteIv.setOnClickListener {
//            startActivity(Intent(activity, DatePickerActivity::class.java))
//        }
    }

    private fun startSetting() {
        binding.weekBtnSettingIv.setOnClickListener {
            startActivity(Intent(activity, SettingActivity::class.java))
        }
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

}
