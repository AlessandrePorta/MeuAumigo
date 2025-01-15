package com.example.meuaumigo.ui.homemain

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.ActivityMainBinding
import com.example.meuaumigo.ui.profile.ProfileUpdateNameDirections
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var firebaseRef: DatabaseReference

    private lateinit var fireStorage: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseRef = FirebaseDatabase.getInstance().getReference("UserData")
        fireStorage = FirebaseStorage.getInstance().getReference("UserImage")
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
                        task.exception?.message.toString(),
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                    showLoading(false)
                }
            }
    }

    fun signIn(email: String, password: String) {
        showLoading(true)
        val user = auth.currentUser
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    updateUI(user)
                    navController().navigate(R.id.action_loginFragment_to_needAHomeFragment)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        task.exception?.message.toString(),
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

    fun updateUserName(name: String?) {
        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }
        auth.currentUser!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.uid?.let {
                        firebaseRef.child(it).child("name").setValue(name)
                            .addOnCompleteListener {
                                Toast.makeText(this, "Nome alterado com sucesso!", Toast.LENGTH_SHORT).show()
                                navController().popBackStack()
                            }
                            .addOnFailureListener {
                                showLoading(false)
                                Toast.makeText(this, "Algo deu errado na atualização: " + it.message, Toast.LENGTH_SHORT).show()
                                navController().popBackStack()
                            }
                    }
                }
            }

    }


    fun reload() {
        auth.currentUser!!.reload().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                updateUI(auth.currentUser)
                Log.d(TAG, "Reload successful!")
            }
        }
    }

    private fun logOut() {
        auth.signOut().apply {
            navController().navigate(R.id.onboardingFragment)
        }
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

    fun showLoading(isLoaded: Boolean) {
        binding.pbLoad.isVisible = isLoaded
        binding.fcFragment.isVisible = !isLoaded
    }

    override fun onBackPressed() {
        if (navController().currentDestination?.id == R.id.loginFragment) {
            navController().popBackStack()
        } else if (navController().currentDestination?.id == R.id.registerFragment) {
            navController().popBackStack()
        } else if (navController().currentDestination?.id == R.id.needAHomeFragment) {
            logOut()
        } else if (navController().currentDestination?.id == R.id.homeProfileFragment) {
            navController().popBackStack()
        } else if (navController().currentDestination?.id == R.id.lookingForHomeFragment) {
            navController().popBackStack()
        } else if (navController().currentDestination?.id == R.id.petDetailsFragment) {
            navController().popBackStack()
        } else if (navController().currentDestination?.id == R.id.onboardingFragment) {
            finish()
        } else if (navController().currentDestination?.id == R.id.profileUpdateName) {
            navController().popBackStack()
        }
    }
}