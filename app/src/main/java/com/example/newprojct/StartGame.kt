package com.example.newprojct

import android.app.PendingIntent.OnFinished
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Collections
import java.util.HashMap
import java.util.ArrayList


class StartGame : AppCompatActivity() {


//those variable declarations in Kotlin
    private lateinit var tvTimer: TextView ;
    private lateinit var tvResult:TextView;
    private lateinit var ivShowImage: ImageView;

    private var map = HashMap<String,Int>(); //store tech name , img
    private var techList = ArrayList<String>(); // store tech name only

    private var index = 0;

    private lateinit var btn1 :Button; // ctrl + D
    private lateinit var btn2 :Button;
    private lateinit var btn3 :Button;
    private lateinit var btn4 :Button;

    private lateinit var tvPoints : TextView;
    private var points = 0;

    private lateinit var countDownTimer: CountDownTimer;
    private var millisUntilFinished : Long = 0; // time limit for each question




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startgame)

        // Initializing views
        tvTimer = findViewById(R.id.tvTimer);
        tvResult = findViewById(R.id.tvResult);
        ivShowImage = findViewById(R.id.ivShowImage);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        tvPoints = findViewById(R.id.tvPoints);

        index = 0;
// initializing tech list with technology name
        techList.add("Bootstrap");
        techList.add("C");
        techList.add("Codeigniter");
        techList.add("Cplusplus");
        techList.add("Csharp");
        techList.add("CSS3");
        techList.add("GitHup");
        techList.add("HTML5");
        techList.add("Java");
        techList.add("JQuery");
        techList.add("MYSQL");
        techList.add("NodeJs");
        techList.add("PHP");
        techList.add("Python");
        techList.add("Wordpress");
        techList.add("Android");

        // put in map with id
        map[techList[0]] =R.drawable.bootstap;
        map[techList[1]] = R.drawable.c;
        map[techList[2]] = R.drawable.codeigniter;
        map[techList[3]] = R.drawable.cplusplus;
        map[techList[4]] = R.drawable.cshap;
        map[techList[5]] = R.drawable.css3;
        map[techList[6]] = R.drawable.github;
        map[techList[7]] = R.drawable.html5;
        map[techList[8]] = R.drawable.java;
        map[techList[9]] = R.drawable.jquery;
        map[techList[10]] = R.drawable.mysql;
        map[techList[11]] = R.drawable.node;
        map[techList[12]] = R.drawable.php;
        map[techList[13]] = R.drawable.python;
        map[techList[14]] = R.drawable.wordpress;
        map[techList[15]] = R.drawable.android;

        techList.shuffle(); //random question start
        millisUntilFinished = 10000; //time limit 10s for each question

        points = 0;
        startGame();
    }

    private fun startGame() {
        //resetting time limit to 10 secounds
        millisUntilFinished = 10000;
        //display time
        tvTimer.text = "" + (millisUntilFinished/ 10000) + "s"
        generateQuestion(index);

        //handle the countdown
        countDownTimer = object : CountDownTimer(millisUntilFinished, 1000){
            override fun onTick(millisUntilFinished: Long){
                //updating the timer every tick
                tvTimer.text = "${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                //handling when the countdown finishes
                index++;
                if (index > techList.size-1){
                    ivShowImage.visibility = View.GONE
                    btn1.visibility = View.GONE;
                    btn2.visibility = View.GONE;
                    btn3.visibility = View.GONE;
                    btn4.visibility = View.GONE;

                    val intent = Intent(this@StartGame, GameOver::class.java)
                    intent.putExtra("points",points);
                    startActivity(intent)

                    finish()
                }else{
                    //if there are more question, continue the game
                    startGame();
                }
            }
        }.start()
    }
// question generate panna
    private fun generateQuestion(index: Int) {

        //create a temporary copy of te techList
        val techListTemp = ArrayList<String>().apply { addAll(techList) }
        val correctAnswer = techList[index];

        // Remove the correct answer from the temporary list
        techListTemp.remove(correctAnswer);
        techListTemp.shuffle();// shuffle the temporary list


    // Create a new list containing the correct answer and three incorrect answers
        val newList = ArrayList<String>()
        newList.add(techListTemp[0]);
        newList.add(techListTemp[1]);
        newList.add(techListTemp[2]);
        newList.add(correctAnswer);
        newList.shuffle();

    // Set the text of the buttons with the shuffled answers
        btn1.text= newList[0];
        btn2.text= newList[1];
        btn3.text= newList[2];
        btn4.text= newList[3];

    // Set the image resource for the question
        ivShowImage.setImageResource(map[techList[index]]!!)

    }


    fun nextQuestion(view: View) {
        countDownTimer.cancel();   // Cancelling the countdown timer
        index++;// moving the next question

        if(index >techList.size -1){
            ivShowImage.visibility = View.GONE;
            btn1.visibility = View.GONE;
            btn2.visibility = View.GONE;
            btn3.visibility = View.GONE;
            btn4.visibility = View.GONE;


            val intent = Intent(this, GameOver::class.java)
            intent.putExtra("points", points)
            startActivity(intent);

            finish();

        }else{
            startGame();
        }
    }


    fun answerSelected(view: View) {
      // Retrieving the text of the selected button
    val answer = (view as Button) . text. toString() . trim()
        // Retrieving the correct answer for the current question
        val correctAnswer = techList[index];

        if (answer == correctAnswer){
            // If the answer is correct, increment points, update points TextView, and set result TextView
            points++

            tvPoints.text="$points/${techList.size}"
            tvResult.text = "Correct"
            index++;
        }else{
            tvResult.text ="Wrong Answer"
        }
    }
//fun answerSelected(view: View) {
//    // Retrieving the text of the selected button
//    val answer = (view as Button).text.toString().trim()
//    // Retrieving the correct answer for the current question
//    val correctAnswer = techList[index]
//
//    if (answer == correctAnswer) {
//        // If the answer is correct, increment points, update points TextView, and set result TextView
//        points++
//        tvPoints.text = "$points/${techList.size}"
//        tvResult.text = "Correct"
//        index++ // Move to the next question
//
//        if (index < techList.size) {
//            // If there are more questions, start the next question
//            startGame()
//        } else {
//            // If there are no more questions, end the game
//            GameOver()
//        }
//    } else {
//        tvResult.text = "Wrong Answer"
//    }
//}

}