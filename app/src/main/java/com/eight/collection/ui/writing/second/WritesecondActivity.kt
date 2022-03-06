package com.eight.collection.ui.writing.second

import android.content.Intent
import android.os.Bundle
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
import com.eight.collection.data.remote.modi.ModiResult
import com.eight.collection.data.remote.modi.ModiService
import com.eight.collection.data.remote.recieves3url.ReceiveS3UrlService
import com.eight.collection.data.remote.write.WriteService
import com.eight.collection.databinding.ActivityWritesecondBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.finish.FinishActivity
import com.eight.collection.ui.writing.ModiView
import com.eight.collection.ui.writing.ReceiveS3URLView
import com.eight.collection.ui.writing.WriteView
import com.eight.collection.ui.writing.first.AddedClothes
import com.eight.collection.ui.writing.first.FixedClothes
import com.eight.collection.ui.writing.first.Image
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.eight.collection.ui.writing.second.place.WritesecondPlaceFragment
import com.eight.collection.ui.writing.second.weather.WritesecondWeatherFragment
import com.eight.collection.ui.writing.second.who.WritesecondWhoFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.hedgehog.ratingbar.RatingBar

class WritesecondActivity : AppCompatActivity(), ReceiveS3URLView, WriteView, ModiView {
    lateinit var binding : ActivityWritesecondBinding
    val information = arrayListOf("PLACE","WEATHER","WHO")
    val fragmentList = arrayListOf<Fragment>()
    private var getplacedataListener : WritesecondActivity.GetPlaceDataListener? = null
    private var getweatherdataListener : WritesecondActivity.GetWeatherDataListener? = null
    private var getwhodataListener : WritesecondActivity.GetWhoDataListener? = null
    private lateinit var ratingBar : RatingBar
    var lookpoint : Float = 0F
    var modidate : String? = null



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

        if (intent.getIntExtra("mode",1) == 2){
            modi()
        }



        setGetPlaceDataClickListener((fragmentList[0] as WritesecondPlaceFragment))
        setGetWeatherDataClickListener(fragmentList[1] as WritesecondWeatherFragment)
        setGetWhoDataClickListener(fragmentList[2] as WritesecondWhoFragment)

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

                //Write API
                write()

                val intent = Intent(this, FinishActivity::class.java)
                intent.putExtra("date", date)


                startActivity(intent)

                finish()
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
        Log.d("url",url)
    }

    override fun onReceiveS3URLFailure(code: Int, message: String) {
        Log.d("message",message)
    }


    // Write API
    private fun getWrite() : Write{
        val mode = intent.getIntExtra("mode",1)
        Log.d("mode:","${mode}")
        //Date
        val date = intent.getStringExtra("date")

        //lookname
        val lookname = intent.getStringExtra("lookname")

        //photoIs
        val photoIs = intent.getIntExtra("photoIs", -1)

        //image
        val imageList = intent.getParcelableArrayListExtra<Image>("image")

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
        Log.d("image","${imageList}")
        Log.d("fixedplace","${fixedPlace}")
        Log.d("addedplace","${addedPlace}")
        Log.d("fixedweather","${fixedWeather}")
        Log.d("addedweather","${addedWeather}")
        Log.d("fixedwho","${fixedWho}")
        Log.d("addedwho","${addedWho}")

        //comment
        var comment : String = binding.writesecondCommentEt.text.toString()

        return Write(mode,date,lookname,photoIs,imageList,fixedClothes,addedClothes,fixedPlace,addedPlace,fixedWeather,addedWeather,fixedWho,addedWho,lookpoint,comment)
    }

    private fun write(){
        WriteService.write(this, getWrite())
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


    private fun modi(){
        ModiService.modi(this, modidate!!)
    }

    override fun onModiLoading() {

    }

    override fun onModiSuccess(modiresult: ModiResult) {
        if(modiresult.selected?.comment != null){
            binding.writesecondCommentEt.setText(modiresult.selected?.comment)
        }
        if(modiresult.selected?.lookpoint != null){
            binding.writesecondLookpointRatingbar.setStar(modiresult.selected?.lookpoint)
        }
    }

    override fun onModiFailure(code: Int, message: String) {

    }


}