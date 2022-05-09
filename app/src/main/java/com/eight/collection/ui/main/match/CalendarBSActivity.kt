//package com.eight.collection.ui.main.match
//
//import android.content.Context
//import android.content.Intent
//import android.graphics.Color
//import android.graphics.Typeface
//import android.graphics.drawable.ColorDrawable
//import android.graphics.drawable.GradientDrawable
//import android.os.Build
//import android.os.Bundle
//import android.util.DisplayMetrics
//import android.util.Log
//import android.util.TypedValue
//import android.view.*
//import android.view.animation.AnimationUtils
//import android.widget.TextView
//import android.widget.Toast
//import androidx.annotation.RequiresApi
//import androidx.appcompat.app.AppCompatActivity
//import androidx.constraintlayout.widget.ConstraintSet
//import androidx.core.content.ContentProviderCompat.requireContext
//import androidx.core.content.ContextCompat
//import androidx.core.view.children
//import androidx.core.view.marginTop
//import androidx.core.widget.NestedScrollView
//import com.eight.collection.R
//import com.eight.collection.data.entities.Cloth
//import com.eight.collection.data.entities.Diary
//import com.eight.collection.data.entities.Photo
//import com.eight.collection.data.remote.finish.Finish
//import com.eight.collection.data.remote.finish.FinishService
//import com.eight.collection.data.remote.setting.SettingService
//import com.eight.collection.databinding.*
//import com.eight.collection.ui.BaseActivity
//import com.eight.collection.ui.finish.FinishActivity
//import com.eight.collection.ui.introduce.IntroduceDialog
//import com.eight.collection.ui.main.mylook.MyLookDetailActivity
//import com.eight.collection.ui.main.mylook.MyLookOOTD
//import com.eight.collection.ui.main.mylook.MyLookRVAdapter
//import com.eight.collection.ui.main.setting.SettingActivity
//import com.eight.collection.ui.main.week.DeleteView
//import com.eight.collection.ui.writing.first.WritefirstActivity
//import com.google.android.material.bottomsheet.BottomSheetBehavior
//import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment
//import com.kizitonwose.calendarview.CalendarView
//import com.kizitonwose.calendarview.model.CalendarDay
//import com.kizitonwose.calendarview.model.CalendarMonth
//import com.kizitonwose.calendarview.model.DayOwner
//import com.kizitonwose.calendarview.ui.DayBinder
//import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
//import com.kizitonwose.calendarview.ui.ViewContainer
//import com.skydoves.powermenu.OnMenuItemClickListener
//import com.skydoves.powermenu.PowerMenu
//import com.skydoves.powermenu.PowerMenuItem
//import java.io.Serializable
//import java.time.DayOfWeek
//import java.time.LocalDate
//import java.time.YearMonth
//import java.time.ZoneId
//import java.time.format.DateTimeFormatter
//import java.time.format.TextStyle
//import java.util.*
//
//
//class CalendarBSActivity(context: Context) : BottomSheetDialogFragment() {
//
//    private lateinit var binding: ActivityMatchCalendarBsBinding
//
//    override val toolbar: Toolbar?
//        get() = binding.exFourToolbar
//
//    override val titleRes: Int? = null
//
//    private val today = LocalDate.now()
//
//    private var startDate: LocalDate? = null
//    private var endDate: LocalDate? = null
//
//    private val headerDateFormatter = DateTimeFormatter.ofPattern("EEE'\n'd MMM")
//
//    private val startBackground: GradientDrawable by lazy {
//        requireContext().getDrawable(R.drawable.example_4_continuous_selected_bg_start) as GradientDrawable
//    }
//
//    private val endBackground: GradientDrawable by lazy {
//        requireContext().getDrawable(R.drawable.example_4_continuous_selected_bg_end) as GradientDrawable
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//
//        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
//        binding = ActivityMatchCalendarBsBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
////    override fun onStart() {
////        super.onStart()
////        initCalendar()
////    }
//
//    private fun initCalendar(){
//        setHasOptionsMenu(true)
//        // We set the radius of the continuous selection background drawable dynamically
//        // since the view size is `match parent` hence we cannot determine the appropriate
//        // radius value which would equal half of the view's size beforehand.
//        binding.exFourCalendar.post {
//            val radius = ((binding.exFourCalendar.width / 7) / 2).toFloat()
//            startBackground.setCornerRadius(topLeft = radius, bottomLeft = radius)
//            endBackground.setCornerRadius(topRight = radius, bottomRight = radius)
//        }
//
//        // Set the First day of week depending on Locale
//        val daysOfWeek = daysOfWeekFromLocale()
//        binding.legendLayout.root.children.forEachIndexed { index, view ->
//            (view as TextView).apply {
//                text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
//                setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
//                setTextColor(R.color.green100)
//            }
//        }
//
//        val currentMonth = YearMonth.now()
//        binding.exFourCalendar.setup(currentMonth, currentMonth.plusMonths(12), daysOfWeek.first())
//        binding.exFourCalendar.scrollToMonth(currentMonth)
//
//        class DayViewContainer(view: View) : ViewContainer(view) {
//            lateinit var day: CalendarDay // Will be set when this container is bound.
//            val binding = Example4CalendarDayBinding.bind(view)
//
//            init {
//                view.setOnClickListener {
//                    if (day.owner == DayOwner.THIS_MONTH && (day.date == today || day.date.isAfter(today))) {
//                        val date = day.date
//                        if (startDate != null) {
//                            if (date < startDate || endDate != null) {
//                                startDate = date
//                                endDate = null
//                            } else if (date != startDate) {
//                                endDate = date
//                            }
//                        } else {
//                            startDate = date
//                        }
//                        this@Example4Fragment.binding.exFourCalendar.notifyCalendarChanged()
//                        bindSummaryViews()
//                    }
//                }
//            }
//        }
//
//        binding.exFourCalendar.dayBinder = object : DayBinder<DayViewContainer> {
//            override fun create(view: View) = DayViewContainer(view)
//            override fun bind(container: DayViewContainer, day: CalendarDay) {
//                container.day = day
//                val textView = container.binding.exFourDayText
//                val roundBgView = container.binding.exFourRoundBgView
//
//                textView.text = null
//                textView.background = null
//                roundBgView.makeInVisible()
//
//                val startDate = startDate
//                val endDate = endDate
//
//                when (day.owner) {
//                    DayOwner.THIS_MONTH -> {
//                        textView.text = day.day.toString()
//                        if (day.date.isBefore(today)) {
//                            textView.setTextColorRes(R.color.example_4_grey_past)
//                        } else {
//                            when {
//                                startDate == day.date && endDate == null -> {
//                                    textView.setTextColorRes(R.color.white)
//                                    roundBgView.makeVisible()
//                                    roundBgView.setBackgroundResource(R.drawable.example_4_single_selected_bg)
//                                }
//                                day.date == startDate -> {
//                                    textView.setTextColorRes(R.color.white)
//                                    textView.background = startBackground
//                                }
//                                startDate != null && endDate != null && (day.date > startDate && day.date < endDate) -> {
//                                    textView.setTextColorRes(R.color.white)
//                                    textView.setBackgroundResource(R.drawable.example_4_continuous_selected_bg_middle)
//                                }
//                                day.date == endDate -> {
//                                    textView.setTextColorRes(R.color.white)
//                                    textView.background = endBackground
//                                }
//                                day.date == today -> {
//                                    textView.setTextColorRes(R.color.example_4_grey)
//                                    roundBgView.makeVisible()
//                                    roundBgView.setBackgroundResource(R.drawable.example_4_today_bg)
//                                }
//                                else -> textView.setTextColorRes(R.color.example_4_grey)
//                            }
//                        }
//                    }
//                    // Make the coloured selection background continuous on the invisible in and out dates across various months.
//                    DayOwner.PREVIOUS_MONTH ->
//                        if (startDate != null && endDate != null && isInDateBetween(day.date, startDate, endDate)) {
//                            textView.setBackgroundResource(R.drawable.example_4_continuous_selected_bg_middle)
//                        }
//                    DayOwner.NEXT_MONTH ->
//                        if (startDate != null && endDate != null && isOutDateBetween(day.date, startDate, endDate)) {
//                            textView.setBackgroundResource(R.drawable.example_4_continuous_selected_bg_middle)
//                        }
//                }
//            }
//        }
//
//        class MonthViewContainer(view: View) : ViewContainer(view) {
//            val textView = Example4CalendarHeaderBinding.bind(view).exFourHeaderText
//        }
//        binding.exFourCalendar.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
//            override fun create(view: View) = MonthViewContainer(view)
//            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
//                val monthTitle = "${month.yearMonth.month.name.toLowerCase().capitalize()} ${month.year}"
//                container.textView.text = monthTitle
//            }
//        }
//
//        binding.exFourSaveButton.setOnClickListener click@{
//            val startDate = startDate
//            val endDate = endDate
//            if (startDate != null && endDate != null) {
//                val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
//                val text = "Selected: ${formatter.format(startDate)} - ${formatter.format(endDate)}"
//                Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
//            } else {
//                Snackbar.make(requireView(), "No selection. Searching all Airbnb listings.", Snackbar.LENGTH_LONG)
//                    .show()
//            }
//            fragmentManager?.popBackStack()
//        }
//
//        bindSummaryViews()
//    }
//
//
//
//    private fun isInDateBetween(inDate: LocalDate, startDate: LocalDate, endDate: LocalDate): Boolean {
//        if (startDate.yearMonth == endDate.yearMonth) return false
//        if (inDate.yearMonth == startDate.yearMonth) return true
//        val firstDateInThisMonth = inDate.plusMonths(1).yearMonth.atDay(1)
//        return firstDateInThisMonth >= startDate && firstDateInThisMonth <= endDate && startDate != firstDateInThisMonth
//    }
//
//    private fun isOutDateBetween(outDate: LocalDate, startDate: LocalDate, endDate: LocalDate): Boolean {
//        if (startDate.yearMonth == endDate.yearMonth) return false
//        if (outDate.yearMonth == endDate.yearMonth) return true
//        val lastDateInThisMonth = outDate.minusMonths(1).yearMonth.atEndOfMonth()
//        return lastDateInThisMonth >= startDate && lastDateInThisMonth <= endDate && endDate != lastDateInThisMonth
//    }
//
//    private fun bindSummaryViews() {
//        binding.exFourStartDateText.apply {
//            if (startDate != null) {
//                text = headerDateFormatter.format(startDate)
//                setTextColorRes(R.color.example_4_grey)
//            } else {
//                text = getString(R.string.start_date)
//                setTextColor(Color.GRAY)
//            }
//        }
//
//        binding.exFourEndDateText.apply {
//            if (endDate != null) {
//                text = headerDateFormatter.format(endDate)
//                setTextColorRes(R.color.example_4_grey)
//            } else {
//                text = getString(R.string.end_date)
//                setTextColor(Color.GRAY)
//            }
//        }
//
//        // Enable save button if a range is selected or no date is selected at all, Airbnb style.
//        binding.exFourSaveButton.isEnabled = endDate != null || (startDate == null && endDate == null)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.example_4_menu, menu)
//        binding.exFourToolbar.post {
//            // Configure menu text to match what is in the Airbnb app.
//            binding.exFourToolbar.findViewById<TextView>(R.id.menuItemClear).apply {
//                setTextColor(requireContext().getColorCompat(R.color.example_4_grey))
//                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
//                isAllCaps = false
//            }
//        }
//        menu.findItem(R.id.menuItemClear).setOnMenuItemClickListener {
//            startDate = null
//            endDate = null
//            binding.exFourCalendar.notifyCalendarChanged()
//            bindSummaryViews()
//            true
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        initCalendar()
//        val closeIndicator = requireContext().getDrawableCompat(R.drawable.ic_close)?.apply {
//            setColorFilter(requireContext().getColorCompat(R.color.example_4_grey), PorterDuff.Mode.SRC_ATOP)
//        }
//        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(closeIndicator)
//        requireActivity().window.apply {
//            // Update statusbar color to match toolbar color.
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                statusBarColor = requireContext().getColorCompat(R.color.white)
//                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//            } else {
//                statusBarColor = Color.GRAY
//            }
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        requireActivity().window.apply {
//            // Reset statusbar color.
//            statusBarColor = requireContext().getColorCompat(R.color.colorPrimaryDark)
//            decorView.systemUiVisibility = 0
//        }
//    }
//
//
////    private fun initCalendar(){
////        //day cell 크기 조정
////        val dm = DisplayMetrics()
////        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager //requireContext() -> this
////        wm.defaultDisplay.getMetrics(dm)
////        view?.findViewById<CalendarView>(R.id.calendarView)?.apply {
////            val dayWidth = dm.widthPixels / 7
////            val dayHeight = (dayWidth * 1.8).toInt()
////            daySize = com.kizitonwose.calendarview.utils.Size(dayWidth, dayHeight)
////        }
////
////        class DayViewContainer(view: View) : ViewContainer(view) {
////            val calendarDay = CalendarDateBinding.bind(view).calendarDayTv
////            val calendarCell = CalendarDateBinding.bind(view).dateCell
////            val rankPoint = CalendarDateBinding.bind(view).calendarRankIv
////            val todayHighlight = CalendarDateBinding.bind(view).calendarTodayView
////        }
////
////        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {
////
////
////            override fun create(view: View) = DayViewContainer(view)
////
////            @RequiresApi(Build.VERSION_CODES.O)
////            override fun bind(container: DayViewContainer, day: CalendarDay) {
////                container.calendarDay.text = day.date.dayOfMonth.toString()
////                container.calendarCell.setOnClickListener{}
////
//////                if (day.owner == DayOwner.THIS_MONTH) {
//////                    //today highlight
//////                    val currentDay = LocalDate.now()
//////
//////                    //오늘 날짜인 것 표시
//////                    if (currentDay == day.date){
//////                        container.todayHighlight.visibility = View.VISIBLE
//////                    }
//////                    else{
//////                        container.todayHighlight.visibility = View.GONE
//////                    }
//////                    container.calendarDay.setTextColor(Color.BLACK)
//////                } else {
//////
//////                    container.calendarDay.setTextColor(Color.LTGRAY)
//////                }
////            }
////        }
////
////        class MonthViewContainer(view: View) : ViewContainer(view) {
////            val calendarYear = CalendarBsYearMonthHeaderBinding.bind(view).calendarYearTv
////            val calendarMonth= CalendarBsYearMonthHeaderBinding.bind(view).calendarMonthTv
////        }
////        binding.calendarView.monthHeaderBinder = object :
////            MonthHeaderFooterBinder<MonthViewContainer> {
////            override fun create(view: View) = MonthViewContainer(view)
////            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
////                container.calendarYear.text = "${month.year}"
////                container.calendarMonth.text = "${month.yearMonth.month.name.toLowerCase().capitalize()}"
////                container.calendarMonth.setOnClickListener{}
////            }
////        }
////
////        val currentMonth = YearMonth.now()
////        val firstMonth = currentMonth.minusMonths(10)
////        val lastMonth = currentMonth.plusMonths(10)
////        val daysOfWeek = arrayOf(
////            DayOfWeek.SUNDAY,
////            DayOfWeek.MONDAY,
////            DayOfWeek.TUESDAY,
////            DayOfWeek.WEDNESDAY,
////            DayOfWeek.THURSDAY,
////            DayOfWeek.FRIDAY,
////            DayOfWeek.SATURDAY
////        )
////        binding.calendarView.setup(firstMonth, lastMonth, daysOfWeek.first())
////
////    }
//
//}
//
