package com.eight.collection.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.eight.collection.ApplicationClass
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status


class SMSReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            val status: Status? = extras!![SmsRetriever.EXTRA_STATUS] as Status?

            if (status != null) {
                when (status.getStatusCode()) {
                    CommonStatusCodes.SUCCESS -> {
                        val message = extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String?
                        Log.d(ApplicationClass.TAG, "SmsReceiver : onReceiver(CommonStatusCodes.SUCCESS)")
                        Log.d(ApplicationClass.TAG, "message : $message")
                        val code = message?.replace("[^0-9]".toRegex(),"")
                        val codeCg = code!!.substring(0, 6)
                        ApplicationClass.saveCode(codeCg)
                    }
                    CommonStatusCodes.TIMEOUT -> Log.d(ApplicationClass.TAG, "SmsReceiver : onReceiver(CommonStatusCodes.TIMEOUT)")
                }
            }
        }
    }
}