package mx.tec.loginfire

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import mx.tec.loginfire.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityLoginBinding
    //Progress Dialog
    private lateinit var progressDialog: ProgressDialog
    //fire base Dialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email=""
    private var passwor=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure Progress dialog
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Plaese Wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //Init fireBase
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()

        //open regiter
        binding.createNewAcount.setOnClickListener {
            startActivity(Intent(this,SingUpctivity::class.java))
        }

        //begin Log in
        binding.btnLogIn.setOnClickListener {
            //before log in
            validateData()

        }
       /* val newAcount= findViewById<TextView>(R.id.createNewAcount)

        newAcount.setOnClickListener {
            val intent= Intent(this, SingUpctivity::class.java)
            startActivity(intent)
        }*/

    }

    private fun validateData() {
        email=binding.inputEmail.text.toString().trim()

    }

    private fun checkUser() {
        //if user s already logged
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            startActivity(Intent(this,ProfileActivity::class.java))
            finish()
        }
    }
}