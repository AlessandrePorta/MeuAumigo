package com.example.meuaumigo.ui.homemain

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        FirebaseApp.initializeApp(this)
        auth = Firebase.auth
        init()
        showLoading(false)
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun init() {
        navController().navigate(R.id.onboardingFragment)
    }

    fun createAccount(email: String, password: String, name: String) {
        showLoading(true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext,
                        "Conta criada com sucesso!",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(user)
                    updateUser(user, name)
                    navController().popBackStack()
                    showLoading(false)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "A senha deve ter no mínimo 6 caracteres",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                    showLoading(false)
                }
            }
    }

    fun signIn(email: String, password: String) {
        showLoading(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    navController().navigate(R.id.action_loginFragment_to_needAHomeFragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Login ou senha incorretos",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                    showLoading(false)
                }
            }
    }

    private fun navController() =
        ((supportFragmentManager.findFragmentById(R.id.fcFragment)) as NavHostFragment).navController

    private fun updateUI(user: FirebaseUser?) {
    }

    private fun updateUser(user: FirebaseUser?, name: String?) {
        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }

    }

    private fun reload() {
    }

    fun logOut() {
        auth.signOut()
        navController().popBackStack()
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

    fun showLoading(isLoaded : Boolean){
        binding.pbLoad.isVisible = isLoaded
        binding.fcFragment.isVisible = !isLoaded
    }

    override fun onBackPressed() {
        navController().popBackStack()
    }
}