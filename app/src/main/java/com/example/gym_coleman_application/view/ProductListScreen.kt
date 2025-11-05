package com.example.gym_coleman_application.view

// --- IMPORTS ---
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gym_coleman_application.R

// --- MODELO SIMPLE PARA LISTA ---
data class ProductoSimple(
    val nombre: String,
    val precio: String,
    val imagen: Int
)

// --- PANTALLA ---
@Composable
fun ProductListScreen(navController: NavController) {

    // Lista de productos con imágenes
    val productosDisponibles = listOf(
        ProductoSimple("Creatina", "25.000", R.drawable.creatina),
        ProductoSimple("Proteína Whey", "60.000", R.drawable.proteina),
        ProductoSimple("Pre-entreno", "48.990", R.drawable.planes),
        ProductoSimple("Omega 3", "30.000", R.drawable.creatina),
        ProductoSimple("Barra de Proteína", "1.500", R.drawable.proteina),
        ProductoSimple("Shaker", "7.990", R.drawable.planes),
        ProductoSimple("Membresía", "25.000", R.drawable.planes),
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        // Banner
        item {
            Image(
                painter = painterResource(id = R.drawable.planes),
                contentDescription = "Banner Gimnasio Coleman",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Título
        item {
            Text(
                text = "Nuestros Productos",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Lista
        items(productosDisponibles) { producto ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable {
                        navController.navigate(
                            "ProductoFormScreen/${producto.nombre}/${producto.precio}"
                        )
                    },
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // ✅ IMAGEN DEL PRODUCTO
                    Image(
                        painter = painterResource(id = producto.imagen),
                        contentDescription = producto.nombre,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(end = 12.dp),
                        contentScale = ContentScale.Crop
                    )

                    // ✅ NOMBRE + PRECIO
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = producto.nombre,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$${producto.precio}",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
