package mx.tec.loginfire

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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

        binding.txtLogin.setOnClickListener {
            findNavController().navigate(R.id.SingupToLogin)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}