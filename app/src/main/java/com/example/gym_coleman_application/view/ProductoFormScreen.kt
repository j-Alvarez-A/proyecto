package com.example.gym_coleman_application.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gym_coleman_application.data.model.Producto
import com.example.gym_coleman_application.viewmodel.ProductoViewModel
import kotlinx.serialization.descriptors.PrimitiveKind
import com.example.gym_coleman_application.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ProductoFormScreen(
    navController: NavController,
    nombre:String,
    precio:String
){// Inicio

    var cantidad by remember{ mutableStateOf(TextFieldValue("")) }
    var direccion by remember{ mutableStateOf(TextFieldValue("")) }

    var Efectivo  by remember{ mutableStateOf(false) }
    var Debito  by remember{ mutableStateOf(false) }

// conectar al viewmodel
    val viewModel: ProductoViewModel = viewModel()

    // Observa directamente los productos
    val productos : List<Producto> by viewModel.productos.collectAsState()



    Scaffold (
        bottomBar = {
            BottomAppBar {
                // Contenido Barra superior
            } // fin Bootom App
        }// fin bottom

    ) // fin Scaffold

    {// inicio inner
            innerPadding ->
        // contenedor principal Column organiza sus elementos hijos en una secuencia vertic
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        )// fin Column
        { // Inicio Contenido

            Image(
                painter= painterResource(id= android.R.drawable.ic_menu_gallery),
                contentDescription = "Imagen Producto",
                modifier=Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )// fin Image

            Spacer(modifier =Modifier.height(16.dp))

            Text(text=nombre, style= MaterialTheme.typography.headlineSmall)
            Text(text="Precio: $precio", style= MaterialTheme.typography.bodyLarge)

            Spacer(modifier =Modifier.height(16.dp))


            OutlinedTextField(
                value=cantidad,
                onValueChange = {cantidad = it},
                //OutlinedTextField es un componente de entrada de texto
                // se utiliza para permitir que el usuario ingrese un valor.

                label ={Text("Cantidad")},
                modifier = Modifier.fillMaxWidth()
            ) // fin cantidad

            OutlinedTextField(
                value=direccion,
                onValueChange = {direccion = it},
                //OutlinedTextField es un componente de entrada de texto
                // se utiliza para permitir que el usuario ingrese un valor.

                label ={Text("Direccion")},
                modifier = Modifier.fillMaxWidth()
            ) // fin direccion

            // Row organiza elementos en una secuencia horizontal

            Row(verticalAlignment = Alignment.CenterVertically){
                Checkbox(
                    checked =Efectivo,
                    onCheckedChange = {Efectivo = it}
                )
                Text("Pago en Efectivo")
            }// fin row 1

            Row(verticalAlignment = Alignment.CenterVertically){
                Checkbox(
                    checked =Debito,
                    onCheckedChange = {Debito = it}
                )
                Text("Pago Debito")
            }// fin row 2

            Spacer(modifier =Modifier.height(16.dp))

            //--- 10. BOTÓN DE CONFIRMACIÓN ---

            Button(
                onClick = {
                val producto = Producto(
                    nombre = nombre,
                    precio = precio,
                    cantidad = cantidad.text,
                    direccion = direccion.text,
                    imagen = R.drawable.creatina
                )
                    viewModel.guardarProducto(producto)

                },
                enabled=cantidad.text.isNotBlank() && direccion.text.isNotBlank()
            ) // fin Button
            { // inicio texto
                Text("Confirmar Pedido")
            }// fin texto
            Spacer(modifier =Modifier.height(16.dp))
// Mostrar Productos Guardados
            Text("Pedidos realizados :", style = MaterialTheme.typography.headlineSmall)
            if (productos.isNotEmpty()){
                LazyColumn(modifier = Modifier.weight(1f)){
                    items(productos){ producto ->
                        // Card es un contenedor con elevación que agrupa contenido relacionado.
                        Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                        )//fin card
                        {// contenido Card
                            Column(modifier = Modifier.padding(8.dp))
                            {// contenido columna // Muestra los detalles de cada pedido guardado.
                                Text(
                                    text = "${producto.nombre} - ${producto.precio}",
                                    style=MaterialTheme.typography.bodyLarge
                                )// fin texto 1
                                Text(
                                    text = "Cantidad :${producto.cantidad} ",
                                    style=MaterialTheme.typography.bodyLarge
                                )// fin texto 2
                                Text(
                                    text = "Direccion :${producto.direccion}",
                                    style=MaterialTheme.typography.bodyLarge
                                )// fin texto 3


                            } // fin contenido columna

                        } // fin contenido Card
                    }// items
                } // fin lazy
            } //end if
            else{
                Text(
                    text = "No hay pedidos realizados",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
            } // fin else
        } //Fin Contenido

    } // fin inner

}//fin


@Preview(showBackground = true)
@Composable
fun PreviewProductoFormScreen() {
    // Preview básico para testing
    ProductoFormScreen(
        navController = rememberNavController(),
        nombre = "Producto Ejemplo",
        precio = "$10.00"
    )
}