package com.example.madscalculator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : Activity() {

    var userNameEditText:EditText? = null
    var userPassEditText:EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        userNameEditText = findViewById(R.id.userName)
        userPassEditText = findViewById(R.id.userPassword)

        findViewById<Button>(R.id.loginButton).setOnClickListener{

            var userName = userNameEditText?.text.toString()
            var userPassword = userPassEditText?.text.toString()

            if(userName.equals("Ashish") && userPassword.equals("123456$")){
                var intent = Intent(this,CalculatorActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,"Invalid User Or Password",Toast.LENGTH_LONG).show();
            }
        }
    }




}