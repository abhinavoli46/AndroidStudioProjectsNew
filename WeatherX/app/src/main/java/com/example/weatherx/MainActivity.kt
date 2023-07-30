package com.example.weatherx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import com.example.weatherx.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchData("Washington")

        citySearch()
    }

    override fun onResume() {
        super.onResume()
        if (binding.searchView != null) {
            binding.searchView.setQuery("", false);
            binding.searchView.clearFocus();
            binding.searchView.onActionViewCollapsed();
        }
    }

    private fun citySearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                fetchData(query!!)
                if (binding.searchView != null) {
                    binding.searchView.setQuery("", false);
                    binding.searchView.clearFocus();
                    binding.searchView.onActionViewCollapsed();
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }

        })

    }

    private fun fetchData(cityName : String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)

        val response = retrofit.getWeatherData("$cityName","0e5b03793433d91f6f56c2ac5032e324","metric")
        response.enqueue(object : Callback<WeatherApp>{
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responseBody = response.body()

                if(response.isSuccessful && responseBody!=null){
                    //Get required items from responsebody
                    val temperature = responseBody.main.temp
                    val humidity = responseBody.main.humidity
                    val windspeed = responseBody.wind.speed
                    val sunRise = responseBody.sys.sunrise.toLong()
                    val sunSet = responseBody.sys.sunset.toLong()
                    val sealevel = responseBody.main.sea_level
                    val condition = responseBody.weather.firstOrNull()?.main?: "Unknown"
                    val maxTemp = responseBody.main.temp_max
                    val minTemp = responseBody.main.temp_min
                    binding.temperatureTextView.text = "$temperature°C"
                    binding.weathertypetextview.text = condition
                    binding.maxtempTextView.text = "Max : $maxTemp°C"
                    binding.mintempTextView.text = "Min : $minTemp°C"
                    binding.humidity1.text = "$humidity %"
                    binding.textViewwindspeed1.text = "$windspeed m/s%"
                    binding.textViewsealevel1.text = "$sealevel hpa"
                    binding.textViewsunrise1.text = "${setTime(sunRise)}"
                    binding.textViewsunset1.text = "${setTime(sunSet)}"
                    binding.textViewrainconditions1.text = "$condition"
                    binding.citynameTextView.text = "$cityName"
                    binding.weekDayTextView.text = setDay(System.currentTimeMillis())
                    binding.dateTextView.text = setDate()

                    changeDisplayToConditions(binding.textViewrainconditions1.text.toString())
                    }
            }

            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun changeDisplayToConditions(conditions : String) {
        when(conditions){
            "Partly Clouds", "Clouds", "Overcast", "Mist", "Foggy" ->{
                binding.root.setBackgroundResource(R.drawable.cloud_background)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)
            }
            "Clear Sky", "Sunny", "Clear" ->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
            "Rain","Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain" ->{
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView.setAnimation(R.raw.rain)
            }
            "Snow","Light Snow", "Moderate Snow", "Heavy Snow", "Blizzard" ->{
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.lottieAnimationView.setAnimation(R.raw.snow)
            }
            else ->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }


        }
        binding.lottieAnimationView.playAnimation()

    }

    private fun setDate(): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    fun setDay(timestamp:Long):String{
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date())
    }

    fun setTime(timestamp:Long):String{
        val sdf = SimpleDateFormat("HH:MM", Locale.getDefault())
        return sdf.format(Date(timestamp*1000))
    }
}