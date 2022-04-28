package com.example.aboutme.form

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.aboutme.R
import com.example.aboutme.tasklist.Task
import java.util.*

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        val inputTitle = findViewById<EditText>(R.id.titleForm)
        val inputDescription = findViewById<EditText>(R.id.descriptionForm)
        val button = findViewById<Button>(R.id.button)
        val taskedit = intent.getSerializableExtra("task") as? Task
        inputDescription.setText(taskedit?.description)
        inputTitle.setText(taskedit?.title)

        button.setOnClickListener {
            val newTask = Task(
                id = taskedit?.id?: UUID.randomUUID().toString(), title = inputTitle.text.toString(),
                description = inputDescription.text.toString()
            )
            intent.putExtra("task", newTask)


            setResult(RESULT_OK, intent)

            finish()
        }


    }
}