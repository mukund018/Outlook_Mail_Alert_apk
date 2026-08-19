package com.example.outlookringalert

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StopAlertReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        OutlookNotificationListener.stopAlert(context)
    }
}