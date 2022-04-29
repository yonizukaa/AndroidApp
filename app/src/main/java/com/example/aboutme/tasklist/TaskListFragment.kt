package com.example.aboutme.tasklist

import TaskListAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.aboutme.R
import com.example.aboutme.form.FormActivity
import com.example.aboutme.network.Api
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class TaskListFragment : Fragment() {

    private val adapter = TaskListAdapter()
    private var taskList = listOf(
        Task("id1", "title1", "description1"),
        Task("id2", "title2", "description2"),
        Task("id3", "title3", "description3"),

        )
    val createTask =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = result.data?.getSerializableExtra("task") as Task?
                ?: return@registerForActivityResult
            taskList = taskList + task
            adapter.currentList = taskList
            adapter.notifyDataSetChanged()
        }

    val editTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = result.data?.getSerializableExtra("task") as Task?
            ?: return@registerForActivityResult
        taskList = taskList.map { if (it.id == task.id) task else it }
        adapter.currentList = taskList
        adapter.notifyDataSetChanged()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)

        adapter.currentList = taskList
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler1)
        recyclerView.adapter = adapter

        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            val intent = Intent(context, FormActivity::class.java)
            createTask.launch(intent)
        }
        adapter.onClickDelete = { task ->
            taskList = taskList - task
            refreshAdapter()

        }
        adapter.onClickEdit = { task ->
            val intent = Intent(context, FormActivity::class.java)
            intent.putExtra("task", task)
            editTask.launch(intent)
        }

        }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch{
            val userInfo = Api.userWebService.getInfo().body()!!
            val userInfoTextView = view?.findViewById<TextView>(R.id.userInfo)
            userInfoTextView?.text = "${userInfo.firstName} ${userInfo.lastName}"


        }
    }


    private fun refreshAdapter() {
        adapter.currentList = taskList
        adapter.notifyDataSetChanged()
    }


}
