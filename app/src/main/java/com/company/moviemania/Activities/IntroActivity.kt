package com.company.moviemania.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.company.moviemania.R

class IntroActivity : AppCompatActivity() {
    lateinit var getStarted: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        getStarted = findViewById(R.id.get_started)
        getStarted.setOnClickListener(View.OnClickListener {
            val i = Intent(this@IntroActivity, SignInActivity::class.java)
            startActivity(i)
            finish()
        })
    }
}