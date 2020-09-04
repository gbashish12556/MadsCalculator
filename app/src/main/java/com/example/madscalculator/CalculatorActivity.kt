package com.example.madscalculator

import android.os.Bundle
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
        text = textArea!!.text.toString()
        text = text + buttonText
        textArea!!.setText(text)
    }

    fun evaluateExpression(view: View?) {
        advanceCalculator = CalculatorUtils()
        text = textArea!!.text.toString()
        val postfixExpression = advanceCalculator!!.findPostfix(text)
        if (postfixExpression == "Invalid expression") {
            errorMessageText!!.visibility = View.VISIBLE
            textArea!!.setText("")
            return
        }
        val result = advanceCalculator!!.evaluatePostfix(postfixExpression)
        //        TODO You can make it like 2+4=6.0*2=12.0..so that further you can take actions..however i don't want it that messy;)
        textArea!!.setText("" + result)
    }

    fun clearTextField(view: View?) {
        textArea!!.setText("")
    }

    fun findSqrt(view: View?) {
        text = textArea!!.text.toString()
        var value = 0.0
        try {
            value = text!!.toDouble()
            textArea!!.setText("" + Math.sqrt(value))
            errorMessageText!!.visibility = View.GONE
        } catch (e: NumberFormatException) {
            errorMessageText!!.visibility = View.VISIBLE
            textArea!!.setText("")
        }
    }

    fun findLog(view: View?) {
        text = textArea!!.text.toString()
        var value = 0.0
        try {
            value = text!!.toDouble()
            textArea!!.setText("" + Math.log10(value))
            errorMessageText!!.visibility = View.GONE
        } catch (e: NumberFormatException) {
            errorMessageText!!.visibility = View.VISIBLE
            textArea!!.setText("")
        }
    }
}
