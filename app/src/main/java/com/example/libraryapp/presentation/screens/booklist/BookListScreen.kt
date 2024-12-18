package com.example.libraryapp.presentation.screens.booklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
//import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.libraryapp.presentation.model.BookListUiState
import com.example.libraryapp.presentation.model.BookUI
import com.example.libraryapp.presentation.screens.booklist.BookListViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.libraryapp.domain.model.ReadStatus
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    viewModel: BookListViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Biblioteca") },
                actions = {
                    IconButton(onClick = { /* Implementar añadir libro */ }) {
                        Icon(Icons.Filled.Add, contentDescription = "Añadir libro")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = viewModel::updateSearchQuery,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            when (uiState) {
                is BookListUiState.Loading -> LoadingIndicator()
                is BookListUiState.Error -> ErrorMessage(
                    message = (uiState as BookListUiState.Error).message
                )
                is BookListUiState.Success -> BookList(
                    books = (uiState as BookListUiState.Success).books,
                    onBookClick = navigateToDetail,
                    onStatusUpdate = viewModel::updateReadStatus
                )
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text("Buscar libros...") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) }
    )
}

@Composable
private fun BookList(
    books: List<BookUI>,
    onBookClick: (String) -> Unit,
    onStatusUpdate: (String, ReadStatus) -> Unit
) {
    LazyColumn {
        items(books) { book ->
            BookItem(
                book = book,
                onClick = { onBookClick(book.id) },
                onStatusUpdate = { status -> onStatusUpdate(book.id, status) }
            )
        }
    }
}

@Composable
private fun BookItem(
    book: BookUI,
    onClick: () -> Unit,
    onStatusUpdate: (ReadStatus) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            ReadStatusButton(
                currentStatus = book.readStatus,
                onStatusUpdate = onStatusUpdate
            )
        }
    }
}

@Composable
private fun ReadStatusButton(
    currentStatus: ReadStatus,
    onStatusUpdate: (ReadStatus) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Cambiar estado"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
//            ReadStatus.values().forEach { status ->
//                DropdownMenuItem(
//                    content = { Text(status.name) },
//                    onClick = {
//                        onStatusUpdate(status)
//                        expanded = false
//                    }
//                )
//            }
            ReadStatus.values().forEach { status ->
                DropdownMenuItem(
                    text = { Text(status.name) },
                    onClick = {
                        onStatusUpdate(status)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error
        )
    }
}