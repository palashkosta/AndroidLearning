package com.pal.androidarchitectureapp

import androidx.lifecycle.*
import com.pal.androidarchitectureapp.db.Word
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * But how is our viewModel going to be injected? Do we need a ViewModelFactory like before? Well it turns out that Hilt makes this just as easy for us.
 * We need to annotate the constructor of the ViewModel with @ViewModelInject and that's all.
 *
 */
class WordViewModel @Inject constructor(private val repository: WordRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
