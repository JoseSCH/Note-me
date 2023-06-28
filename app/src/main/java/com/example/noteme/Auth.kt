package com.example.noteme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.noteme.databinding.ActivityAuthBinding
import com.example.noteme.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth

class Auth : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setup()

        /*binding.signInButton.setOnClickListener {
            val googleConfig = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this, googleConfig)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, 100)
            finish()
        }*/
    }

    fun setup(){

        supportActionBar?.apply {
            title = "Login"
            elevation = 0f
        }

        binding.B1.setOnClickListener{
            if (binding.emailText.text.isNotEmpty() && binding.passwordText.text.isNotEmpty()){

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.emailText.text.toString(),
                    binding.passwordText.text.toString()).addOnCompleteListener{

                        if (it.isSuccessful){
                            showAll(it.result?.user?.email ?: "", ProviderType.BASIC)
                        }else{
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        }

                }

            }
        }

        binding.B2.setOnClickListener{
            if (binding.emailText.text.isNotEmpty() && binding.passwordText.text.isNotEmpty()){

                FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.emailText.text.toString(),
                    binding.passwordText.text.toString()).addOnCompleteListener{

                    if (it.isSuccessful){
                        showAll(it.result?.user?.email ?: "", ProviderType.BASIC)
                    }else{
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                    }

                }

            }
        }

    }

    fun showAll(email: String, provider: ProviderType) {
        val mainIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider)
        }

        startActivity(mainIntent)
    }
}