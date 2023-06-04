package com.android.study.design_pattern.mvc_exam_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.study.design_pattern.mvc_exam_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Adapter.ItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var modelAdapter: Adapter
    private var modelList = mutableListOf<Model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        for(i in 1 until 11) {
            val model = Model("$i")
            modelList.add(model)
        }

        setContentView(binding.root)
        modelAdapter = Adapter(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = modelAdapter
        }

        binding.insertButton.setOnClickListener {
            insert()
        }


    }

    override fun onClick(models: Model) {
        delete()
    }

    fun delete() {
        Thread {
            AppDatabase.getInstance(this)?.modelDao()?.delete(models)

            runOnUiThread {
                modelAdapter.submitList(modelAdapter.currentList)
            }
        }.start()
    }

    fun insert() {
        val model = Model("안녕")
        val newModelList = modelAdapter.currentList
        newModelList.add(model)

        Thread {
            AppDatabase.getInstance(this)?.modelDao()?.insert(models)

            runOnUiThread {
                Toast.makeText(this, "버튼 누름", Toast.LENGTH_SHORT).show()
                modelAdapter.submitList(newModelList)
            }
        }.start()
    }

}