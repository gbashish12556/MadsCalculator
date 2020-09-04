package com.example.madscalculator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.VisibleForTesting
import androidx.test.espresso.IdlingResource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener


class MainActivity : Activity() {

    var userNameEditText:EditText? = null
    var userPassEditText:EditText? = null
    var errorText:TextView? = null

    var remoteUserName:String = ""
    var remotePassword:String = ""
    private var mIdlingResource: RetrofitIdlingResource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getIdlingResource()
        if(mIdlingResource != null) {
            mIdlingResource!!.setIdleState(false)
        }

        userNameEditText = findViewById(R.id.userName)
        userPassEditText = findViewById(R.id.userPassword)
        errorText  = findViewById(R.id.error_message)

        findViewById<Button>(R.id.loginButton).setOnClickListener{

            var userName = userNameEditText?.text.toString()
            var userPassword = userPassEditText?.text.toString()

            if(userName.equals(remoteUserName) && userPassword.equals(remotePassword)){
                var intent = Intent(this,CalculatorActivity::class.java)
                startActivity(intent)
            }else{
                errorText?.visibility = View.VISIBLE
            }
        }
    }


    override fun onResume() {
        super.onResume()

        App.userNameNode.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                remoteUserName = (dataSnapshot.getValue() as String?)!!
                mIdlingResource!!.setIdleState(true)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("error", databaseError.toString())
            }
        })

        App.userPassNode.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                remotePassword = (dataSnapshot.getValue() as String?)!!
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("error", databaseError.toString())
            }
        })

    }

    @VisibleForTesting
    @NonNull
    fun getIdlingResource(): IdlingResource {
        if (mIdlingResource == null) {
            mIdlingResource = RetrofitIdlingResource()
        }
        return mIdlingResource!!
    }


}