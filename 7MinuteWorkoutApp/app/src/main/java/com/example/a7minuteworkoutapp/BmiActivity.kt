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
    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW = "US_UNITS_VIEW"
    }

    private var currentVisibleView : String = METRIC_UNITS_VIEW
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

            if(currentVisibleView == METRIC_UNITS_VIEW) {
                if(validateMetricUnits()) {
                    val heightValue: Float = binding?.heightInput?.text.toString().toFloat() / 100
                    val weightValue: Float = binding?.weightInput?.text.toString().toFloat()
                    calculateBMI(heightValue, weightValue)
                }
                else
                    Toast.makeText(this, "Please enter correct values!", Toast.LENGTH_LONG).show()
            }
            else {
                if(validateUsUnits()){
                    val heightFeet = binding?.heightFeetInput?.text.toString().toFloat()
                    val heightInch = binding?.heightInchInput?.text.toString().toFloat()
                    //1 feet = 0.3048 meters and 1 inch = 0.0254 meters
                    val heightValue: Float = ((heightFeet*0.3048f) + (heightInch * 0.0254f))
                    //1 pound = 0.453592 kg
                    val weightValue: Float = binding?.weightInput?.text.toString().toFloat() * 0.453592f
                    calculateBMI(heightValue,weightValue)
                }
                else
                    Toast.makeText(this, "Please enter correct values!", Toast.LENGTH_LONG).show()
            }

            this.currentFocus?.let { it1 -> hideKeyboard(it1) }
        }
        //Functionality Of Reset Button
        binding?.resetbtn?.setOnClickListener {
            if(currentVisibleView == METRIC_UNITS_VIEW){
                resetmetricview()
            }
            else {
                resetusview()
            }
        }
        //Functionality of Radio Buttons
        binding?.radiogroup?.setOnCheckedChangeListener{ _,checkedId:Int ->
            if(checkedId == R.id.rbMetricsUnits){
                currentVisibleView = METRIC_UNITS_VIEW
                binding?.linearlayoutusheight?.visibility = View.INVISIBLE
                binding?.inputHeightTextView?.visibility = View.VISIBLE
                binding?.inputWeightTextView?.hint = "WEIGHT (in kg)"
                resetmetricview()
                resetusview()
            }
            else {
                currentVisibleView = US_UNITS_VIEW
                binding?.linearlayoutusheight?.visibility = View.VISIBLE
                binding?.inputHeightTextView?.visibility = View.INVISIBLE
                binding?.inputWeightTextView?.hint = "WEIGHT (in pounds)"
                resetusview()
                resetmetricview()
            }

        }

    }

    private fun calculateBMI(height : Float,weight : Float) {
            val bmi = weight / (height * height)

            displayBMI(bmi)
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


        val bmiValueAfterRoundingOff =
            BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.tvBmiValue?.text = bmiValueAfterRoundingOff
        binding?.tvBmiType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
        binding?.displayBMIResult?.visibility = View.VISIBLE
    }

    private fun resetmetricview() {
        binding?.displayBMIResult?.visibility = View.INVISIBLE
        binding?.heightInput?.text?.clear()
        binding?.weightInput?.text?.clear()
    }
    private fun resetusview() {
        binding?.displayBMIResult?.visibility = View.INVISIBLE
        binding?.heightFeetInput?.text?.clear()
        binding?.heightInchInput?.text?.clear()
        binding?.weightInput?.text?.clear()
    }

    private fun validateMetricUnits(): Boolean {

        if (binding?.weightInput?.text.toString().isEmpty() || binding?.heightInput?.text.toString().isEmpty())
        {
            return false
        }
        return true
    }
    private fun validateUsUnits(): Boolean {
        if (binding?.weightInput?.text.toString().isEmpty() || binding?.heightFeetInput?.text.toString().isEmpty() || binding?.heightInchInput?.text.toString().isEmpty() || binding?.heightInchInput?.text.toString().toFloat() >= 12.0f)
        {
            return false
        }
        return true
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