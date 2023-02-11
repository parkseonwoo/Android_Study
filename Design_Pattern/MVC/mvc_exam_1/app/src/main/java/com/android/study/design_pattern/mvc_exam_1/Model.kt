package com.android.study.design_pattern.mvc_exam_1

class Model {
    var password : MutableList<Int> = mutableListOf()

    fun inputPassword(i : Int) {
        if(password.size < 4) {
            password.add(i)
        }
    }

    fun checkPassword() : Boolean {

        var trueCount = 0
        var savePassord = mutableListOf(1, 2, 3, 4)

        for(i in 0 until savePassord.size){
            if(savePassord.get(i) == password.get(i)) {
                trueCount++
            }
        }

        return trueCount == 4
    }
}

// 뷰를 표시하는 데이터를 가지고 있는 클래스