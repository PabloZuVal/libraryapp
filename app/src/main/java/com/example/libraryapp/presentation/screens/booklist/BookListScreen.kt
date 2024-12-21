// presentation/screens/booklist/BookListScreen.kt
package com.example.libraryapp.presentation.screens.booklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.libraryapp.domain.model.ReadStatus
import com.example.libraryapp.presentation.model.BookListUiState
import com.example.libraryapp.presentation.model.BookUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToAdd: () -> Unit,
    viewModel: BookListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Biblioteca") },
                actions = {
                    IconButton(onClick = { onNavigateToAdd() }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar libro"
                        )
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
            // Search Bar
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::updateSearchQuery,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            when (uiState) {
                is BookListUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is BookListUiState.Success -> {
                    BookList(
                        books = (uiState as BookListUiState.Success).books,
                        onBookClick = onNavigateToDetail,
                        onStatusChange = viewModel::updateReadStatus
                    )
                }
                is BookListUiState.Error -> {
                    ErrorMessage(
                        message = (uiState as BookListUiState.Error).message,
                        onRetry = viewModel::loadBooks
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = modifier,
        placeholder = { Text("Buscar libros...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar"
            )
        }
    )
}

@Composable
fun BookList(
    books: List<BookUI>,
    onBookClick: (String) -> Unit,
    onStatusChange: (String, ReadStatus) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(books) { book ->
            BookItem(
                book = book,
                onClick = { onBookClick(book.id) },
                onStatusChange = { status -> onStatusChange(book.id, status) }
            )
        }
    }
}

@Composable
fun BookItem(
    book: BookUI,
    onClick: () -> Unit,
    onStatusChange: (ReadStatus) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = book.author,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                StatusChip(
                    currentStatus = book.readStatus,
                    onStatusChange = onStatusChange
                )
            }
        }
    }
}

@Composable
fun StatusChip(
    currentStatus: ReadStatus,
    onStatusChange: (ReadStatus) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        AssistChip(
            onClick = { expanded = true },
            label = { Text(currentStatus.toString()) },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = when (currentStatus) {
                    ReadStatus.TO_READ -> MaterialTheme.colorScheme.secondary
                    ReadStatus.READING -> MaterialTheme.colorScheme.primary
                    ReadStatus.FINISHED -> MaterialTheme.colorScheme.tertiary
                }
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ReadStatus.values().forEach { status ->
                DropdownMenuItem(
                    text = { Text(status.toString()) },
                    onClick = {
                        onStatusChange(status)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ErrorMessage(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}