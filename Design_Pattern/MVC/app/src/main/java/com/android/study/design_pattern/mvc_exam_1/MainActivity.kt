package com.android.study.design_pattern.mvc_exam_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.study.design_pattern.mvc_exam_1.databinding.ActivityMainBinding

//Controller
class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var model = Model()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainactivity = this

    }

    fun clickNumber(i : Int) {
        Toast.makeText(this, "$i 번을 클릭했습니다.", Toast.LENGTH_SHORT).show()
        model.inputPassword(i)

        if(model.password.size == 4 && model.checkPassword()) {
            // 4자리 이상 비밀번호가 1234일 경우
            binding.sucessMessage.visibility = View.VISIBLE
        }

    }
}