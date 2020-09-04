package com.example.madscalculator

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        initialiseRTD()

    }

    companion object {

        lateinit var userNameNode: DatabaseReference
        lateinit var userPassNode: DatabaseReference
        lateinit var lastTenResultNode: DatabaseReference
        /*
        *intilaising fiebase RTD nodes
        */
        private fun initialiseRTD() {
            Log.d("calculatorNode","intialise")
            userNameNode = FirebaseDatabase.getInstance().getReference("userName")
            userPassNode = FirebaseDatabase.getInstance().getReference("userPass")
            lastTenResultNode = FirebaseDatabase.getInstance().getReference("lastTenResult")
            Log.d("userNameNode",userNameNode.toString())

        }
    }
}