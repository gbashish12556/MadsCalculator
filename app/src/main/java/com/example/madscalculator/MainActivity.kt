package com.example.madscalculator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener


class MainActivity : Activity() {

    var userNameEditText:EditText? = null
    var userPassEditText:EditText? = null

    var remoteUserName:String = ""
    var remotePassword:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userNameEditText = findViewById(R.id.userName)
        userPassEditText = findViewById(R.id.userPassword)

        findViewById<Button>(R.id.loginButton).setOnClickListener{

            var userName = userNameEditText?.text.toString()
            var userPassword = userPassEditText?.text.toString()

            if(userName.equals(remoteUserName) && userPassword.equals(remotePassword)){
                var intent = Intent(this,CalculatorActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,"Invalid User Or Password",Toast.LENGTH_LONG).show();
            }
        }
    }


    override fun onResume() {
        super.onResume()

        App.userNameNode.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                remoteUserName = (dataSnapshot.getValue() as String?)!!
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

}