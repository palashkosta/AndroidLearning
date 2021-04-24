package com.pal.androidarchitectureapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pal.androidarchitectureapp.utils.LogicUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var logicUtils: LogicUtils
    @Inject lateinit var wordRepository: WordRepository
    @Inject lateinit var viewModel: WordViewModel

//    private val wordViewModel: WordViewModel by viewModels {
//        WordViewModelFactory(wordRepository)
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allWords.observe(this, { words ->
            logicUtils.showMessage()
            words.let { adapter.submitList(it) }
        })
    }
}