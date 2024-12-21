// presentation/screens/bookform/BookFormScreen.kt
package com.example.libraryapp.presentation.screens.bookform

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.model.ReadStatus
import java.util.UUID

import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
//import androidx.compose.material3.ExposedDropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookFormScreen(
    bookId: String? = null,
    onNavigateBack: () -> Unit,
    viewModel: BookFormViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var isbn by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var readStatus by remember { mutableStateOf(ReadStatus.TO_READ) }
    var coverUrl by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) } // Definición de expanded


    LaunchedEffect(bookId) {
        // Si hay un bookId, cargar el libro para editar
        bookId?.let { id ->
            viewModel.getBook(id)?.let { book ->
                title = book.title
                author = book.author
                isbn = book.isbn
                description = book.description
                readStatus = book.readStatus
                coverUrl = book.coverUrl
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (bookId == null) "Agregar Libro" else "Editar Libro") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Autor") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = isbn,
                onValueChange = { isbn = it },
                label = { Text("ISBN") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = coverUrl,
                onValueChange = { coverUrl = it },
                label = { Text("URL de portada") },
                modifier = Modifier.fillMaxWidth()
            )

            // Estado de lectura
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = readStatus.toString(),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Estado de lectura") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    ReadStatus.values().forEach { status ->
                        DropdownMenuItem(
                            text = { Text(status.toString()) },
                            onClick = {
                                readStatus = status
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val book = Book(
                        id = bookId ?: UUID.randomUUID().toString(),
                        title = title,
                        author = author,
                        isbn = isbn,
                        description = description,
                        readStatus = readStatus,
                        coverUrl = coverUrl
                    )
                    viewModel.saveBook(book)
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (bookId == null) "Agregar" else "Guardar cambios")
            }
        }
    }
}