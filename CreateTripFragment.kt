package com.example.moviuoc
//  Paquete principal donde se encuentra este fragmento.

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.moviuoc.data.ViajeEntity
import com.example.moviuoc.ui.viaje.ViajeViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
//  Importa todas las librerías necesarias para manejo de vistas, ViewModel y validación de datos.

class CreateTripFragment : Fragment() {
    //  Fragmento que permite a un conductor crear un nuevo viaje.

    private val vm: ViajeViewModel by viewModels()
    //  ViewModel que maneja la lógica de datos y conexión con la base de datos (Room).

    override fun onCreateView(
        i: LayoutInflater, c: ViewGroup?, s: Bundle?
    ): View = i.inflate(R.layout.fragment_create_trip, c, false)
    //  Infla el diseño visual XML que contiene los campos de entrada y el botón para crear el viaje.

    override fun onViewCreated(view: View, s: Bundle?) {
        super.onViewCreated(view, s)
        //  Se ejecuta después de crear la vista: aquí se inicializan los componentes y eventos.

        //  Referencias a los campos de entrada (TextInputLayouts y EditTexts)
        val tilFecha: TextInputLayout = view.findViewById(R.id.tilFecha)
        val etFecha: TextInputEditText = view.findViewById(R.id.etFecha)
        val tilCupos: TextInputLayout = view.findViewById(R.id.tilCupos)
        val etCupos: TextInputEditText = view.findViewById(R.id.etCupos)
        val tilOrigen: TextInputLayout = view.findViewById(R.id.tilOrigen)
        val etOrigen: TextInputEditText = view.findViewById(R.id.etOrigen)
        val tilDestino: TextInputLayout = view.findViewById(R.id.tilDestino)
        val etDestino: TextInputEditText = view.findViewById(R.id.etDestino)
        val tilPrecio: TextInputLayout = view.findViewById(R.id.tilPrecio)
        val etPrecio: TextInputEditText = view.findViewById(R.id.etPrecio)

        val btnCrear: Button = view.findViewById(R.id.btnCrear)
        //  Botón que guardará el viaje cuando se presione.

        //  Configuración inicial de fecha y hora actual
        val ahora = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        if (etFecha.text.isNullOrBlank()) {
            etFecha.setText(sdf.format(ahora.time))
        }
        // Si el campo fecha está vacío, se coloca la fecha actual automáticamente.

        btnCrear.setOnClickListener {
            //  Se ejecuta al presionar el botón “Crear”.

            // Validaciones básicas de los campos
            var ok = true
            fun err(til: TextInputLayout, msg: String) {
                til.error = msg
                ok = false
            }

            //  Limpia los errores anteriores
            tilFecha.error = null
            tilCupos.error = null
            tilOrigen.error = null
            tilDestino.error = null
            tilPrecio.error = null

            //  Obtiene los valores ingresados por el usuario
            val fechaStr = etFecha.text?.toString()?.trim().orEmpty()
            val cuposStr = etCupos.text?.toString()?.trim().orEmpty()
            val origen = etOrigen.text?.toString()?.trim().orEmpty()
            val destino = etDestino.text?.toString()?.trim().orEmpty()
            val precioStr = etPrecio.text?.toString()?.trim().orEmpty()

            //  Comprueba que no estén vacíos
            if (fechaStr.isEmpty()) err(tilFecha, "Requerido")
            if (cuposStr.isEmpty()) err(tilCupos, "Requerido")
            if (origen.isEmpty()) err(tilOrigen, "Requerido")
            if (destino.isEmpty()) err(tilDestino, "Requerido")
            if (precioStr.isEmpty()) err(tilPrecio, "Requerido")

            //  Convierte la fecha ingresada a milisegundos
            val fechaMillis = runCatching {
                sdf.parse(fechaStr)!!.time
            }.getOrElse {
                err(tilFecha, "Fecha inválida (usa dd/MM/yyyy HH:mm)")
                -1L
            }

            // 👥 Convierte los cupos y precio a números enteros
            val cupos = cuposStr.toIntOrNull() ?: run {
                err(tilCupos, "Debe ser número")
                -1
            }

            val precio = precioStr.toIntOrNull() ?: run {
                err(tilPrecio, "Debe ser número")
                -1
            }

            //  Si hay errores, se detiene la creación del viaje
            if (!ok) return@setOnClickListener

            //  Crea un objeto ViajeEntity y lo guarda en la base de datos (Room)
            val viaje = ViajeEntity(
                fechaMillis = fechaMillis,
                cupos = cupos,
                origen = origen,
                destino = destino,
                precio = precio
            )
            vm.insert(viaje)
            // Se llama al método insert() del ViewModel para guardar el viaje.

            //  Vuelve a la pantalla anterior después de guardar.
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}
