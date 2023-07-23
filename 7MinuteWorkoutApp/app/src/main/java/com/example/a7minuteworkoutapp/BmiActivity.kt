package com.example.a7minuteworkoutapp


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.a7minuteworkoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(R.color.black)

        setSupportActionBar(binding?.toolbarbmi)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarbmi?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding?.calculateBtn?.setOnClickListener {
            calculateBMI()
            this.currentFocus?.let { it1 -> hideKeyboard(it1) }
        }
        binding?.resetbtn?.setOnClickListener {
            reset()
        }


    }

    private fun calculateBMI() {
        if (validateMetricUnits()) {
            val heightValue: Float = binding?.heightInput?.text.toString().toFloat() / 100
            val weightValue: Float = binding?.weightInput?.text.toString().toFloat()
            val bmi = weightValue / (heightValue * heightValue)

            displayBMI(bmi)

        } else {
            Toast.makeText(this, "Please Enter Valid Values!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayBMI(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very Severely Underweight"
            bmiDescription = "Eat More and Exercise Daily!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely Underweight"
            bmiDescription = "Eat More and Exercise Daily!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Eat More and Exercise Daily!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "You are in a good shape!"
        } else {
            bmiLabel = "Obese"
            bmiDescription = "Oops! You need to eat healthy and exercise daily.!"
        }

        binding?.displayBMIResult?.visibility = View.VISIBLE
        val bmiValueAfterRoundingOff =
            BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.tvBmiValue?.text = bmiValueAfterRoundingOff
        binding?.tvBmiType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
    }

    private fun reset() {
        binding?.displayBMIResult?.visibility = View.INVISIBLE
        binding?.heightInput?.text?.clear()
        binding?.weightInput?.text?.clear()
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        if (binding?.weightInput?.text.toString().isEmpty() || binding?.heightInput?.text.toString()
                .isEmpty()
        ) {
            isValid = false
        }
        return isValid

    }

    @SuppressLint("ServiceCast")
    private fun hideKeyboard(view: View) {
        // on below line checking if view is not null.
        if (view != null) {
            // on below line we are creating a variable
            // for input manager and initializing it.
            val inputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

            // on below line hiding our keyboard.
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }

    }
}