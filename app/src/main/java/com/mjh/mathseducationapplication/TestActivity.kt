package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.children
import com.mjh.mathseducationapplication.model.*
import com.mjh.mathseducationapplication.core.table.*

/**
 * A class representing the [TestActivity] controller.
 *
 * This class provides the functionality of the [TestActivity] such as setting the view and
 * button functionality.
 */
class TestActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var radioButtonAnswers: List<Answer>
    private lateinit var selectedAnswer: String
    private lateinit var testTable: TestTable
    private lateinit var questionTable: QuestionTable
    private lateinit var answerTable: AnswerTable
    private lateinit var studentAttemptTable: StudentAttemptTable
    private lateinit var studentTable: StudentTable
    private lateinit var test: Test
    private lateinit var currentQuestion: Question
    private lateinit var student: Student

    /*---- Methods ----*/
    /**
     * Sets the next question for the [TestActivity] view if there are any questions in the questions pool.
     * Instead of creating separate views for each question, the text views' and radio buttons' values are
     * changed. Each answer should be unique and randomly allocated apart from the correct answer.
     */
    private fun nextQuestion() {
        // Check to see if any questions are available.
        if(this.test.questionsPool.size >= 1) {
            this.resetView()

            // Take the last question from the questions pool.
            this.currentQuestion = this.test.questionsPool[this.test.questionsPool.size - 1]

            // Display question information.
            findViewById<TextView>(R.id.textViewCurrentQuestion).text = "${this.test.currentQuestionCount} / 14"
            findViewById<TextView>(R.id.textViewQuestion).text = this.currentQuestion.question
            findViewById<ImageView>(R.id.imageViewQuestionPicture).setImageResource(
                this.resources.getIdentifier("${this.currentQuestion.questionPicture}", "drawable", this.packageName)
            )

            // Display answer and two wrong answers.
            val answers: MutableList<Answer> = mutableListOf()
            answers.add(this.answerTable.getAnswer(this.currentQuestion.answerID))

            for(i in 1 until 3) {
                answers.add(this.test.answersPool[(0 until this.test.answersPool.size).random()])
            }

            // Check if any answers are the same, if so, change them.
            while(answers[0].answer == answers[1].answer || answers[0].answer == answers[2].answer || answers[1].answer == answers[2].answer) {
                if(answers[0].answer == answers[1].answer) {
                    answers[1] = this.test.answersPool[(0 until this.test.answersPool.size).random()]
                }
                if(answers[0].answer == answers[2].answer) {
                    answers[2] = this.test.answersPool[(0 until this.test.answersPool.size).random()]
                }
                if(answers[1].answer == answers[2].answer) {
                    answers[1] = this.test.answersPool[(0 until this.test.answersPool.size).random()]
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
            this.test.removeQuestion(currentQuestion)

            this.test.currentQuestionCount++
        } else {
            this.calculateScore()
        }
    }

    /**
     * After the student has selected an answer, add the [StudentAttempt] to the StudentAttempt Table.
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun submitAnswer(view: View) {
        // Get the answer ID of the answer that the student has selected.
        val studentAnswerID: Int = this.radioButtonAnswers.filter { it.answer == this.selectedAnswer }[0].answerID

        this.studentAttemptTable.addAttempt(StudentAttempt(-1, this.test.testID, this.currentQuestion.questionID, studentAnswerID))

        // Go to the next question.
        this.nextQuestion()
    }

    /**
     * Applied to each radio button which ensures an answer is selected before a student is able
     * to submit to prevent any exceptions.
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun answerSelect(view: View) {
        findViewById<Button>(R.id.buttonSubmit).isEnabled = true
        this.selectedAnswer = findViewById<RadioButton>(view.id).text.toString()
    }

    /**
     * Reset the answer selection and ensure that the submission button is disabled.
     */
    private fun resetView() {
        findViewById<RadioGroup>(R.id.radioGroupAnswers).clearCheck()
        findViewById<Button>(R.id.buttonSubmit).isEnabled = false
    }

    /**
     * Calculates the score of the student's test by obtaining correct attempts. Once the score is
     * calculated, the test that the student started is updated with the student's final score.
     */
    private fun calculateScore() {
        val correctAttempts: Int = this.studentAttemptTable.getCorrectAttempts(this.test.testID)

        val overallScore: Double = ((correctAttempts * 1.0) / (this.test.currentQuestionCount - 1)) * 100

        this.testTable.updateTest(Test(this.test.testID, -1, "%.2f".format(overallScore).toDouble()))

        val student: Student = Student(this.student.studentID, this.studentTable.getStudent(this.student.studentID).studentName, this.student.userID)
        val newIntent: Intent = Intent(this, TestResultActivity::class.java).apply {
            putExtra("student", student)
            putExtra("score", overallScore)
        }
        startActivity(newIntent)
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        // Initialise tables.
        this.answerTable = AnswerTable(this, "MathsEducation.db", 1)
        this.questionTable = QuestionTable(this, "MathsEducation.db", 1)
        this.testTable = TestTable(this, "MathsEducation.db", 1)
        this.studentAttemptTable = StudentAttemptTable(this, "MathsEducation.db", 1)
        this.studentTable = StudentTable(this, "MathsEducation.db", 1)

        // Create a temporary Test, Set result to -1 as Test has yet to be complete.
        this.student = intent.getSerializableExtra("student") as Student
        this.test = Test(-1, this.student.studentID, -1.0)
        test.populateAnswersPool(this.answerTable.getAnswers())
        test.populateQuestionsPool(this.questionTable.getQuestions())

        this.testTable.addTest(test)

        // Set class' testID to newly created test.
        this.test.testID = this.testTable.getLastTestID()

        this.nextQuestion()
    }
}