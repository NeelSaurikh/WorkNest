package com.example.worknest.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.worknest.R
import com.example.worknest.databinding.ActivitySignUpBinding
import com.example.worknest.firebase.FirestoreClass
import com.example.worknest.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : BaseActivity() {
//    private lateinit var binding: ActivitySignUpBinding
    private lateinit var toolbarSignUpActivity: androidx.appcompat.widget.Toolbar
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize views using findViewById
        toolbarSignUpActivity = findViewById(R.id.toolbar_sign_up_activity)
        etName = findViewById(R.id.et_name)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnSignUp = findViewById(R.id.btn_sign_up)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setupActionBar()
    }


    private fun setupActionBar() {
        setSupportActionBar(toolbarSignUpActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        toolbarSignUpActivity.setNavigationOnClickListener { onBackPressed() }

        btnSignUp.setOnClickListener {
            registerUser()
        }
    }


    fun userRegisteredSuccess() {
        Toast.makeText(this@SignUpActivity, "You have successfully registered.", Toast.LENGTH_LONG).show()
        hideProgressDialog()
        FirebaseAuth.getInstance().signOut()
        finish()
    }
    private fun registerUser(){
        val name : String = etName.text.toString().trim(){it <= ' '}
        val email : String = etEmail.text.toString().trim(){it <= ' '}
        val password : String = etPassword.text.toString().trim(){it <= ' '}
        if(validateForm(name,email,password)){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                    {
                        task->
                            if(task.isSuccessful){
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            val registeredEmail = firebaseUser.email!!
                            val user = User(firebaseUser.uid,name,registeredEmail)
                            FirestoreClass().registerUser(this, user)

                            }else{
                            Toast.makeText(this,
                                "Registration Failed: ${task.exception!!.message}",Toast.LENGTH_SHORT)
                        }
                    }
               )
        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter name.")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email address.")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter password.")
                false
            }
            else -> {
                true
            }
        }
    }
}