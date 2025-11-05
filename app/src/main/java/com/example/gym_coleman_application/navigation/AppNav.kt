package com.example.gym_coleman_application.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gym_coleman_application.ui.theme.login.LoginScreen
// Importa las pantallas desde la carpeta 'view'

import com.example.gym_coleman_application.view.ProductoFormScreen
import com.example.gym_coleman_application.viewimport.DrawerMenu

@Composable
fun AppNav() {
    val navController = rememberNavController()

    // El NavHost define todas las rutas de la aplicación
    NavHost(navController = navController, startDestination = "login") {

        // 1. Ruta para la pantalla de Login
        //    La identifica con la ruta de texto "login".
        //    Aunque LoginScreen no navega directamente a OTRAS pantallas,
        //    es una buena práctica pasarlo por si se necesita en el futuro

        composable("login") {
            LoginScreen(
                //    Llama y muestra el Composable 'LoginScreen', que contiene la intefaz
                navController = navController,
                // Cuando el login es exitoso, navega a DrawerMenu
                onLoginSuccess = { username ->
                    //    Este es el parámetro más importante. Es una "función de callback".
                    navController.navigate("DrawerMenu/$username") {
                        // Borra el login del historial para no poder volver
                        popUpTo("login") { inclusive = true }//el popout le dice al navegador que borre el login del historial
                    }
                }
            )
        }

        // 2. Ruta para la pantalla principal (Menú Lateral)
        composable(
            route = "DrawerMenu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            DrawerMenu(username = username, navController = navController)
        }

        // 3. Ruta para el formulario de un producto
        composable(
            route = "ProductoFormScreen/{nombre}/{precio}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("precio") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Se obtienen los datos directamente, sin Uri.encode
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val precio = backStackEntry.arguments?.getString("precio") ?: ""
            ProductoFormScreen(navController = navController, nombre = nombre, precio = precio)
        }
    }
}
