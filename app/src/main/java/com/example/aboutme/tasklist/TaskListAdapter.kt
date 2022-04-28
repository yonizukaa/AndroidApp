import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aboutme.R
import com.example.aboutme.tasklist.Task


class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    var currentList: List<Task> = emptyList()
    var onClickDelete: (Task) -> Unit = {}
    var onClickEdit:(Task)-> Unit = {}



    // on utilise `inner` ici afin d'avoir accès aux propriétés de l'adapter directement
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task) {

            val textView = itemView.findViewById<TextView>(R.id.task_title)
            textView.text = task.title
            val textView1 = itemView.findViewById<TextView>(R.id.task_description)
            textView1.text = task.description
            val buttonDelete = itemView.findViewById<Button>(R.id.buttonDelete)
            buttonDelete.setOnClickListener { onClickDelete(task)}
            val buttonEdit = itemView.findViewById<Button>(R.id.buttonEdit)
            buttonEdit.setOnClickListener { onClickEdit(task)}



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(currentList[position])

    }

    override fun getItemCount(): Int {
         return currentList.count()
    }


}