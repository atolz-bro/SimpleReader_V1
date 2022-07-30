package com.example.simplereader_v1.viewModels

import androidx.lifecycle.*
import com.example.simplereader_v1.database.models.LibraryBook
import com.example.simplereader_v1.database.models.LibraryBookDao
import com.example.simplereader_v1.database.models.Note
import com.example.simplereader_v1.database.models.NoteDao
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LibraryBooksAndNotesViewModel(val bookDao: LibraryBookDao, val noteDao: NoteDao) : ViewModel() {
    fun getNotes(bookName : String) : List<Note>{
        var it : List<Note>
        runBlocking {
            it = noteDao.getNotes(bookName = bookName)
        }
       return it;
    }

    fun addNote(note: Note){
        viewModelScope.launch {
            noteDao.insertNote(note);
        }

    }

    fun getLibraryBooks() : List<LibraryBook>{
        var it : List<LibraryBook>
        runBlocking {
            it =  bookDao.getLibraryBooks()
        }
        return it
    }

    fun addLibraryBook(libraryBook: LibraryBook){
        viewModelScope.launch {
            bookDao.insertBook(libraryBook);
        }
    }

    fun updateLibraryBook(libraryBook: LibraryBook){
        viewModelScope.launch {
            bookDao.update(libraryBook)
        }
    }

    fun getBookNotesCount(bookName: String) : Int{
        var count = 0;
        runBlocking {
            count = noteDao.getNoteCount(bookName)
        }
        return count

    }

    fun removeLibraryBook(libraryBook: LibraryBook){
        runBlocking {
            bookDao.delete(libraryBook)
        }
    }

}

class LibraryBooksAndNotesViewModelFactory(val bookDao: LibraryBookDao, val noteDao: NoteDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryBooksAndNotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LibraryBooksAndNotesViewModel(bookDao,noteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}