package com.company.moviemania.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.company.moviemania.R

class SignInActivity : AppCompatActivity() {
  private lateinit var signIn: Button
  private lateinit var editTextUsername: EditText
  private lateinit var editTextPassword: EditText

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_sign_in)
    initView()
  }

  private fun initView() {
    editTextUsername = findViewById(R.id.editTextUserName)
    editTextPassword = findViewById(R.id.editTextUserPassword)
    signIn = findViewById(R.id.sign_in)
    signIn.setOnClickListener(
      View.OnClickListener {
        if (
          editTextUsername.getText().toString().isEmpty() ||
            editTextPassword.getText().toString().isEmpty()
        ) {
          Toast.makeText(this@SignInActivity, "Please enter data", Toast.LENGTH_SHORT).show()
        } else if (
          editTextUsername.getText().toString() == "admin" &&
            editTextPassword.getText().toString() == "admin"
        ) {
          startActivity(Intent(this@SignInActivity, MainActivity::class.java))
          finish()
        } else {
          Toast.makeText(
              this@SignInActivity,
              "Your username or password is incorrect",
              Toast.LENGTH_SHORT,
            )
            .show()
        }
      }
    )
  }
}
