package ru.linew.todoapp.ui.feature.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.linew.todoapp.R

//This is Single-Activity
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}