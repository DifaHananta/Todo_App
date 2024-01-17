package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        val title = findViewById<TextView>(R.id.detail_ed_title)
        val desc = findViewById<TextView>(R.id.detail_ed_description)
        val dueDate = findViewById<TextView>(R.id.detail_ed_due_date)
        val delete = findViewById<Button>(R.id.btn_delete_task)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailTaskViewModel::class.java]

        val taskId = intent.getIntExtra(TASK_ID, 0)
        val task = getTask(taskId)
        task.observe(this, Observer { task ->
            if (task != null) {
                title.text = task.title
                desc.text = task.description
                dueDate.text = DateConverter.convertMillisToString(task.dueDateMillis)
                delete.setOnClickListener {
                    viewModel.deleteTask()
                    finish()
                }
            }
        })
    }

    private fun getTask(taskId: Int): LiveData<Task> {
        viewModel.setTaskId(taskId)
        val data = viewModel.task
        Log.d("Test Data", data.toString())
        return data
    }
}