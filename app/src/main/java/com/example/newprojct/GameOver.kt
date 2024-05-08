package com.example.newprojct

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameOver : AppCompatActivity(){

    // Declare lateinit variables
    private lateinit var tvPoints : TextView
    private lateinit var tvPersonalBest : TextView
    private lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gameover)

        val points = intent.extras?.getInt("points") ?:0
        tvPoints = findViewById(R.id.tvPoints);
        tvPersonalBest = findViewById(R.id.tvPersonalBest);
        tvPoints.text = points.toString()

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("pref", 0)
        // Retrieve the personal best score from SharedPreferences, default to 0 if not found
        var pointsSP = sharedPreferences.getInt("pointsSP",0);


        // Check if the current score is higher than the personal best score
        if (points > pointsSP){
            pointsSP = points
            val  editor = sharedPreferences.edit()// Get SharedPreferences editor
            editor.putInt("pointsSP",pointsSP)// Put the updated score in SharedPreferences
            editor.apply()
        }

        tvPersonalBest.text = pointsSP.toString()
    }

    // restart fun call home page
    fun restart(view: View) {
        val intent = Intent(this, Home::class.java);
        startActivity(intent)
        finish()
    }
    fun Exit(view: View) {
        finish();
    }

}
