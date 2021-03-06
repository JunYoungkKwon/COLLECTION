package com.eight.collection.ui.main.week

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.utils.calendar
import com.eight.collection.R
import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.entities.Diary
import com.eight.collection.data.remote.calendar.CalendarService
import com.eight.collection.data.remote.setting.SettingService
import com.eight.collection.data.remote.weekly.WeeklyService
import com.eight.collection.databinding.CalendarDateBinding
import com.eight.collection.databinding.CalendarYearMonthHeaderBinding
import com.eight.collection.databinding.FragmentWeekBinding
import com.eight.collection.ui.BaseFragment
import com.eight.collection.ui.finish.FinishActivity
import com.eight.collection.ui.main.month.MonthView
import com.eight.collection.ui.main.mylook.MyLookActivity
import com.eight.collection.ui.main.setting.SettingActivity
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import kotlinx.coroutines.selects.select
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalField
import java.time.temporal.WeekFields
import java.util.*
import kotlin.collections.ArrayList


class WeekFragment(): BaseFragment<FragmentWeekBinding>(FragmentWeekBinding::inflate),MonthView, WeeklyView, DeleteView {


    private  lateinit var diaryRVAdapter: DiaryRVAdapter

    private var selectedDate: LocalDate? = null
    private var dateSave: MutableList<Diary>? = null
    private var moveToDate: LocalDate? = null
    private var seletDate: LocalDate? = null
    private var firstdate: LocalDate? = null
    private var lastdate: LocalDate? = null

    override fun onResume() {
        super.onResume()
//        val contentResolver = applicationContext.contentResolver
//
//        val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
//                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
//        contentResolver.takePersistableUriPermission(uri, takeFlags)
        getMonth()
//        initWeeklyRV()
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
        binding.weekBtnWriteIv.bringToFront()
        binding.weekBtnRankIv.bringToFront()

    }

