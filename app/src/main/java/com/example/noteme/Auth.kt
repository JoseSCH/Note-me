package com.example.noteme

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.noteme.databinding.ActivityAuthBinding
import com.example.noteme.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Suppress("DEPRECATION")
class Auth : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private val GOOGLE_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setup()
        session()
    }

    private fun setup(){

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
                            Toast.makeText(this, "Error al registrar", Toast.LENGTH_LONG).show()
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
                        Toast.makeText(this, "Error al acceder", Toast.LENGTH_LONG).show()
                    }

                }

            }
        }

        binding.googleButton.setOnClickListener {

            // conf
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)

        }

    }

    private fun showAll(email: String, provider: ProviderType) {
        val mainIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.toString())
        }

        startActivity(mainIntent)
        finish()
    }
    private fun session(){

        val prefs = getSharedPreferences("Data", Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if(email != null && provider != null){
            binding.linearXD.visibility = View.INVISIBLE
            showAll(email, ProviderType.valueOf(provider))
        }

    }

    override fun onRestart() {
        super.onRestart()

        val prefs = getSharedPreferences("Data", Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if(email != null && provider != null){
            binding.linearXD.visibility = View.INVISIBLE
            showAll(email, ProviderType.valueOf(provider))
        }else{
            binding.linearXD.visibility = View.VISIBLE
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try{
                val account = task.getResult(ApiException::class.java)

                if(account != null) {

                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{

                        if(it.isSuccessful){
                            showAll(account.email ?: "", ProviderType.GOOGLE)
                        }else{
                            Toast.makeText(this, "Error al ingresar con la cuenta", Toast.LENGTH_LONG).show()
                        }

                    }
                }
            }catch (e: ApiException){
                Toast.makeText(this, "Error al obtener datos", Toast.LENGTH_LONG).show()
            }
        }
    }
}