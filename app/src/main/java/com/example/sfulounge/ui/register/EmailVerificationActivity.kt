package com.example.sfulounge.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sfulounge.databinding.ActivityEmailVerificationBinding
import com.example.sfulounge.ui.setup.SetupBasicInfoActivity

class EmailVerificationActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityEmailVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityEmailVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel = ViewModelProvider(this, RegisterModelFactory())
            .get(RegisterViewModel::class.java)

        registerViewModel.retryEmailResult.observe(this, Observer {
            val verResult = it ?: return@Observer

            if (verResult.error != null) {
                showVerificationFailed(verResult.error)
            } else {
                showVerificationEmailSent()
            }
        })
        registerViewModel.verificationResult.observe(this) {
            val result = it ?: return@observe
            if (result.error != null) {
                showVerificationFailed(result.error)
            } else {
                onEmailVerificationSuccessful()
            }
        }

        val resend = binding.resendEmail
        val nextBtn = binding.next

        nextBtn.setOnClickListener {
            registerViewModel.verifyUser()
        }
        resend.setOnClickListener {
            registerViewModel.retrySendVerificationEmail()
        }
    }

    /**
     * wiring to activities
     */
    private fun onEmailVerificationSuccessful() {
        startActivity(Intent(this, SetupBasicInfoActivity::class.java))
        finish()
    }

    /**
     * UI
     */
    private fun showVerificationFailed(@StringRes errorString: Int) {
        Toast.makeText(this, getString(errorString), Toast.LENGTH_SHORT).show()
    }

    private fun showVerificationEmailSent() {
        Toast.makeText(this, "Verification email sent!", Toast.LENGTH_SHORT)
            .show()
    }
}