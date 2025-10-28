package com.example.moviuoc
//  Paquete del proyecto donde se encuentra este archivo (parte del módulo MoviUOC).

import android.content.Context
//  Importa el contexto de Android para poder acceder a almacenamiento local (SharedPreferences).

//  Clase de datos que representa a un usuario en la aplicación.
data class User(val email: String, val password: String, val name: String, val career: String)
// Cada usuario tiene: correo, contraseña, nombre y carrera.

object AuthStore {
    //  Objeto singleton (solo hay una instancia en toda la app) que gestiona el registro e inicio de sesión.

    private const val PREFS = "auth_prefs"
    //  Nombre del archivo SharedPreferences donde se guardan los datos.

    private const val LOGGED = "logged_in_email"
    //  Clave que se usa para guardar el correo del usuario que ha iniciado sesión.

    private fun prefs(ctx: Context) =
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    //  Función privada que obtiene el acceso a las preferencias compartidas.
    // Context.MODE_PRIVATE indica que los datos solo son accesibles dentro de la app.

    private fun key(email: String) = "user_" + email.trim().lowercase()
    //  Genera una clave única para cada usuario a partir de su correo electrónico.

    fun saveUser(ctx: Context, user: User) {
        //  Guarda la información del usuario en SharedPreferences.

        val packed = listOf(user.password, user.name, user.career).joinToString("|")
        //  Combina los datos del usuario en una sola cadena separada por "|", para almacenarlos fácilmente.

        prefs(ctx).edit().putString(key(user.email), packed).apply()
        //  Guarda la cadena asociada a la clave generada para ese usuario.
    }

    fun getUser(ctx: Context, email: String): User? {
        // Recupera la información de un usuario a partir de su correo.

        val raw = prefs(ctx).getString(key(email), null) ?: return null
        // Si no hay datos guardados para ese correo, devuelve null.

        val parts = raw.split("|")
        //  Divide la cadena guardada para volver a obtener los datos originales (contraseña, nombre, carrera).

        if (parts.size < 3) return null
        //  Si faltan datos, se considera inválido.

        return User(email.trim().lowercase(), parts[0], parts[1], parts[2])
        //  Devuelve un objeto `User` reconstruido con sus datos.
    }

    fun login(ctx: Context, email: String, password: String): Boolean {
        //  Intenta iniciar sesión con un correo y una contraseña.

        val u = getUser(ctx, email) ?: return false
        // Busca el usuario en el almacenamiento. Si no existe, devuelve false.

        val ok = u.password == password
        //  Verifica si la contraseña ingresada coincide con la guardada.

        if (ok) prefs(ctx).edit().putString(LOGGED, u.email).apply()
        //  Si las credenciales son correctas, guarda el correo del usuario logueado.

        return ok
        // Devuelve true si el login fue exitoso.
    }

    fun isLogged(ctx: Context): Boolean =
        prefs(ctx).getString(LOGGED, null) != null
    //  Verifica si hay un usuario actualmente logueado (si existe el valor en SharedPreferences).

    fun logout(ctx: Context) {
        //  Cierra la sesión del usuario actual.
        prefs(ctx).edit().remove(LOGGED).apply()
        //  Elimina la clave que guarda el correo del usuario logueado.
    }
}
