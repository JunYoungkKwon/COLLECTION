package com.eight.collection.ui.writing.second

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.eight.collection.R
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.entities.Write.Write
import com.eight.collection.data.remote.imageUpload.ImageUploadService
import com.eight.collection.data.remote.modi.ModiResult
import com.eight.collection.data.remote.modi.ModiService
import com.eight.collection.data.remote.recieves3url.ReceiveS3UrlService
import com.eight.collection.data.remote.write.WriteService
import com.eight.collection.databinding.ActivityWritesecondBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.finish.FinishActivity
import com.eight.collection.ui.writing.*
import com.eight.collection.ui.writing.first.*
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomFragment
import com.eight.collection.ui.writing.first.shoes.WritefirstShoesFragment
import com.eight.collection.ui.writing.first.top.WritefirstTopFragment
import com.eight.collection.ui.writing.second.place.WritesecondPlaceFragment
import com.eight.collection.ui.writing.second.weather.WritesecondWeatherFragment
import com.eight.collection.ui.writing.second.who.WritesecondWhoFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.hedgehog.ratingbar.RatingBar
import java.io.File

class WritesecondActivity : AppCompatActivity(), ReceiveS3URLView, WriteView,
    RefreshDialogInterface, ModiView, ImageUploadView {
    lateinit var binding : ActivityWritesecondBinding
    lateinit var refreshDialog : RefreshDialog
    val information = arrayListOf("PLACE","WEATHER","WHO")
    val fragmentList = arrayListOf<Fragment>()
    private var getplacedataListener : WritesecondActivity.GetPlaceDataListener? = null
    private var getweatherdataListener : WritesecondActivity.GetWeatherDataListener? = null
    private var getwhodataListener : WritesecondActivity.GetWhoDataListener? = null
    private var refreshplacedataListener : WritesecondActivity.RefreshPlaceDataListener? = null
    private var refreshweatherdataListener : WritesecondActivity.RefreshWeatherDataListener? = null
    private var refreshwhodataListener : WritesecondActivity.RefreshWhoDataListener? = null
    private lateinit var ratingBar : RatingBar
    private var mListener : OnClickFinishListener? = null
    var lookpoint : Float = 0F
    var modidate : String? = null
    var upLoadUrl : String? = null
    var writefirstActivity : WritefirstActivity? = null
    var mode : Int = 1
    var a : Int = 0

    //image
    var imageList : ArrayList<Image>? = null
    var imagefileList : ArrayList<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritesecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentList.apply {
            add(WritesecondPlaceFragment())
            add(WritesecondWeatherFragment())
            add(WritesecondWhoFragment())
        }

        val writesecondAdapter = WritesecondVPA(this,fragmentList)
        binding.writesecondVp.adapter = writesecondAdapter
        TabLayoutMediator(binding.writesecondTb,binding.writesecondVp){
                tab, position ->
            tab.text = information[position]
        }.attach()

        val date = intent.getStringExtra("date")
        modidate = date


        mode = intent.getIntExtra("mode",1)
        Log.d("mode","${mode}")
        //수정하기시 기존 데이터 불러오기
        if (mode == 2){
            modi()
        }


        //초기화 기능 구현
        refreshDialog = RefreshDialog(this, this)
        binding.writesecondRefreshIv.setOnClickListener{
            refreshDialog.show()
        }


        setGetPlaceDataClickListener((fragmentList[0] as WritesecondPlaceFragment))
        setGetWeatherDataClickListener(fragmentList[1] as WritesecondWeatherFragment)
        setGetWhoDataClickListener(fragmentList[2] as WritesecondWhoFragment)

        setRefreshPlaceDataClickListener((fragmentList[0] as WritesecondPlaceFragment))
        setRefreshWeatherDataClickListener(fragmentList[1] as WritesecondWeatherFragment)
        setRefreshWhoDataClickListener(fragmentList[2] as WritesecondWhoFragment)

        ratingBar = findViewById(R.id.writesecond_lookpoint_ratingbar)


        ratingBar.setOnRatingChangeListener(object: RatingBar.OnRatingChangeListener{
            override fun onRatingChange(RatingCount: Float) {
                lookpoint = RatingCount
            }
        })



        binding.writesecondFinishButton2.setOnClickListener {
            if(lookpoint == 0F){
                var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = "LOOK POINT를 설정해주세요."
                var toast = Toast(this)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 240)
                toast.show()
            }
            else {
                //URL 받기
                receiveS3Url()

                //image Upload
                imageList = intent.getParcelableArrayListExtra("image")
                imagefileList = intent.getStringArrayListExtra("file")
                Log.d("imagefileList","${imagefileList}")

                if (imagefileList != null) {
                    for (i in imagefileList!!){
                        imageUpload(i!!)
                    }
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    //Write API
                    write()
                }, 100)


                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, FinishActivity::class.java)
                    intent.putExtra("date", date)

                    startActivity(intent)

                    finish()
                }, 700)
            }
        }
    }


    // Presigned URL 받아오기 API
    private fun receiveS3Url(){
        ReceiveS3UrlService.receiveS3url(this)
    }

    override fun onReceiveS3URLLoading() {
    }

    override fun onReceiveS3URLSuccess(url : String){
    }

    override fun onReceiveS3URLFailure(code: Int, message: String) {
        Log.d("message",message)
    }


    // Write API
    private fun getWrite() : Write {
        val mode = intent.getIntExtra("mode",1)

        //Date
        val date = intent.getStringExtra("date")

        //lookname
        val lookname = intent.getStringExtra("lookname")

        //photoIs
        val photoIs = intent.getIntExtra("photoIs", -1)

        //Clothes
        val fixedClothes = intent.getParcelableArrayListExtra<FixedClothes>("fixed")
        val addedClothes = intent.getParcelableArrayListExtra<AddedClothes>("added")

        //PWW Part
        val fixedPlace : ArrayList<Int> = ArrayList()
        val addedPlace : ArrayList<String> = ArrayList()
        val fixedWeather : ArrayList<Int> = ArrayList()
        val addedWeather : ArrayList<String> = ArrayList()
        val fixedWho : ArrayList<Int> = ArrayList()
        val addedWho : ArrayList<String> = ArrayList()
        fixedPlace.addAll(getplacedataListener!!.getFixedData())
        addedPlace.addAll(getplacedataListener!!.getAddedData())
        fixedWeather.addAll(getweatherdataListener!!.getFixedData())
        addedWeather.addAll(getweatherdataListener!!.getAddedData())
        fixedWho.addAll(getwhodataListener!!.getFixedData())
        addedWho.addAll(getwhodataListener!!.getAddedData())

        Log.d("date","${date}")
        Log.d("fixedplace","${fixedPlace}")
        Log.d("addedplace","${addedPlace}")
        Log.d("fixedweather","${fixedWeather}")
        Log.d("addedweather","${addedWeather}")
        Log.d("fixedwho","${fixedWho}")
        Log.d("addedwho","${addedWho}")
        Log.d("imageList","${imageList}")

        //comment
        var comment : String = binding.writesecondCommentEt.text.toString()

        return Write(mode,date,lookname,photoIs,imageList,fixedClothes,addedClothes,fixedPlace,addedPlace,fixedWeather,addedWeather,fixedWho,addedWho,lookpoint,comment)
    }

    private fun write(){
        Handler(Looper.getMainLooper()).postDelayed({
            WriteService.write(this, getWrite())
        }, 500)
    }

    override fun onWriteLoading() {
    }

    override fun onWriteSuccess() {
    }

    override fun onWriteFailure(code: Int, message: String) {
        when(code) {
            4009,4010,4011,4012,4013,4015 -> {
                var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = message
                var toast = Toast(this)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 270)
                toast.show()
            }

            else -> {
                var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = message
                var toast = Toast(this)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 270)
                toast.show()
            }
        }
    }

    interface GetPlaceDataListener {
        fun getFixedData() : ArrayList<Int>
        fun getAddedData() : ArrayList<String>
    }

    interface GetWeatherDataListener {
        fun getFixedData() : ArrayList<Int>
        fun getAddedData() : ArrayList<String>
    }

    interface GetWhoDataListener {
        fun getFixedData() : ArrayList<Int>
        fun getAddedData() : ArrayList<String>
    }

    fun setGetPlaceDataClickListener(getPlaceDataListener : WritesecondPlaceFragment){
        this.getplacedataListener = getPlaceDataListener
    }

    fun setGetWeatherDataClickListener(getWeatherDataListener : WritesecondWeatherFragment){
        this.getweatherdataListener = getWeatherDataListener
    }

    fun setGetWhoDataClickListener(getWhoDataListener : WritesecondWhoFragment){
        this.getwhodataListener = getWhoDataListener
    }

    //색 초기화
    interface RefreshPlaceDataListener {
        fun refreshData()
    }

    interface RefreshWeatherDataListener {
        fun refreshData()
    }

    interface RefreshWhoDataListener {
        fun refreshData()
    }

    fun setRefreshPlaceDataClickListener(refreshPlaceDataListener : WritesecondPlaceFragment){
        this.refreshplacedataListener = refreshPlaceDataListener
    }

    fun setRefreshWeatherDataClickListener(refreshWeatherDataListener : WritesecondWeatherFragment){
        this.refreshweatherdataListener = refreshWeatherDataListener
    }

    fun setRefreshWhoDataClickListener(refreshWhoDataListener : WritesecondWhoFragment){
        this.refreshwhodataListener = refreshWhoDataListener
    }



    override fun onOkButtonClicked() {
        //LOOK POINT 초기화
        binding.writesecondLookpointRatingbar.setStar(0F)
        lookpoint = 0F
        //COMENT 초기화
        binding.writesecondCommentEt.setText("")
        //버튼 초기화
        refreshplacedataListener?.refreshData()
        refreshweatherdataListener?.refreshData()
        refreshwhodataListener?.refreshData()

    }
    override fun onCancelButtonClicked() {
    }


    private fun modi(){
        ModiService.modi(this, modidate!!)
    }
    override fun onModiLoading() {
    }
    override fun onModiSuccess(modiresult: ModiResult) {
        //수정하기시 LookPoint 설정
        if(modiresult.selected?.lookpoint != null){
            binding.writesecondLookpointRatingbar.setStar(modiresult.selected?.lookpoint)
            lookpoint = modiresult.selected?.lookpoint
        }
        //수정하기시 Comment 설정
        if(modiresult.selected?.comment != null){
            binding.writesecondCommentEt.setText(modiresult.selected?.comment)
        }
    }
    override fun onModiFailure(code: Int, message: String) {
    }


    // 이미지 S3 업로드 API
    private fun imageUpload(upLoadImage : String){
        val file = File(upLoadImage)
        Log.d("file","${file}")

        ImageUploadService.imageUpload(this, file)
    }

    override fun onImageUploadLoading() {
    }

    override fun onImageUploadSuccess(url: String) {
        Log.d("s3url","${url}")
        upLoadUrl = url
        Log.d("upLoadUrl","${upLoadUrl}")
        imageList?.get(a)?.imageUrl = url
        a=a+1
        Log.d("ImageList","${imageList}")
    }

    override fun onImageUploadFailure(code: Int, message: String) {
        Log.d("failmessage",message)
    }

    //WritefirstActivity 종료
    interface OnClickFinishListener {
        fun onFinish()
    }

    fun setOnClickFinish(listener: WritefirstActivity){
        this.mListener = listener
    }


}