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
import mx.tec.loginfire.databinding.FragmentSingupBinding

class Singup : Fragment() {

    private var _binding: FragmentSingupBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            setUp()
        }
        binding.txtLogin.setOnClickListener {
            findNavController().navigate(R.id.SingupToLogin)
        }
    }

    fun setUp(){
        //Validar campos
        if(binding.inputEmail.text.isNotEmpty() && binding.inputPassword.text.isNotEmpty() && binding.inputConfirmPassword.text.isNotEmpty()) {
            //Validar contraseña
            if(binding.inputPassword.text.toString() == binding.inputConfirmPassword.text.toString()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.inputEmail.text.toString(),
                    binding.inputPassword.text.toString()
                ).addOnCompleteListener{
                    if(it.isSuccessful){
                        val toast = Toast.makeText(activity,"Se registró correctamente", Toast.LENGTH_LONG)
                        toast.show()
                        findNavController().navigate(R.id.SingupToLogin)
                    }else{
                        showAlert("Error", "Se ha producido un error registrando al usuario")
                    }
                }
            }else{
                val errorPassword = Toast.makeText(activity,"Verifica tu contraseña", Toast.LENGTH_LONG)
                errorPassword.show()
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