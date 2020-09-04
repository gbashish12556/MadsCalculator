package com.example.madscalculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {
    var advanceCalculator: CalculatorUtils? = null
    var textArea: EditText? = null
    var text: String? = null
    var errorMessageText: TextView? = null
    var currentHistoryIndex = 0
    var lastTenResult:MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_calculator)

        textArea = findViewById(R.id.edit_text)
        errorMessageText = findViewById(R.id.error_message)
        errorMessageText!!.visibility = View.GONE
    }

    fun getTextFromButton(view: View) {
        errorMessageText!!.visibility = View.GONE
        val buttonText = (view as Button).text as String
        resetHistoryIndex()
        storeResult(buttonText)
        text = textArea!!.text.toString()
        text = text + buttonText
        textArea!!.setText(text)
    }

    fun evaluateExpression(view: View?) {
        resetHistoryIndex()
        storeResult("=")
        advanceCalculator = CalculatorUtils()
        text = textArea!!.text.toString()
        val postfixExpression = advanceCalculator!!.findPostfix(text)
        if (postfixExpression == "Invalid expression") {
            errorMessageText!!.visibility = View.VISIBLE
            textArea!!.setText("")
            return
        }
        val result = advanceCalculator!!.evaluatePostfix(postfixExpression)
        storeResult(result.toString())
        textArea!!.setText("" + result)
    }

    fun clearTextField(view: View?) {
        resetHistoryIndex()
        textArea!!.setText("")
    }

    fun resetHistoryIndex(){
        if(currentHistoryIndex != 0) {
            currentHistoryIndex = 0
        }
    }
    fun getLastAns(view: View?){
        resetHistoryIndex()
        text = textArea!!.text.toString()
        val lastAns = advanceCalculator!!.lastStoredAns.toString()
        storeResult(lastAns)
        if(advanceCalculator!!.validateLastCharacter(text)){
           text = text+ lastAns
        }else{
            text = lastAns
        }
        textArea!!.setText(text)
    }

    fun storeResult(string:String){
        if(lastTenResult.size >= 10){
            lastTenResult.removeAt(0)
        }
        lastTenResult.add(string)
        Log.d("lastTenResult",lastTenResult.toString())
    }

    fun showHistory(view: View?){
        textArea!!.setText(lastTenResult.get(currentHistoryIndex%10))
        currentHistoryIndex++
    }
}
