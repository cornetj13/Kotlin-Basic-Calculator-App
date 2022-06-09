package com.example.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var lastNumeric : Boolean = false
    var lastDot     : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        tvInput.text = ""
        lastNumeric  = false
        lastDot      = false
    }

    fun onDecimalPoint(view: View) {
        if(lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot     = true
        }
    }

    fun onOperator(view: View) {
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot     = false
        }
    }

    private fun isOperatorAdded(value: String) : Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("-") || value.contains("+")
        }
    }

    fun onEqual(view: View) {
        if(lastNumeric) {
            var tvValue = tvInput.text.toString()
            var prefix = ""

            if(tvValue.startsWith("-")) {
                prefix = "-"
                tvValue = tvValue.substring(1)
            }
            if(tvValue.contains("-")) {
                val splitValue    = tvValue.split("-")
                var leftHandSide  = splitValue[0]
                var rightHandSide = splitValue[1]

                if(prefix.isNotEmpty()) {
                    leftHandSide = prefix + leftHandSide
                }

                var result = leftHandSide.toDouble() - rightHandSide.toDouble()
                tvInput.text = removeZeroAfterDot(result.toString())
            } else if(tvValue.contains("+")) {
                val splitValue = tvValue.split("+")
                var leftHandSide = splitValue[0]
                var rightHandSide = splitValue[1]

                if (prefix.isNotEmpty()) {
                    leftHandSide = prefix + leftHandSide
                }

                var result = leftHandSide.toDouble() + rightHandSide.toDouble()
                tvInput.text = removeZeroAfterDot(result.toString())
            } else if(tvValue.contains("*")) {
                val splitValue = tvValue.split("*")
                var leftHandSide = splitValue[0]
                var rightHandSide = splitValue[1]

                if (prefix.isNotEmpty()) {
                    leftHandSide = prefix + leftHandSide
                }

                var result = leftHandSide.toDouble() * rightHandSide.toDouble()
                tvInput.text = removeZeroAfterDot(result.toString())
            } else if(tvValue.contains("/")) {
                val splitValue = tvValue.split("/")
                var leftHandSide = splitValue[0]
                var rightHandSide = splitValue[1]

                if (prefix.isNotEmpty()) {
                    leftHandSide = prefix + leftHandSide
                }

                var result = leftHandSide.toDouble() / rightHandSide.toDouble()
                tvInput.text = removeZeroAfterDot(result.toString())
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String {
        var newResult = result
        if(result.contains(".0")) {
            newResult = result.substring(0, result.length - 2)
        }
        return newResult
    }
}