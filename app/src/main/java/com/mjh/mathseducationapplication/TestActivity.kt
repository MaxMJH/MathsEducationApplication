package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.children
import com.mjh.mathseducationapplication.model.*
import com.mjh.mathseducationapplication.model.table.*

class TestActivity : AppCompatActivity() {
    private lateinit var testTable: TestTable
    private lateinit var questionTable: QuestionTable
    private lateinit var answerTable: AnswerTable
    private lateinit var studentAttemptTable: StudentAttemptTable
    private lateinit var studentTable: StudentTable
    private lateinit var questionsPool: ArrayList<Question>
    private lateinit var answersPool: ArrayList<Answer>
    private lateinit var radioButtonAnswers: List<Answer>
    private var studentID: Int = -1
    private var testID: Int = -1
    private var questionID: Int = -1
    private var studentAnswerID: Int = -1
    private var selectedAnswer: String = ""
    private var currentQuestion: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        // Create a temporary Test.
        this.studentID = intent.getIntExtra("studentID", -1)
        this.testTable = TestTable(this, "MathsEducation.db", 1)

        // Set result to -1 as Test has yet to be complete.
        val test: Test = Test(-1, studentID, -1.0)
        this.testTable.addTest(test)

        // Set class' testID to newly created test.
        this.testID = this.testTable.getLastTestID()

        // Initialise other tables.
        this.questionTable = QuestionTable(this, "MathsEducation.db", 1)
        this.answerTable = AnswerTable(this, "MathsEducation.db", 1)
        this.studentAttemptTable = StudentAttemptTable(this, "MathsEducation.db", 1)
        this.studentTable = StudentTable(this, "MathsEducation.db", 1)
        this.questionsPool = ArrayList()
        this.answersPool = ArrayList()

        this.setupQuestionsPool()
        this.setupAnswersPool()
        this.nextQuestion()
    }

    private fun setupQuestionsPool() {
        val allQuestions: ArrayList<Question> = this.questionTable.getQuestions()

        for(i in 1..14) {
            var randomNumber: Int = (0 until allQuestions.size).random()

            val question: Question = allQuestions[randomNumber]
            this.questionsPool.add(question)

            // Remove question from question pool to avoid duplicates.
            allQuestions.remove(question)
        }
    }

    private fun setupAnswersPool() {
        this.answersPool = this.answerTable.getAnswers()
    }

    private fun nextQuestion() {
        if(this.questionsPool.size >= 1) {
            this.resetView()

            // Display question.
            val question: Question = this.questionsPool[this.questionsPool.size - 1]

            // Set class' testID to newly created test.
            this.questionID = question.questionID

            findViewById<TextView>(R.id.textViewCurrentQuestion).text = "${this.currentQuestion} / 14"
            findViewById<TextView>(R.id.textViewQuestion).text = question.question

            // Display answer and two wrong answers.
            val answers: MutableList<Answer> = mutableListOf()
            answers.add(this.answerTable.getAnswer(question.answerID))

            for(i in 1 until 3) {
                answers.add(this.answersPool[(0 until this.answersPool.size).random()])
            }

            // Check if any answers are the same, if so, change them.
            while(answers[0].answer == answers[1].answer || answers[0].answer == answers[2].answer || answers[1].answer == answers[2].answer) {
                if(answers[0].answer == answers[1].answer) {
                    answers[1] = this.answersPool[(0 until this.answersPool.size).random()]
                }
                if(answers[0].answer == answers[2].answer) {
                    answers[2] = this.answersPool[(0 until this.answersPool.size).random()]
                }
                if(answers[1].answer == answers[2].answer) {
                    answers[1] = this.answersPool[(0 until this.answersPool.size).random()]
                }
            }

            // Put all answers in a list to get student submission.
            this.radioButtonAnswers = answers.toList()

            // Get list of RadioButtons and apply a randomised answer to each.
            val radioGroup: RadioGroup = findViewById(R.id.radioGroupAnswers)
            radioGroup.children.forEach { findViewById<RadioButton>(it.id).apply {
                val index: Int = (answers.indices).random()
                text = answers[index].answer
                answers.removeAt(index)
            } }

            // Remove question.
            this.questionsPool.remove(question)

            // Increment current question text view.
            this.currentQuestion++
        } else {
            // Should go to results page instead.
            this.calculateScore()
        }
    }

    fun submitAnswer(view: View) {
        this.studentAnswerID = this.radioButtonAnswers.filter { it.answer == this.selectedAnswer }[0].answerID

        this.studentAttemptTable.addAttempt(StudentAttempt(-1, this.testID, this.questionID, this.studentAnswerID))

        this.nextQuestion()
    }

    fun answerSelect(view: View) {
        findViewById<Button>(R.id.buttonSubmit).isEnabled = true
        this.selectedAnswer = findViewById<RadioButton>(view.id).text.toString()
    }

    private fun resetView() {
        findViewById<RadioGroup>(R.id.radioGroupAnswers).clearCheck()
        findViewById<Button>(R.id.buttonSubmit).isEnabled = false
    }

    private fun calculateScore() {
        val correctAttempts: Int = this.studentAttemptTable.getCorrectAttempts(this.testID)

        val overallScore: Double = ((correctAttempts * 1.0) / (this.currentQuestion - 1)) * 100

        this.testTable.updateTest(Test(this.testID, -1, "%.2f".format(overallScore).toDouble()))

        val student: Student = Student(this.studentID, this.studentTable.getStudent(studentID).studentName)
        val newIntent: Intent = Intent(this, DashboardActivity::class.java).apply {
            putExtra("student", student)
        }
        startActivity(newIntent)
    }
}