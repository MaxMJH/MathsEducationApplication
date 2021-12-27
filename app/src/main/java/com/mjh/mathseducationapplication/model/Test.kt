package com.mjh.mathseducationapplication.model

data class Test(
    var testID: Int,
    val studentID: Int,
    val result: Double,
    var questionsPool: ArrayList<Question> = ArrayList(),
    var answersPool: ArrayList<Answer> = ArrayList(),
    var currentQuestionCount: Int = 1) {

    /*---- Methods ----*/
    fun populateQuestionsPool(allQuestions: ArrayList<Question>) {
        if(allQuestions.size == 0) {
            return
        }

        for(i in 1..14) {
            val randomNumber: Int = (0 until allQuestions.size).random()

            val question: Question = allQuestions[randomNumber]
            this.questionsPool.add(question)

            allQuestions.remove(question)
        }
    }

    fun populateAnswersPool(allAnswers: ArrayList<Answer>) {
        this.answersPool = allAnswers
    }

    fun removeQuestion(question: Question) {
        this.questionsPool.remove(question)
    }
}