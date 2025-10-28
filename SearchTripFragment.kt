package com.example.moviuoc
// Define el paquete donde se encuentra este fragmento dentro del proyecto MoviUOC.

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviuoc.ui.viaje.ViajeAdapter
import com.example.moviuoc.ui.viaje.ViajeViewModel
// Importa las librerías necesarias para manejar vistas, RecyclerView y ViewModel.

class SearchTripFragment : Fragment() {
    // Fragmento encargado de mostrar la lista de viajes disponibles almacenados en la base de datos.

    private val vm: ViajeViewModel by viewModels()
    // ViewModel que gestiona la información de los viajes, recuperándolos desde la base de datos Room.

    private val adapter = ViajeAdapter()
    // Adaptador personalizado que enlaza los datos de los viajes con el RecyclerView.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_search_trip, container, false)
    // Infla el diseño XML correspondiente al fragmento (fragment_search_trip.xml)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Este método se ejecuta cuando la vista está completamente creada.
        // Aquí se configuran los elementos visuales y la lógica de presentación.

        // Obtiene la referencia al RecyclerView del layout.
        val rv = view.findViewById<RecyclerView>(R.id.rvViajes)

        // Configura el tipo de disposición de los elementos (una lista vertical).
        rv.layoutManager = LinearLayoutManager(requireContext())

        // Asigna el adaptador que mostrará los datos de los viajes.
        rv.adapter = adapter

        // Añade una línea divisoria entre los elementos para mejorar la visualización.
        rv.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )

        // Observa los cambios en la lista de viajes gestionada por el ViewModel.
        vm.viajes.observe(viewLifecycleOwner) { lista ->
            // Cuando la base de datos cambia, el adaptador actualiza la lista en pantalla.
            adapter.submitList(lista)
        }
    }
}
