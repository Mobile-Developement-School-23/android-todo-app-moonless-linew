package ru.linew.todoapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.linew.todoapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}