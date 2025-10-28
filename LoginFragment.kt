package com.example.moviuoc
// Define el paquete del proyecto donde se encuentra este fragmento.

import android.os.Bundle
import android.view.*
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moviuoc.core.SessionManager
import android.widget.Toast
// Importa las clases necesarias para manejar vistas, navegación y mensajes emergentes (Toast).

class LoginFragment : Fragment() {
    // Fragmento encargado del inicio de sesión de usuarios registrados.

    override fun onCreateView(inflater: LayoutInflater, c: ViewGroup?, s: Bundle?): View? {
        // Infla el diseño XML que define la interfaz del inicio de sesión (fragment_login.xml)
        return inflater.inflate(R.layout.fragment_login, c, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Este método se ejecuta cuando la vista ya fue creada y es visible.

        val session = SessionManager(requireContext())
        // Se crea una instancia del SessionManager para manejar el estado de la sesión del usuario.

        if (session.isLogged()) {
            // Si el usuario ya tiene una sesión iniciada, se lo redirige directamente al menú principal.
            findNavController().navigate(R.id.action_login_to_home)
            return
        }

        // Obtiene las referencias a los elementos de la interfaz.
        val etEmail = view.findViewById<TextInputEditText>(R.id.etEmail)
        val etPass  = view.findViewById<TextInputEditText>(R.id.etPass)
        val btnCont = view.findViewById<Button>(R.id.btnContinuar)
        val btnSign = view.findViewById<Button>(R.id.btnIrRegistro)

        // Configura el evento del botón "Continuar" (inicio de sesión).
        btnCont.setOnClickListener {
            // Obtiene los datos ingresados por el usuario.
            val email = etEmail.text?.toString()?.trim().orEmpty()
            val pass  = etPass.text?.toString()?.trim().orEmpty()

            // Valida que el correo sea institucional y que la contraseña tenga al menos 4 caracteres.
            if (email.endsWith("@duocuc.cl") && pass.length >= 4) {

                // Si el usuario ya estaba registrado, se recupera su nombre.
                // Si no, se asigna un nombre genérico.
                val name = if (session.name().isNotBlank()) session.name() else "Estudiante DUOC"

                // Inicia sesión guardando los datos en SessionManager.
                session.login(email, name)

                // Redirige al fragmento principal (HomeFragment).
                findNavController().navigate(R.id.action_login_to_home)
            } else {
                // Muestra un mensaje si los datos ingresados no son válidos.
                Toast.makeText(
                    requireContext(),
                    "Email @duocuc.cl y clave ≥4",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Configura el evento del botón "Ir a registro".
        btnSign.setOnClickListener {
            // Navega al fragmento de registro (SignUpFragment).
            findNavController().navigate(R.id.action_login_to_signup)
        }
    }
}
