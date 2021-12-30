package com.mjh.mathseducationapplication.model

/**
 * A class representing the Test model.
 *
 * This class provides the ability to store Test data which can be sent to the Test table
 * or stored from the Test table.
 *
 * @property testID The test ID of the Test.
 * @property studentID The student ID of the Test.
 * @property result The result of the Test.
 * @property questionsPool The questions pool of the Test.
 * @property answersPool The answers pool of the Test.
 * @property currentQuestionCount The current question count of the Test.
 * @constructor Creates all properties needed which reflects the columns from the Test table.
 */
data class Test(
    var testID: Int,
    val userID: Int,
    val result: Double,
    var questionsPool: ArrayList<Question> = ArrayList(),
    var answersPool: ArrayList<Answer> = ArrayList(),
    var currentQuestionCount: Int = 1) {

    /*---- Methods ----*/
    /**
     * Adds questions to the [Test]'s question pool to be used.
     *
     * @param allQuestions An Array List of type [Question] which contains a pool of questions to be used in the [Test].
     */
    fun populateQuestionsPool(allQuestions: ArrayList<Question>) {
        if(allQuestions.size == 0) {
            return
        }

        // As per requirements, 14 questions will be pulled from the questions pool.
        for(i in 1..14) {
            // Randomly pull questions.
            val randomNumber: Int = (0 until allQuestions.size).random()

            val question: Question = allQuestions[randomNumber]
            this.questionsPool.add(question)

            // Remove pulled question to avoid duplicate questions.
            allQuestions.remove(question)
        }
    }

    /**
     * Adds answers to the [Test]'s answer pool to be used.
     *
     * @param allAnswers An Array List of type [Answer] which contains a pool of answers to be used in the [Test].
     */
    fun populateAnswersPool(allAnswers: ArrayList<Answer>) {
        this.answersPool = allAnswers
    }

    /**
     * Removes a question from the [Test]'s question pool.
     *
     * @param question A [Question] to be removed from the questions pool, if it exists.
     */
    fun removeQuestion(question: Question) {
        this.questionsPool.remove(question)
    }
}