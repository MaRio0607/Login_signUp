package mx.tec.loginfire

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import mx.tec.loginfire.databinding.FragmentLoginBinding
import java.util.concurrent.Executor

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!
    private val REQUEST_CODE = 101010;

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private var userEmail = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val biometricManager = activity?.let { BiometricManager.from(it) }
        when (biometricManager?.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                Log.d("MY_APP_TAG_1", "App can authenticate using biometrics.")
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Toast.makeText(activity, "No biometric features available on this device", Toast.LENGTH_LONG).show()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Toast.makeText(activity, "Biometric features are currently unavailable", Toast.LENGTH_LONG).show()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Log.d("MY_APP_TAG_2", "Prompts the user to create credentials that your app accepts.")
            }
        }

        executor = activity?.let { ContextCompat.getMainExecutor(it) }!!
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(activity,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(activity, "Biometrics authentication succeeded!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.LoginToHome, bundleOf("email" to userEmail))
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Log.d("MY_APP_TAG_3", "Authentication failed.")
                    //Toast.makeText(activity, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        binding.btnLogin.setOnClickListener {
            setUp()
            if (userEmail.isNotEmpty()) biometricPrompt.authenticate(promptInfo)
        }
        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.LoginToSingup)
        }
    }

    fun setUp(){
        //Validar campos
        if(binding.inputEmail.text.isNotEmpty() && binding.inputPassword.text.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.inputEmail.text.toString(),
                binding.inputPassword.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    //Toast.makeText(activity, "Usuario registrado " + it.result.user?.email ?: "", Toast.LENGTH_LONG).show()
                    userEmail = it.result.user?.email ?: ""
                } else {
                    showAlert("Error", "User authentication failed")
                }
            }
        }else{
            Toast.makeText(activity,"Fill in all the required fields", Toast.LENGTH_LONG).show()
        }
    }

    fun showAlert(title: String, message: String){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Ok", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}