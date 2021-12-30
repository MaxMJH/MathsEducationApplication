package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Area
import com.anychart.enums.MarkerType
import com.mjh.mathseducationapplication.model.Student
import com.mjh.mathseducationapplication.model.User
import com.mjh.mathseducationapplication.core.table.AnswerTable
import com.mjh.mathseducationapplication.core.table.QuestionTable
import com.mjh.mathseducationapplication.core.table.StudentTable
import com.mjh.mathseducationapplication.core.table.TestTable

/**
 * A class representing the [DashboardActivity] controller.
 *
 * This class provides the functionality of the [DashboardActivity] such as setting the view and
 * button functionality.
 */
class DashboardActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var questionTable: QuestionTable
    private lateinit var answerTable: AnswerTable
    private lateinit var studentTable: StudentTable
    private lateinit var testTable: TestTable

    /*---- Methods ----*/
    /**
     * Sends the User to the [TestActivity] which starts a test.
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun startTest(view: View) {
        // Check if there are any questions or answers available.
        if(this.questionTable.getQuestions().size == 0 || this.answerTable.getAnswers().size == 0) {
            Toast.makeText(this, "No questions or answers available", Toast.LENGTH_SHORT).show()
        } else {
            val user: User = intent.getSerializableExtra("user") as User
            val student: Student?

            var studentID: Int = this.studentTable.studentExists(user.username)

            // If the user does not exist.
            if(studentID == -1) {
                student = Student(this.studentTable.getNextStudentID(), user.username, user.userID)
                this.studentTable.addStudent(student)
            } else {
                student = Student(studentID, user.username, user.userID)
            }

            // Start new intent that starts the test.
            val testActivityIntent: Intent = Intent(this, TestActivity::class.java).apply {
                putExtra("student", student)
            }

            startActivity(testActivityIntent)
        }
    }

    /**
     * Sends the User to the [AdminLoginActivity] which allows an User to login as an Admin.
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun startAdminSection(view: View) {
        // Start new intent that starts the admin section.
        val intent: Intent = Intent(this, AdminLoginActivity::class.java)
        startActivity(intent)
    }

    /**
     * Creates an area chart that contains the student's test results, if there are any available.
     * If there are no results, then a text view will be displayed stating such.
     */
    private fun createAreaChart() {
        // Code influenced and based off the example code provided by AnyChart.
        // Original code available at https://github.com/AnyChart/AnyChart-Android/blob/master/sample/src/main/java/com/anychart/sample/charts/AreaChartActivity.java
        val areaChart: Cartesian? = AnyChart.area()

        areaChart?.title("Your Results")

        val user: User = intent.getSerializableExtra("user") as User

        // AreaChart requires the data to be of type DataEntry.
        val userResults: ArrayList<DataEntry> = ArrayList()
        this.testTable.getUserTestResults(user.userID).map { ValueDataEntry(it.testID, it.result) }.forEach { userResults.add(it) }

        if(userResults.isEmpty()) {
            findViewById<TextView>(R.id.textViewGraphError).isInvisible = false
        } else {
            // Create the actual 'area' of the graph - student test results.
            val testResultArea: Area? = areaChart?.area(userResults)
            testResultArea?.name("Your Results")
            testResultArea?.fill("#F19B1A", 0.5)
            testResultArea?.stroke("2 #F19B1A")
            testResultArea?.markers()?.enabled(true)
            testResultArea?.markers()?.type(MarkerType.CIRCLE)?.size(4)?.stroke("1.5 #fff")
            testResultArea?.markers()?.zIndex(100)

            areaChart?.xAxis(0)?.title("Test ID")
            areaChart?.yAxis(0)?.title("Your Results")

            findViewById<AnyChartView>(R.id.areaChartResults).setChart(areaChart)
        }
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val user: User = intent.getSerializableExtra("user") as User
        findViewById<TextView>(R.id.textViewStudentName).text = user.username
        findViewById<TextView>(R.id.textViewGraphError).isInvisible = true

        this.questionTable = QuestionTable(this, "MathsEducation.db", 1)
        this.answerTable = AnswerTable(this, "MathsEducation.db", 1)
        this.studentTable = StudentTable(this, "MathsEducation.db", 1)
        this.testTable = TestTable(this, "MathsEducation.db", 1)

        // Remove any tests from the database if they have a result of -1.
        this.testTable.removeIncompleteTests()

        // Then create the chart.
        this.createAreaChart()
    }
}