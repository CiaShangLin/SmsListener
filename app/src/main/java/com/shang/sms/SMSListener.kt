package com.shang.sms

import android.content.*
import android.os.Build
import android.provider.Telephony
import android.telephony.SmsManager.getDefault
import android.telephony.SmsMessage
import android.telephony.TelephonyManager
import android.telephony.TelephonyManager.PHONE_TYPE_CDMA
import android.telephony.gsm.SmsManager.getDefault

import android.util.Log
import androidx.core.telephony.TelephonyManagerCompat


class SMSListener : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Telephony.Sms.Intents.SMS_RECEIVED_ACTION -> {
                try {
                    intent?.extras?.let {
                        val pdus = it.get("pdus") as Array<Any>
                        for (element in pdus) {
                            val manager =
                                context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                            val smsMessage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                val format = if (manager.phoneType == PHONE_TYPE_CDMA) {
                                    SmsMessage.FORMAT_3GPP2
                                } else {
                                    SmsMessage.FORMAT_3GPP
                                }
                                SmsMessage.createFromPdu(element as ByteArray, format)
                            } else {
                                SmsMessage.createFromPdu(element as ByteArray)
                            }
                            Log.d("DEBUG", "${smsMessage.displayMessageBody}")
                            clipboard(context, smsMessage.displayMessageBody)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun clipboard(context: Context?, code: String) {
        val clipboardManager =
            context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val data = ClipData.newPlainText("text", code)
        clipboardManager.setPrimaryClip(data)
    }
}