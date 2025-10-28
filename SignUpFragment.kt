package com.example.moviuoc
// Paquete donde se encuentra este fragmento dentro del proyecto MoviUOC.

import android.os.Bundle
import android.view.*
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moviuoc.core.SessionManager
import android.widget.Toast
// Importa las clases necesarias para la interfaz, navegación, manejo de sesión y mensajes.

class SignUpFragment : Fragment() {
    // Fragmento encargado del registro de nuevos usuarios.

    override fun onCreateView(inflater: LayoutInflater, c: ViewGroup?, s: Bundle?): View? {
        // Infla el diseño XML asociado a este fragmento (fragment_sign_up.xml)
        return inflater.inflate(R.layout.fragment_sign_up, c, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Este método se ejecuta cuando la vista del fragmento ya está creada.

        // Se obtienen las referencias a los campos del formulario.
        val etEmail = view.findViewById<TextInputEditText>(R.id.etEmail)
        val etPass  = view.findViewById<TextInputEditText>(R.id.etPass)
        val etName  = view.findViewById<TextInputEditText>(R.id.etName)
        val btnReg  = view.findViewById<Button>(R.id.btnRegistrar)

        // Configura el evento del botón de registro.
        btnReg.setOnClickListener {
            // Se obtienen los valores ingresados por el usuario.
            val email = etEmail.text?.toString()?.trim().orEmpty()
            val pass  = etPass.text?.toString()?.trim().orEmpty()
            val name  = etName.text?.toString()?.trim().orEmpty()

            // Validación básica de los datos ingresados.
            // - El correo debe ser institucional (@duocuc.cl)
            // - La contraseña debe tener al menos 4 caracteres
            // - El nombre no debe estar vacío
            if (email.endsWith("@duocuc.cl") && pass.length >= 4 && name.isNotBlank()) {
                // Si los datos son válidos, se guarda la sesión del usuario.
                SessionManager(requireContext()).login(email, name)

                // Muestra un mensaje confirmando la creación de la cuenta.
                Toast.makeText(requireContext(), "Cuenta creada", Toast.LENGTH_SHORT).show()

                // Redirige al usuario de vuelta al fragmento de inicio de sesión.
                findNavController().navigate(R.id.action_signup_to_login)
            } else {
                // Si los datos no son válidos, muestra un mensaje de advertencia.
                Toast.makeText(
                    requireContext(),
                    "Completa datos válidos (@duocuc.cl, pass ≥4)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
