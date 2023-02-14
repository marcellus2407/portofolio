package com.dicoding.picodiploma.storyapp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.dicoding.picodiploma.storyapp.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

    class RegisterActivity : AppCompatActivity() {
        companion object {
            private const val TAG = "Register Activity"
        }

        private lateinit var binding: ActivityRegisterBinding
        private lateinit var myEditText: MyEditText

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityRegisterBinding.inflate(layoutInflater)
            setContentView(binding.root)


            binding.signupButton.setOnClickListener {
                val inputName = binding.nameEditText.text.toString()
                val inputEmail = binding.emailEditText.text.toString()
                val inputPassword = binding.passwordEditText.text.toString()

                makeAccount(inputName, inputEmail, inputPassword)

            }
                myEditText = findViewById(R.id.passwordEditText)
                playAnimation()


        }

            private fun playAnimation() {
                val nameTextView =
                    ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
                val nameEditLayout =
                    ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f)
                        .setDuration(500)
                val emailTextView =
                    ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
                val emailEditTextLayout =
                    ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f)
                        .setDuration(500)
                val passwordTextView =
                    ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f)
                        .setDuration(500)
                val passwordEditTextLayout =
                    ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f)
                        .setDuration(500)
                val register =
                    ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)

                AnimatorSet().apply {
                    playSequentially(
                        nameTextView,
                        nameEditLayout,
                        emailTextView,
                        emailEditTextLayout,
                        passwordTextView,
                        passwordEditTextLayout,
                        register
                    )
                    startDelay = 500
                }.start()

            }
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when(item.itemId) {
                R.id.menu_language -> {
                    val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                    startActivity(intent)
                    return true
                }
            }
            return true
        }
        private fun makeAccount(inputName: String, inputEmail: String, inputPassword: String) {
            val client =
                ApiConfig.getApiService().registerUser(inputName, inputEmail, inputPassword)
            client.enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    val responseBody = response.body()
                    Log.d(TAG, "onResponse: $responseBody")
                    if (response.isSuccessful && responseBody?.message == "User created") {
                        Toast.makeText(
                            this@RegisterActivity,
                            getString(R.string.registersuccess),
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.e(TAG, "onFailure1: ${response.message()}")
                        Toast.makeText(
                            this@RegisterActivity,
                            getString(R.string.registerfail),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure2: ${t.message}")
                    Toast.makeText(
                        this@RegisterActivity,
                        getString(R.string.registerfail),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        }
    }

