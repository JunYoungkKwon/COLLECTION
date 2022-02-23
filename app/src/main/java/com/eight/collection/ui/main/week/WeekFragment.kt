package com.eight.collection.ui.main.week

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.navigation.Navigation
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.R
import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.remote.calendar.CalendarService
import com.eight.collection.data.remote.weekly.WeeklyService
import com.eight.collection.databinding.CalendarDateBinding
import com.eight.collection.databinding.CalendarYearMonthHeaderBinding
import com.eight.collection.databinding.FragmentWeekBinding
import com.eight.collection.ui.BaseFragment
import com.eight.collection.ui.main.month.MonthView
import com.eight.collection.ui.main.setting.SettingActivity
import com.eight.collection.ui.writing.first.WritefirstActivity
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


class WeekFragment(): BaseFragment<FragmentWeekBinding>(FragmentWeekBinding::inflate),MonthView, WeeklyView {

    private  lateinit var diaryRVAdapter: DiaryRVAdapter

    var firstdate: LocalDate? = null
    var lastdate: LocalDate? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getMonth()
        initWeeklyRV()
        binding.calendarView.monthScrollListener = {
            if (binding.calendarView.maxRowCount == 1) {
                val weekFields = WeekFields.of(DayOfWeek.SUNDAY, 1)
                val weekOfMonth: TemporalField = weekFields.weekOfMonth()

                firstdate = it.weekDays.first().first().date
                lastdate = it.weekDays.last().last().date
                getWeek()


            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initAfterBinding() {
        startMyLook()
        startWriteFirst()
        startSetting()

        binding.weekBtnSettingIv.bringToFront()

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
            val rankPoint = CalendarDateBinding.bind(view).calendarRankIv
            val calendarCell = CalendarDateBinding.bind(view).dateCell
            val todayHighlight = CalendarDateBinding.bind(view).calendarTodayView
        }

        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {

            override fun create(view: View) = DayViewContainer(view)

            @RequiresApi(Build.VERSION_CODES.O)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.calendarDay.text = day.date.dayOfMonth.toString()
                container.calendarCell.setOnClickListener{

                }

                //container.calendarDate.text= day.date.month.toString()
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
                        val locadate:LocalDate = date.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        if (locadate == day.date){
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
        binding.calendarView.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.calendarYear.text = "${month.year}"
                container.calendarMonth.text = "${month.yearMonth.month.name.toLowerCase().capitalize()}"
                container.calendarYear.setOnClickListener{
                    view?.let { Navigation.findNavController(it).navigate(R.id.datePickerActivity) }
                }

//                binding.calendarView.monthScrollListener = {
//                    if (binding.calendarView.maxRowCount == 1) {
//                        val weekFields = WeekFields.of(DayOfWeek.SUNDAY, 1)
//                        val weekOfMonth: TemporalField = weekFields.weekOfMonth()
//
//                        firstdatetest = it.weekDays.first().first().date
//                        Log.d("Test2", firstdatetest.toString())
//
//                        var firstDate = it.weekDays.first().first().date
//                        var lastDate = it.weekDays.last().last().date
//
////                        DateCheck.apply {
////                            add(DateCheck(firstDate, lastDate))
////                        }
//
////                        val lastDate = it.weekDays.last().last().date
////                        val firstDate = it.weekDays.first().first().date
//
//
//                    }
//
//                }

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
        binding.calendarView.scrollToDate(currentDay)

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

    private fun getWeek(){
        WeeklyService.getWeek(this)
    }

    private fun initWeeklyRV(){
        diaryRVAdapter = DiaryRVAdapter(requireContext())
        binding.weekDiaryRecyclerView.adapter = diaryRVAdapter
        diaryRVAdapter.setMyitemClickListener(object : DiaryRVAdapter.MyitemClickListener{

            override fun onRemoveDiary(position: Int) {
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
                                Log.d(TAG, "인덱스" + position)
                            }
                            R.id.menu_item_delete -> {
                                diaryRVAdapter.removeItem(position)
                                Log.d(TAG, "인덱스" + position)
                            }
                        }
                        return false
                    }
                })
                popupMenu.show()
            }
        })
    }

    override fun onWeeklyLoading() {
        Log.d("Week/Data/ERROR", "loading")
    }

    override fun onWeeklySuccess(weekly: MutableList<Diary>) {
        var indexarraylist = ArrayList<Int>()
        for(i in 0 .. weekly.size-1 step (1)){
            val index = i
            val now1: LocalDate = LocalDate.now()
            val now =now1.minusDays(20)
            val date:Date = weekly[i].date
            val localdate:LocalDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            if(localdate >= firstdate){
                if(localdate <= lastdate ){
                    val index = i
                    indexarraylist.add(index)
                }
            }
        }
        if( indexarraylist.size != 0){
            val filterindex = indexarraylist.filterNotNull()
            val first = filterindex.get(0)
            val last = filterindex.get(filterindex.size -1) +1
            val sub_mulist = weekly.subList(first, last)
            diaryRVAdapter.addWeekly(sub_mulist)
            binding.weekDefault2Text.visibility= View.GONE
            binding.weekDefault1Text.visibility= View.GONE
            binding.weekDefaultIv.visibility= View.GONE
        }
        else{
            diaryRVAdapter.removeWeekly()
            binding.weekDefault2Text.visibility= View.VISIBLE
            binding.weekDefault1Text.visibility= View.VISIBLE
            binding.weekDefaultIv.visibility= View.VISIBLE
        }


    }

    override fun onWeeklyFailure(code: Int, message: String) {
        when (code) {
            4000,4005, 4020 -> {
                Log.d("Week/Data/ERROR", "error")
            }
            else -> {
                Log.d("Week/Server/ERROR", "error")
            }
        }
    }

}