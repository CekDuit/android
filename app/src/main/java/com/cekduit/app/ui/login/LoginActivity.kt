package com.cekduit.app.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cekduit.app.data.pref.UserModel
import com.cekduit.app.databinding.ActivityLoginBinding
import com.cekduit.app.ui.main.MainActivity
import com.cekduit.app.utils.ViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlin.getValue

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Check if user is already signed in
        GoogleSignIn.getLastSignedInAccount(this)?.let {
            navigateToMainActivity(it.displayName)
        }

        binding.googleSigninButton.setOnClickListener {
            startGoogleSignIn()
        }
    }

    private fun startGoogleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            try {
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
                navigateToMainActivity(account.displayName)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMainActivity(accountName: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            accountName?.let { putExtra("ACCOUNT_NAME", it) }
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            viewModel.saveSession(UserModel(email, "sample_token"))
            showSuccessDialog()
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Success!")
            setMessage("Login berhasil! Ayo mulai cek keuangan Anda sekarang!")
            setPositiveButton("Lanjut") { _, _ ->
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val animators = listOf(
            binding.titleTextView,
            binding.messageTextView,
            binding.emailTextView,
            binding.emailEditTextLayout,
            binding.passwordTextView,
            binding.passwordEditTextLayout,
            binding.loginButton,
            binding.googleSigninButton
        ).map { view ->
            ObjectAnimator.ofFloat(view, View.ALPHA, 1f).setDuration(100)
        }

        AnimatorSet().apply {
            playSequentially(animators)
            startDelay = 100
        }.start()
    }

    companion object {
        private const val RC_SIGN_IN = 1000
    }
}