package com.example.moviuoc
// 📦 Define el paquete donde está esta clase, dentro del proyecto MoviUOC.

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.moviuoc.core.SessionManager
//  Importa todas las librerías necesarias para manejar vistas, permisos y la cámara.

class ProfileFragment : Fragment() {
    // Fragmento que muestra el perfil del usuario con su nombre, email y foto.

    private lateinit var img: ImageView
    //  Imagen donde se mostrará la foto del usuario.

    private lateinit var session: SessionManager
    //  Maneja la información del usuario guardada en la sesión (nombre, email, foto, etc).

    //  Contracto de resultado para abrir la cámara y tomar una foto.
    private val takePhoto =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bmp ->
            //  Este bloque se ejecuta cuando el usuario toma una foto.
            if (bmp != null) {
                //  Guardamos la foto en almacenamiento interno con ayuda del SessionManager.
                val path = session.savePhoto(requireContext(), bmp)

                // Mostramos la imagen recién tomada en pantalla.
                img.setImageBitmap(BitmapFactory.decodeFile(path))
            }
        }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View? {
        // Infla el layout XML que define la interfaz del fragmento.
        // En este caso, carga "fragment_profile.xml"
        return i.inflate(R.layout.fragment_profile, c, false)
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)
        // Se ejecuta después de que la vista fue creada.
        // Ideal para inicializar variables y configurar la lógica de la pantalla.

        session = SessionManager(requireContext())
        //Instanciamos el administrador de sesión para acceder a los datos guardados.

        // Conectamos las variables del código con los elementos del XML.
        img = v.findViewById(R.id.imgFoto)
        val tvNombre = v.findViewById<TextView>(R.id.tvNombre)
        val tvEmail  = v.findViewById<TextView>(R.id.tvEmail)
        val btnFoto  = v.findViewById<Button>(R.id.btnTomarFoto)

        //  Mostramos los datos guardados del usuario.
        tvNombre.text = "Nombre: ${session.name()}"
        tvEmail.text  = "Email: ${session.email()}"

        //  Si ya hay una foto guardada, la cargamos desde el almacenamiento.
        val p = session.photoPath()
        if (p.isNotBlank()) {
            img.setImageBitmap(BitmapFactory.decodeFile(p))
        }

        //  Configuramos el botón para tomar una foto con la cámara.
        btnFoto.setOnClickListener {
            //  Verifica si el permiso de cámara ya está concedido.
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
                //  Si ya tiene permiso, abre la cámara.
                takePhoto.launch(null)
            } else {
                //  Si no tiene permiso, lo solicita al usuario.
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), 1001)
            }
        }
    }
}
