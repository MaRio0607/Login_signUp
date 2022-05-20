package mx.tec.loginfire

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import mx.tec.loginfire.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            setUp()
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
                    val toast = Toast.makeText(activity, "Bienvenido " + it.result.user?.email ?: "", Toast.LENGTH_LONG)
                    toast.show()
                    findNavController().navigate(R.id.LoginToHome)
                } else {
                    showAlert("Error", "Se ha producido un error autenticando al usuario")
                }
            }
        }else{
            val errorEmpty = Toast.makeText(activity,"Completa todos los campos", Toast.LENGTH_LONG)
            errorEmpty.show()
        }
    }

    fun showAlert(title: String, message: String){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}