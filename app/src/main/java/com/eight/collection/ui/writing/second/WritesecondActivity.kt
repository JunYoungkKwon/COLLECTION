package com.eight.collection.ui.writing.second

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.eight.collection.R
import com.eight.collection.data.entities.Write.Write
import com.eight.collection.data.remote.recieves3url.ReceiveS3UrlService
import com.eight.collection.databinding.ActivityWritesecondBinding
import com.eight.collection.ui.finish.FinishActivity
import com.eight.collection.ui.writing.ReceiveS3URLView
import com.eight.collection.ui.writing.WriteView
import com.eight.collection.ui.writing.first.AddedClothes
import com.eight.collection.ui.writing.first.FixedClothes
import com.eight.collection.ui.writing.first.Image
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomFragment
import com.eight.collection.ui.writing.first.etc.WritefirstEtcFragment
import com.eight.collection.ui.writing.first.shoes.WritefirstShoesFragment
import com.eight.collection.ui.writing.first.top.WritefirstTopFragment
import com.eight.collection.ui.writing.second.place.WritesecondPlaceFragment
import com.eight.collection.ui.writing.second.weather.WritesecondWeatherFragment
import com.eight.collection.ui.writing.second.who.WritesecondWhoFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.hedgehog.ratingbar.RatingBar

class WritesecondActivity : AppCompatActivity(), ReceiveS3URLView, WriteView{
    lateinit var binding : ActivityWritesecondBinding
    val information = arrayListOf("PLACE","WEATHER","WHO")
    val fragmentList = arrayListOf<Fragment>()
    private var getplacedataListener : WritesecondActivity.GetPlaceDataListener? = null
    private var getweatherdataListener : WritesecondActivity.GetWeatherDataListener? = null
    private var getwhodataListener : WritesecondActivity.GetWhoDataListener? = null
    private lateinit var ratingBar : RatingBar


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

        setGetPlaceDataClickListener((fragmentList[0] as WritesecondPlaceFragment))
        setGetWeatherDataClickListener(fragmentList[1] as WritesecondWeatherFragment)
        setGetWhoDataClickListener(fragmentList[2] as WritesecondWhoFragment)

        ratingBar = findViewById(R.id.writesecond_lookpoint_ratingbar)

        var lookpoint : Float = 3F

        ratingBar.setOnRatingChangeListener(object: RatingBar.OnRatingChangeListener{
            override fun onRatingChange(RatingCount: Float) {
                lookpoint = RatingCount
                Log.d("log1","$RatingCount")
            }
        })


        binding.writesecondFinishButton2.setOnClickListener {
            //URL 받기
            receiveS3Url()

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
            val fixedPlace : ArrayList<FixedPlace> = ArrayList()
            val addedPlace : ArrayList<AddedPlace> = ArrayList()
            val fixedWeather : ArrayList<FixedWeather> = ArrayList()
            val addedWeather : ArrayList<AddedWeather> = ArrayList()
            val fixedWho : ArrayList<FixedWho> = ArrayList()
            val addedWho : ArrayList<AddedWho> = ArrayList()
            fixedPlace.addAll(getplacedataListener!!.getFixedData())
            addedPlace.addAll(getplacedataListener!!.getAddedData())
            fixedWeather.addAll(getweatherdataListener!!.getFixedData())
            addedWeather.addAll(getweatherdataListener!!.getAddedData())
            fixedWho.addAll(getwhodataListener!!.getFixedData())
            addedWho.addAll(getwhodataListener!!.getAddedData())


            //lookpoint
            lookpoint

            //comment
            var comment : String = binding.writesecondCommentEt.text.toString()


            //Write API
            write()



            startActivity(Intent(this, FinishActivity::class.java))
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
    private fun getWrite(){

    }

    private fun write(){

    }

    override fun onWriteLoading() {

    }

    override fun onWriteSuccess() {

    }

    override fun onWriteFailure(code: Int, message: String) {

    }

    interface GetPlaceDataListener {
        fun getFixedData() : ArrayList<FixedPlace>
        fun getAddedData() : ArrayList<AddedPlace>
    }

    interface GetWeatherDataListener {
        fun getFixedData() : ArrayList<FixedWeather>
        fun getAddedData() : ArrayList<AddedWeather>
    }

    interface GetWhoDataListener {
        fun getFixedData() : ArrayList<FixedWho>
        fun getAddedData() : ArrayList<AddedWho>
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


}