package com.example.kotlincouratinusviewmodelresource

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.kotlincouratinusviewmodelresource.adapters.TodoAdapter
import com.example.kotlincouratinusviewmodelresource.api.ApiClient
import com.example.kotlincouratinusviewmodelresource.databinding.ActivityMainBinding
import com.example.kotlincouratinusviewmodelresource.databinding.DialogPostBinding
import com.example.kotlincouratinusviewmodelresource.models.MyTodo
import com.example.kotlincouratinusviewmodelresource.models.Request
import com.example.kotlincouratinusviewmodelresource.repository.TodoRepository
import com.example.kotlincouratinusviewmodelresource.utils.Status
import com.example.kotlincouratinusviewmodelresource.viewmodel.MyTodoViewModel
import com.example.kotlincouratinusviewmodelresource.viewmodel.MyViewModelFactory

class MainActivity : AppCompatActivity(), TodoAdapter.RvAction {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var myTodoViewModel: MyTodoViewModel
    lateinit var todoAdapter: TodoAdapter
    lateinit var todoRepository: TodoRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        todoRepository = TodoRepository(ApiClient.getApiService())
        myTodoViewModel = ViewModelProvider(
            this,
            MyViewModelFactory(todoRepository)
        ).get(MyTodoViewModel::class.java)
        todoAdapter = TodoAdapter(rvAction = this)
        binding.rv.adapter = todoAdapter

        myTodoViewModel.getAllTodo()
            .observe(this) {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                    }

                    Status.SUCCESS -> {
                        todoAdapter.list = it.data!!
                        todoAdapter.notifyDataSetChanged()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        binding.btnAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val dialogItemBinding = DialogPostBinding.inflate(layoutInflater)
            dialog.setView(dialogItemBinding.root)
            dialogItemBinding.btnSave.setOnClickListener {
                val sarlavha = dialogItemBinding.etSarlavha.text.toString()
                val batafsil = dialogItemBinding.etBatafsil.text.toString()
                val zarurlik = dialogItemBinding.spinner.selectedItem.toString()
                myTodoViewModel.addTodo(Request(true, batafsil, "2024-01-01", sarlavha, zarurlik))
                    .observe(this) {
                        when (it.status) {
                            Status.LOADING -> {
                                dialogItemBinding.progressBar.visibility = View.VISIBLE
                                dialogItemBinding.btnSave.isEnabled = false

                            }

                            Status.ERROR -> {
                                Toast.makeText(this, "Xatolik ${it.message}", Toast.LENGTH_SHORT)
                                    .show()
                                dialogItemBinding.progressBar.visibility = View.GONE
                                dialogItemBinding.btnSave.isEnabled = true
                            }

                            Status.SUCCESS -> {
                                Toast.makeText(this, "Muvaffaqiyatli saqlandi", Toast.LENGTH_SHORT)
                                    .show()
                                dialog.dismiss()
                            }


                        }
                    }

            }
            dialog.show()
        }
    }

    override fun moreClick(myTodo: MyTodo, imageView: ImageView) {
        val popmenu = PopupMenu(this, imageView)
        popmenu.inflate(R.menu.my_menu)

        popmenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    myTodoViewModel.deleteTodo(myTodo.id)
                    Toast.makeText(this, "O'chirildi", Toast.LENGTH_SHORT).show()
                }

                R.id.edit -> {
                    editClick(myTodo)
                }
            }
            true
        }
        popmenu.show()

    }

    private fun editClick(myTodo: MyTodo){
        val dialog = AlertDialog.Builder(this).create()
        val dialogItemBinding = DialogPostBinding.inflate(layoutInflater)
        dialog.setView(dialogItemBinding.root)
        dialogItemBinding.apply {
            etSarlavha.setText(myTodo.sarlavha)
            etBatafsil.setText(myTodo.batafsil)
            when(myTodo.zarurlik){
                "shart" -> spinner.setSelection(0)
                "foydali" -> spinner.setSelection(1)
                "tavsiya" -> spinner.setSelection(2)
                "hayot_mamot" -> spinner.setSelection(3)
            }
            btnSave.setOnClickListener {
                val request = Request(true,
                    etBatafsil.text.toString(),
                    "2024-01-01",
                    etSarlavha.text.toString(),
                    spinner.selectedItem.toString())
                myTodoViewModel.updateTodo(myTodo.id,request)
                    .observe(this@MainActivity){
                        when(it.status){
                            Status.LOADING -> {
                                progressBar.visibility = View.VISIBLE
                                btnSave.isEnabled = false
                            }
                            Status.ERROR -> {
                                Toast.makeText(this@MainActivity, "Xatolik ${it.message}", Toast.LENGTH_SHORT).show()
                                progressBar.visibility = View.GONE
                                btnSave.isEnabled = true
                            }
                            Status.SUCCESS -> {
                                Toast.makeText(this@MainActivity, "Muvaffaqiyatli saqlandi", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }

                        }
                    }
            }
        }

        dialog.show()
    }
}