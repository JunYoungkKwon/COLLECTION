package com.eight.collection.ui.main.week

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.navigation.Navigation
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.DatePicker
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
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.eight.collection.ui.main.setting.SettingActivity
import java.util.*





class WeekFragment(): BaseFragment<FragmentWeekBinding>(FragmentWeekBinding::inflate){


    @RequiresApi(Build.VERSION_CODES.O)
    override fun initAfterBinding() {
        startMyLook()
        startWriteFirst()
        startSetting()
        binding.weekBtnSettingIv.bringToFront()
        view?.let { Navigation.findNavController(it).navigate(R.id.datePickerActivity) }
        //startDatePicker()

//        val mcurrentTime = Calendar.getInstance()
//        val year = mcurrentTime.get(Calendar.YEAR)
//        val month = mcurrentTime.get(Calendar.MONTH)
//        val day = mcurrentTime.get(Calendar.DAY_OF_MONTH)
//
//        val datePicker = activity?.let {
//            DatePickerDialog(it, R.style.MyDatePickerStyle, object : DatePickerDialog.OnDateSetListener {
//                override fun onDateSet(
//                    view: android.widget.DatePicker?,
//                    year: Int,
//                    month: Int,
//                    dayOfMonth: Int
//                ) {
//                    //selectedDate.setText(String.format("%d / %d / %d", dayOfMonth, month + 1, year))
//                }
//            }, year, month, day)
//        }
//        if (datePicker != null) {
//            datePicker.show()
//        }

//        val  listener: OnSelectDateListener = object : OnSelectDateListener {
//            override fun onSelect(calendar: List<Calendar>) {
//
//            }
//        }
//
//        val oneDayBuilder = DatePickerBuilder(this.requireContext(), listener)
//            .pickerType(CalendarView.ONE_DAY_PICKER)
//            .headerLabelColor(R.color.terracota)
//            .selectionColor(R.color.daysLabelColor)
//            .dialogButtonsColor(android.R.color.holo_green_dark)
//            .disabledDaysLabelsColor(android.R.color.holo_purple)
//            .headerVisibility(View.VISIBLE)
//            .abbreviationsBarVisibility(View.GONE)
//            .abbreviationsLabelsColor(R.color.terracota)
//            .headerColor(R.color.bottom_navi)
//            .daysLabelsColor(R.color.black)
//            .pagesColor(R.color.bottom_navi
//            //.typefaceSrc(R.font.roboto_light)
//            .previousButtonSrc(R.drawable.ic_datepicker_previous)
//            .forwardButtonSrc(R.drawable.ic_datepicker_next)
//
//
//
//
//        val oneDayPicker = oneDayBuilder.build()
//        oneDayPicker.show()


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
                container.calendarYear.setOnClickListener{

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
        binding.calendarView.scrollToMonth(currentMonth)

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
            Diary(R.drawable.ic_diary_point,"2022/01/02", R.drawable.example1, 1,
                mutableListOf(
                    Mood("눈"),
                    Mood("화창"),
                    Mood("화창"),
                    Mood("화창"),
                    Mood("화창"),
                    Mood("화창"),
                    Mood("화창"),
                    Mood("화창")
                ), mutableListOf(
                    Top("티셔츠", "#FFFFFF"),
                ), mutableListOf(
                    Bottom("티셔츠", "#FFFFFF"),
                    Bottom("티셔츠2", "#FFFF00"),
                    Bottom("티셔츠3", "#FFFFFF"),
                    Bottom("티셔츠4", "#FFFFFF")
                ), mutableListOf(
                    Shoes("티셔츠", "#FFFFFF")
                ), mutableListOf(
                    Etc("티셔츠", "#FFFFFF")
                )
            ),

            Diary(
                R.drawable.ic_diary_point,"2022/01/03", R.drawable.example1, 1,
                mutableListOf(
                    Mood("눈"),
                    Mood("화창"),
                    Mood("화창"),
                    Mood("화창"),
                    Mood("화창"),
                    Mood("화창"),
                    Mood("화창"),
                    Mood("화창")
                ), mutableListOf(
                    Top("티셔츠", "#FFFFFF")
                ), mutableListOf(
                    Bottom("티셔츠", "#FFFFFF")
                ), mutableListOf(
                    Shoes("티셔츠", "#FFFFFF")
                ), mutableListOf(
                    Etc("티셔츠", "#FFFFFF")
                )
            ),

            Diary(
                R.drawable.ic_diary_point,"2022/01/04", R.drawable.example1, 8,
                mutableListOf(
                    Mood("눈"), Mood("화창")
                ), mutableListOf(
                    Top("티셔츠", "#FFFFFF")
                ), mutableListOf(
                    Bottom("티셔츠", "#FFFFFF")
                ), mutableListOf(
                    Shoes("티셔츠", "#FFFFFF")
                ), mutableListOf(
                    Etc("티셔츠", "#FFFFFF")
                )
            ),

            Diary(
                R.drawable.ic_diary_point,"2022/01/06", R.drawable.example1, 12,
                mutableListOf(
                    Mood("눈"), Mood("화창"), Mood("화창"), Mood("화창"), Mood("화창")
                ), mutableListOf(
                    Top("티셔츠", "#FFFFFF")
                ), mutableListOf(
                    Bottom("티셔츠", "#FFFFFF")
                ), mutableListOf(
                    Shoes("티셔츠", "#FFFFFF")
                ), mutableListOf(
                    Etc("티셔츠", "#FFFFFF")
                )
            ),

            Diary(
                R.drawable.ic_diary_point,"2022/01/06", R.drawable.example1, 33,
                mutableListOf(
                    Mood("눈"), Mood("화창"), Mood("화창"), Mood("화창")
                ), mutableListOf(
                    Top("티셔츠", "#FFFFFF")
                ), mutableListOf(
                    Bottom("티셔츠", "#FFFFFF"),
                    Bottom("티셔츠", "#FFFFFF"),
                    Bottom("티셔츠", "#FFFFFF"),
                    Bottom("티셔츠", "#FFFFFF"),
                    Bottom("티셔츠", "#FFFFFF")
                ), mutableListOf(
                    Shoes("티셔츠", "#FFFFFF")
                ), mutableListOf(
                    Etc("티셔츠", "#FFFFFF"),
                    Etc("티셔츠", "#FFFFFF"),
                    Etc("티셔츠", "#FFFFFF"),
                    Etc("티셔츠", "#FFFFFF"),
                    Etc("티셔츠", "#FFFFFF"),
                    Etc("티셔츠", "#FFFFFF"),
                    Etc("티셔츠", "#FFFFFF")
                )
            ),
        )
        return diaryList


    }
    private fun startDatePicker() {
        startActivity(Intent(activity, DatePickerActivity::class.java))
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
