package com.example.personalexpensetracker.consoleApplication
import java.util.Scanner
/*
    This is class Declaration for user. UserId is automatically generated and it is unique
 */

class User (
        private var userName: String,
        private var password : String,
        private var income: Float
) {
    private var userId : Int = generateUserId()
    private var amountSpend : Float = 0F

    // This object is used to generate automatic User ID everyTime Creating new  User Object
    companion object {
        private var idGenerator : Int = 0
        fun generateUserId(): Int {
            return ++idGenerator
        }
    }

    // Getter Method
    fun getUserId() : Int {
        return this.userId
    }

    // Function to verify the user name and password
    fun isPasswordCorrect(password : String) :Boolean
    {
        return this.password == password
    }

    // Function to display User Object Details
    fun displayUserDetails()
    {
        println("User Object Details")
        println("UserId:$userId")
        println("User Name:$userName")
        println("Income:$income")
        println("AmountSpend:$amountSpend")
        println("Remaining Amount:${income - amountSpend}")
    }
}

class ExpenseType (
    private val expenseType: String,
    private var description : String = ""
) {
    private var expenseTypeId: Int = generateExpenseTypeId()

    // This object is used to generate automatic Expense ID everyTime Creating new  Expense Object
    companion object {
        private var idGenerator: Int = 0
        fun generateExpenseTypeId(): Int {
            return ++idGenerator
        }
    }
    // Function to display Expense Object Details
    fun displayExpenseDetails()
    {
        println("Expense Object Details")
        println("Expense Name:$expenseType")
        println("Expense Id:$expenseTypeId")
        println("Description:$description")
    }

    fun display_ExpensType_id () {
        print("Expense Name:$expenseType")
        println("\tExpense Id:$expenseTypeId")
    }

}
/*
    This class is used to record expenses related to which user
 */
class ExpenseRecord (
    private var expenseId : Int,
     private var userId : Int,
     private var amount : Float,
     private var date: String,
     private var description: String = ""
) {
    private var recordId: Int = generateRecordId()

    // This object is used to generate automatic Record ID everyTime Creating new  Record Object
    companion object {
        private var idGenerator: Int = 0
        fun generateRecordId(): Int {
            return ++idGenerator
        }
    }
    fun getRecordId () : Int {
        return recordId
    }
    fun getUserId() :Int {
        return userId
    }

//     Function to display Record Object Details
    fun displayExpenseDetails()
    {
        println("Record  Details")
        println("Expense ID:$expenseId")
        println("user Id:$userId")
        println("Description:$description")
        println ("Amount: $amount")
        println("Date: $date")
    }

}

/*
    Main class
 */
class ExpenseTracker  {

    private var userMap : MutableMap<String, User> = mutableMapOf()
    private var expenseTypeMap : MutableMap<String, ExpenseType> = mutableMapOf()
    private var expenseRecordList : MutableList<ExpenseRecord> = mutableListOf()

    // Function to check user credential is correct or not
    fun checkUserCredential(name : String, password : String): Int {
        // Check if the user is already sign in or not
        if ( userMap.containsKey(name) ) {
            if (userMap[name]!!.isPasswordCorrect(password)) {
                println("User Credentials verified")
                return  userMap[name]!!.getUserId()
            }
            else {
                println("Wrong Password")
                return -1
            }
        }
        println ("User Not signed In")
        return -2
    }

    // Function to Create new user
    fun signUpUser (name : String, password: String, income: Float) : Int {

        // If the User Already Available If yes return 1 to indicate user already available
        if (userMap.containsKey(name)) {
            println("User All ready exists")
        }
        // Create new User and return the user Id
        else {
            val user = User(
                userName = name, password = password,
                income = income,
            )
            userMap[name] = user
            println("User created")
        }
        return userMap[name]!!.getUserId()
    }

    // Function to create new expense If it does not available
    fun createExpenseType (expenseType: String, description: String) : Boolean {
        if (!expenseType.contains(expenseType)) {
            val expenseTypeObject = ExpenseType(expenseType, description)
            expenseTypeMap[expenseType] = expenseTypeObject
            return true
        }
        return false
    }

    //Function to record the expense For the user
    fun recordExpense(userId: Int, expenseId: Int, amount: Float, date: String, description: String) : Int {
        val record = ExpenseRecord(userId, expenseId, amount, date, description)
        expenseRecordList.add(record)
        return record.getRecordId()
    }

    //Function to display each expenseType available to make user to select expenseType to record Expense
    fun displayExpenseType() {
        for (expenseType in expenseTypeMap.values) {
            expenseType.display_ExpensType_id()
        }
    }

    fun displayUserExpenseRecord(userId : Int) {
        for (record in expenseRecordList) {
            if (record.getUserId() == userId) {
                record.displayExpenseDetails()
            }
        }
    }
}

fun recordUserExpense(app : ExpenseTracker, scanner : Scanner, userId : Int) {

    // Menu driven to handle Expense Record
    while (true) {
        println("\n===== Record Expense Menu =====")
        println("1. Record Expense")
        println("2. Create New ExpenseType")
        println("3. Delete Record")
        println("4. Edit Record")
        println("5. Exit the App")
        print("Enter your choice: ")

        when (scanner.nextInt()) {
            1 -> {
                app.displayExpenseType()
                print("Enter the Expense Type Id:")
                val expenseTypeId = scanner.nextInt()
                print("Enter Amount Spend:")
                val expenseAmount = scanner.nextFloat()
                print("Enter description for this expense:")
                val expenseDescription = scanner.next()
                print("Enter the date of expense:")
                val expenseDate = scanner.next()
                val response = app.recordExpense(
                    userId,
                    expenseId = expenseTypeId,
                    amount = expenseAmount,
                    date = expenseDate,
                    description = expenseDescription,
                )
            }

            2 -> {
                print("Enter Expense Type: ")
                val expenseType = scanner.next() // Read user input
                print("Enter Description: ")
                val description = scanner.next()
                val response = app.createExpenseType(expenseType, description)
                if (response) {
                    println("Expense Type Created Successfully")
                }
                else {
                    println("Expense Type Already Available")
                }
            }

            3 -> {
                app.displayUserExpenseRecord(userId)
                print("Enter Record Id to delete the record: ")
                val recordId = scanner.next() // get Record Id from user to delete the record

            }

            else -> println("Invalid choice. Please enter a valid option.")
        }
    }



}

fun main() {

    println("Welcome to Personal Expense Tracker")

    // Initialization
    val scanner = Scanner(System.`in`)
    val app = ExpenseTracker()
    var option: Int

    // Menu driven to handle User Login and Sign In
    while (true) {
        println("\n===== Menu =====")
        println("1. Login")
        println("2. Sign Up")
        println("3. Exit")
        print("Enter your choice: ")
        option = scanner.nextInt()

        when (option) {
            1 -> {
                print("Enter User Name: ")
                val userName = scanner.next() // Read user input
                print("Enter Password: ")
                val password = scanner.next()
                val response = app.checkUserCredential(userName, password)
                if (response != 0) {
                    recordUserExpense(app, scanner, userId = response)
                }
            }

            2 -> {
                print("Enter User Name: ")
                val userName = scanner.next() // Read user input
                print("Enter Password: ")
                val password = scanner.next()
                print("Enter the Income")
                val income = scanner.nextFloat()
                val response = app.signUpUser(userName, password, income)
                recordUserExpense(app, scanner, userId = response)
            }

            3 -> {
                println("Exiting program. Goodbye!")
                break
            }

            else -> println("Invalid choice. Please enter a valid option.")
        }
    }
}