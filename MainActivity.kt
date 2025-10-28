package com.example.moviuoc
// Paquete principal del proyecto MoviUOC. 
// Agrupa todas las clases relacionadas a la app.

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
//  Importamos las librerías necesarias:
// - AppCompatActivity: clase base para actividades modernas.
// - NavHostFragment y setupActionBarWithNavController: usados para manejar navegación entre fragmentos.

class MainActivity : AppCompatActivity() {
    //  Esta es la actividad principal que controla la navegación y la vista principal de la app.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //  Vincula el diseño visual "activity_main.xml" con esta actividad.
        // Es la pantalla raíz que contiene el contenedor (NavHostFragment).

        // Forma segura: obtenemos el NavController desde el NavHostFragment
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        //  Buscamos el fragmento que actúa como “contenedor de navegación”.
        // El ID "nav_host" debe estar definido en el XML activity_main.xml.

        val navController = navHost.navController
        //  Obtenemos el controlador de navegación, que permite movernos entre pantallas (fragments).

        setupActionBarWithNavController(navController)
        //  Vinculamos el ActionBar (barra superior) con el controlador de navegación.
        // Esto permite que aparezca automáticamente el botón “Atrás” al cambiar de fragmento.
    }

    override fun onSupportNavigateUp(): Boolean {
        //  Se ejecuta cuando el usuario presiona el botón “Atrás” en la barra de acción.

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHost.navController
        //  Nuevamente obtenemos el controlador de navegación activo.

        return navController.navigateUp() || super.onSupportNavigateUp()
        //  Intenta volver al fragmento anterior en la pila de navegación.
        // Si no hay fragmentos anteriores, llama al comportamiento normal de “Atrás”.
    }
}
