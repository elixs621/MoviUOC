package com.example.moviuoc
// Define el paquete donde se encuentra este fragmento dentro del proyecto MoviUOC.

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.moviuoc.core.SessionManager
// Importa las librerías necesarias para manejo de vistas, navegación y control de sesión.

class HomeFragment : Fragment() {
    // Fragmento principal que actúa como menú de inicio de la aplicación.

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View? {
        // Infla el diseño visual correspondiente a este fragmento (fragment_home.xml)
        return i.inflate(R.layout.fragment_home, c, false)
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)
        // Este método se ejecuta después de que la vista fue creada. Aquí se configuran los botones y eventos.

        // Se obtienen las referencias a los botones definidos en el layout.
        val btnCrear = v.findViewById<Button>(R.id.btnCrearViaje)
        val btnBuscar = v.findViewById<Button>(R.id.btnBuscarViaje)
        val btnPerfil = v.findViewById<Button>(R.id.btnPerfil)
        val btnLogout = v.findViewById<Button>(R.id.btnCerrarSesion)

        // Configura la navegación para el botón "Crear Viaje".
        btnCrear.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_createTrip)
        }

        // Configura la navegación para el botón "Buscar Viaje".
        btnBuscar.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_searchTrip)
        }

        // Configura la navegación para el botón "Perfil".
        btnPerfil.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_profile)
        }

        // Configura el botón "Cerrar Sesión".
        btnLogout.setOnClickListener {
            // Cierra la sesión actual eliminando los datos del usuario.
            SessionManager(requireContext()).logout()

            // Muestra un mensaje confirmando que la sesión se cerró correctamente.
            Toast.makeText(requireContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show()

            // Configura las opciones de navegación para limpiar el historial (back stack)
            // y volver directamente al fragmento de inicio de sesión.
            val opts = NavOptions.Builder()
                .setPopUpTo(R.id.LoginFragment, inclusive = true)
                .build()

            // Navega hacia el fragmento Login y elimina las pantallas previas de la pila.
            findNavController().navigate(R.id.LoginFragment, null, opts)
        }
    }
}