    private fun deleteOOTD(date : String) {
        if (date != null) {
            SettingService.deleteOOTD(this, date)
        }
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
            startActivity(Intent(activity, MyLookActivity::class.java))
        }

    }
    private fun getMonth() {
        CalendarService.getMonth(this)
    }

    override fun onMonthLoading() {
        binding.loginLoadingInIv.visibility = View.VISIBLE
        binding.loginLoadingCircleIv.visibility = View.VISIBLE
        binding.loginLoadingBackgroundIv.visibility = View.VISIBLE
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
                diaryRVAdapter = DiaryRVAdapter(requireContext())
                binding.weekDiaryRecyclerView.adapter = diaryRVAdapter
                container.calendarDay.text = day.date.dayOfMonth.toString()
                container.calendarCell.setOnClickListener{
                    val currentSelection = selectedDate
                    if(container.rankPoint.drawable == null) {
                        var layoutInflater = LayoutInflater.from(context).inflate(R.layout.toast_custom,null)
                        var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                        text.text="?????? ???????????? ootd??? ???????????? ????????????."
                        var toast = Toast(context)
                        toast.view = layoutInflater
                        toast.setGravity(Gravity.BOTTOM, 0, 150)
                        toast.show()
                    }
                    else{

                        if (currentSelection == day.date) {
                            selectedDate = null
                        } else {
                            selectedDate = day.date
                            val smoothScroller: RecyclerView.SmoothScroller by lazy {
                                object : LinearSmoothScroller(context) {
                                    override fun getVerticalSnapPreference() = SNAP_TO_START
                                }
                            }

                            if(dateSave?.size == 1){
                                val date1: Date? = dateSave?.get(0)?.date
                                val localdate1: LocalDate? = date1?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                when(selectedDate){
                                    localdate1-> {
                                        smoothScroller.targetPosition = 0
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    else ->  binding.weekDiaryRecyclerView.smoothScrollToPosition(0)
                                }
                            }

                            if(dateSave?.size == 2){
                                val date1: Date? = dateSave?.get(0)?.date
                                val localdate1: LocalDate? = date1?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date2: Date? = dateSave?.get(1)?.date
                                val localdate2: LocalDate? = date2?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                when(selectedDate){
                                    localdate1-> {
                                        smoothScroller.targetPosition = 0
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    localdate2 ->  {
                                        smoothScroller.targetPosition = 1
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    else ->  binding.weekDiaryRecyclerView.smoothScrollToPosition(0)
                                }
                            }

                            if(dateSave?.size == 3){
                                val date1: Date? = dateSave?.get(0)?.date
                                val localdate1: LocalDate? = date1?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date2: Date? = dateSave?.get(1)?.date
                                val localdate2: LocalDate? = date2?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date3: Date? = dateSave?.get(2)?.date
                                val localdate3: LocalDate? = date3?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                when(selectedDate){
                                    localdate1-> {
                                        smoothScroller.targetPosition = 0
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    localdate2 ->  {
                                        smoothScroller.targetPosition = 1
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    localdate3  ->  {
                                        smoothScroller.targetPosition = 2
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    else ->  binding.weekDiaryRecyclerView.smoothScrollToPosition(0)
                                }
                            }



                            if(dateSave?.size == 4){
                                val date1: Date? = dateSave?.get(0)?.date
                                val localdate1: LocalDate? = date1?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date2: Date? = dateSave?.get(1)?.date
                                val localdate2: LocalDate? = date2?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date3: Date? = dateSave?.get(2)?.date
                                val localdate3: LocalDate? = date3?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date4: Date? = dateSave?.get(3)?.date
                                val localdate4: LocalDate? = date4?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                when(selectedDate){
                                    localdate1-> {
                                        smoothScroller.targetPosition = 0
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    localdate2 ->  {
                                        smoothScroller.targetPosition = 1
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    localdate3  ->  {
                                        smoothScroller.targetPosition = 2
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    localdate4  ->  {
                                        smoothScroller.targetPosition = 3
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    else ->  binding.weekDiaryRecyclerView.smoothScrollToPosition(0)
                                }
                            }

                            if(dateSave?.size == 5){
                                val date1: Date? = dateSave?.get(0)?.date
                                val localdate1: LocalDate? = date1?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date2: Date? = dateSave?.get(1)?.date
                                val localdate2: LocalDate? = date2?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date3: Date? = dateSave?.get(2)?.date
                                val localdate3: LocalDate? = date3?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date4: Date? = dateSave?.get(3)?.date
                                val localdate4: LocalDate? = date4?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date5: Date? = dateSave?.get(4)?.date
                                val localdate5: LocalDate? = date5?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                when(selectedDate){
                                    localdate1-> {
                                        smoothScroller.targetPosition = 0
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    localdate2 ->  {
                                        smoothScroller.targetPosition = 1
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    localdate3  ->  {
                                        smoothScroller.targetPosition = 2
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    localdate4  ->  {
                                        smoothScroller.targetPosition = 3
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }

                                    localdate5  ->  {
                                        smoothScroller.targetPosition = 4
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    else ->  binding.weekDiaryRecyclerView.smoothScrollToPosition(0)
                                }
                            }

                            if(dateSave?.size == 6){
                                val date1: Date? = dateSave?.get(0)?.date
                                val localdate1: LocalDate? = date1?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date2: Date? = dateSave?.get(1)?.date
                                val localdate2: LocalDate? = date2?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date3: Date? = dateSave?.get(2)?.date
                                val localdate3: LocalDate? = date3?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date4: Date? = dateSave?.get(3)?.date
                                val localdate4: LocalDate? = date4?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date5: Date? = dateSave?.get(4)?.date
                                val localdate5: LocalDate? = date5?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date6: Date? = dateSave?.get(5)?.date
                                val localdate6: LocalDate? = date6?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                when(selectedDate){
                                    localdate1-> {
                                        smoothScroller.targetPosition = 0
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    localdate2 ->  {
                                        smoothScroller.targetPosition = 1
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    localdate3  ->  {
                                        smoothScroller.targetPosition = 2
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    localdate4  ->  {
                                        smoothScroller.targetPosition = 3
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }

                                    localdate5  ->  {
                                        smoothScroller.targetPosition = 4
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }

                                    localdate6  ->  {
                                        smoothScroller.targetPosition = 5
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    else ->  binding.weekDiaryRecyclerView.smoothScrollToPosition(0)
                                }
                            }

                            if(dateSave?.size == 7){
                                val date1: Date? = dateSave?.get(0)?.date
                                val localdate1: LocalDate? = date1?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date2: Date? = dateSave?.get(1)?.date
                                val localdate2: LocalDate? = date2?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date3: Date? = dateSave?.get(2)?.date
                                val localdate3: LocalDate? = date3?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date4: Date? = dateSave?.get(3)?.date
                                val localdate4: LocalDate? = date4?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date5: Date? = dateSave?.get(4)?.date
                                val localdate5: LocalDate? = date5?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date6: Date? = dateSave?.get(5)?.date
                                val localdate6: LocalDate? = date6?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                val date7: Date? = dateSave?.get(6)?.date
                                val localdate7: LocalDate? = date7?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())
                                    ?.toLocalDate()

                                when(selectedDate){
                                    localdate1-> {
                                        smoothScroller.targetPosition = 0
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    localdate2 ->  {
                                        smoothScroller.targetPosition = 1
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    localdate3  ->  {
                                        smoothScroller.targetPosition = 2
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    localdate4  ->  {
                                        smoothScroller.targetPosition = 3
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }

                                    localdate5  ->  {
                                        smoothScroller.targetPosition = 4
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }

                                    localdate6  ->  {
                                        smoothScroller.targetPosition = 5
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }

                                    localdate7  ->  {
                                        smoothScroller.targetPosition = 6
                                        binding.weekDiaryRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                                    }
                                    else ->  binding.weekDiaryRecyclerView.smoothScrollToPosition(0)
                                }
                            }

                        }
                    }
                }


                diaryRVAdapter.setMyitemClickListener(object : DiaryRVAdapter.MyitemClickListener{
                    override fun onRemoveDiary(view: View, position: Int) {

                        val powerMenu = PowerMenu.Builder(requireContext())
                            .addItem(PowerMenuItem("????????????", false))
                            .addItem(PowerMenuItem("????????????", false))
                            .setMenuRadius(15f)
                            .setDivider(ColorDrawable(ContextCompat.getColor(requireContext(), R.color.pinkish_grey)))
                            .setDividerHeight(1)
                            .setShowBackground(false)
                            .setMenuShadow(20f)
                            .setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_taupe))
                            .setTextGravity(Gravity.CENTER)
                            .setTextTypeface(Typeface.create("@font/suit_regular", Typeface.NORMAL))
                            .setMenuColor(Color.WHITE)
                            .build()

                        val onMenuItemClickListener = OnMenuItemClickListener<PowerMenuItem> { position1, item ->
                            when(item.title){
                                "????????????" -> {
                                    val date = dateSave?.get(position)?.date
                                    var localdate: LocalDate? = date?.toInstant()
                                        ?.atZone(ZoneId.systemDefault())
                                        ?.toLocalDate()
                                    moveToDate = localdate
                                    val formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                    val deleteDate: String? = localdate?.format(formatters)
                                    dateSave?.removeAt(position)
                                    val intent2 = Intent(context, WritefirstActivity::class.java)
                                    intent2.putExtra("date", deleteDate)
                                    startActivity(intent2)
                                }
                                "????????????" -> {
                                    diaryRVAdapter.removeItem(position)
                                    val date = dateSave?.get(position)?.date
                                    var localdate: LocalDate? = date?.toInstant()
                                        ?.atZone(ZoneId.systemDefault())
                                        ?.toLocalDate()
                                    moveToDate = localdate
                                    val formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                    val deleteDate: String? = localdate?.format(formatters)
                                    dateSave?.removeAt(position)
                                    if (deleteDate != null) {
                                        deleteOOTD(deleteDate)
                                    }
                                }
                            }
                            powerMenu.selectedPosition = position
                            powerMenu.dismiss()
                        }
                        powerMenu.onMenuItemClickListener = onMenuItemClickListener
                        powerMenu.showAsDropDown(view, -30, -30)
                    }

                    override fun onStartFinish(position: Int) {
                        val date = dateSave?.get(position)?.date
                        var localdate: LocalDate? = date?.toInstant()
                            ?.atZone(ZoneId.systemDefault())
                            ?.toLocalDate()
                        moveToDate = localdate
                        val formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        val moveDate: String? = localdate?.format(formatters)

                        val intent = Intent(context,FinishActivity::class.java)
                        intent.apply {
                            this.putExtra("date",moveDate)
                        }
                        startActivity(intent)

                    }
                })

                if (day.owner == DayOwner.THIS_MONTH) {

                    val currentDay = LocalDate.now()

                    if (currentDay == day.date){
                        container.todayHighlight.visibility = View.VISIBLE
                    }else{
                        container.todayHighlight.visibility = View.INVISIBLE
                    }

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
                    container.calendarDay.setTextColor(Color.BLACK)
                } else {
                    container.todayHighlight.visibility = View.INVISIBLE

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
        binding.calendarView.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.calendarYear.text = "${month.year}"
                container.calendarMonth.text = "${month.yearMonth.month.name.toLowerCase().capitalize()}"
                container.calendarYear.setOnClickListener(android.view.View.OnClickListener { v ->
                    val datepcikerdlg: DatePickerFragment = DatePickerFragment()
                    datepcikerdlg.setFragmentInterfacer(object : DatePickerFragment.MyFragmentInterfacer {
                        //??????????????? ??? ????????????
                        override fun onButtonClick(getSeletDate: String?) {
                            seletDate = LocalDate.parse(getSeletDate, DateTimeFormatter.ISO_DATE)
                            binding.calendarView.scrollToDate(seletDate!!)
                        }
                    })
                    val fragmanager = this@WeekFragment.fragmentManager
                    if (fragmanager != null) {
                        datepcikerdlg.show(fragmanager, "name")
                    }

                })

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

        Log.d("Test/1",moveToDate.toString())
        if (moveToDate == null && seletDate == null){
            Log.d("Test/2",moveToDate.toString())
            binding.calendarView.scrollToDate(currentDay)
        }else{
            if (moveToDate != null){
                Log.d("Test/3",moveToDate.toString())
                binding.calendarView.scrollToDate(moveToDate!!)
            }
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

    private fun getWeek(){
        WeeklyService.getWeek(this)
    }

    override fun onWeeklyLoading() {}

    override fun onWeeklySuccess(weekly: MutableList<Diary>) {

        var indexarraylist = ArrayList<Int>()
        for(i in 0 .. weekly.size-1 step (1)){
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
            dateSave = sub_mulist

            diaryRVAdapter.addWeekly(sub_mulist)
            binding.weekDefault2Text.visibility= View.GONE
            binding.weekDefault1Text.visibility= View.GONE
            binding.weekDefaultIv.visibility= View.GONE
            binding.itemTopLineView.visibility= View.GONE
        }
        else{
            diaryRVAdapter.removeWeekly()
            binding.weekDefault2Text.visibility= View.VISIBLE
            binding.weekDefault1Text.visibility= View.VISIBLE
            binding.weekDefaultIv.visibility= View.VISIBLE
            binding.itemTopLineView.visibility= View.VISIBLE
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

    override fun onDeleteLoading() {
        binding.loginLoadingInIv.visibility = View.VISIBLE
        binding.loginLoadingCircleIv.visibility = View.VISIBLE
        binding.loginLoadingBackgroundIv.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        binding.loginLoadingCircleIv.startAnimation(animation)
        binding.loginDimBackground.visibility = View.VISIBLE
    }

    override fun onDeleteSuccess() {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE
        getMonth()
    }

    override fun onDeleteFailure(code: Int, message: String) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        when (code) {
            2000, 2001, 2002 -> {
                Log.d("Week/Jwt/ERROR", "error")
            }
            3022, 3023, 3025, 3026, 3044-> {
                Log.d("Week/Data/ERROR", "error")
            }
            4001, 4008-> {
                Log.d("Week/Date/ERROR", "error")
            }
            else -> {
                Log.d("Week/SEVER/ERROR", "error")
            }
        }
    }
}

