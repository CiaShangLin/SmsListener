package com.shang.sms

import android.app.ActivityManager
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.provider.Telephony
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private val mSMSListener = SMSListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.READ_SMS
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS),
//                200
//            )
//        }
    }


    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        registerReceiver(mSMSListener, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mSMSListener)
    }

}