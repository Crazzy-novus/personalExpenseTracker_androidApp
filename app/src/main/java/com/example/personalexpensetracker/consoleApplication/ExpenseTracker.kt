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
//    private var userId : Int = generateUserId()
    private var amountSpend : Float = 0F

    // This object is used to generate automatic User ID everyTime Creating new  User Object
//    companion object {
//        private var idGenerator : Int = 0
//        fun generateUserId(): Int {
//            return ++idGenerator
//        }
//    }

//    // Getter Method to access private variable UserId
//    fun getUserId() : Int {
//        return this.userId
//    }

    // Method to verify the user name and password
    fun isPasswordCorrect(password : String) :Boolean {
        return this.password == password
    }

    // Setter method to update the amount spend
    fun addAmountSpend(amount: Float) {
        this.amountSpend += amount
    }

    // Method to display User Object Details
    fun displayUserDetails() {
        println("\n========================= User Details =========================")
//        println("User ID        : $userId")
        println("User Name      : $userName")
        println("Income         : $$income")
        println("Amount Spent   : $$amountSpend")
        println("Remaining      : $${income - amountSpend}")
        println("==============================================================\n")  // To display details in well structured format
    }
}
/*
    This is class Declaration for Expense Type. Expense Type Id is automatically generated and it is unique.
    Description field is optional
 */

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
    // Method to display Expense Object Details
    fun displayExpenseDetails() {
        println("\n============= Expense Type Details =============")
        println("Expense Name  : $expenseType")
        println("Expense ID    : $expenseTypeId")
        println("Description   : $description")
        println("===========================================\n")
    }

    fun displayExpenseTypeId() {
        println("Expense: $expenseType \t| ID: $expenseTypeId")
    }
}
/*
    This class is used to record expenses related to which user
 */
class ExpenseRecord(
    private var expenseId : Int,
    // private var userId : Int,
     private var amount : Float,
     private var date: String,
     private var description: String = ""
) {
//    private var recordId: Int = generateRecordId()

    // This object is used to generate automatic Record ID everyTime Creating new  Record Object
//    companion object {
//        private var idGenerator: Int = 1
//        fun generateRecordId(): Int {
//            return ++idGenerator
//        }
//    }
//    fun getRecordId () : Int {
//        return recordId
//    }
//    fun getUserId() :Int {
//        return userId
//    }

    // Getter Method
    fun getAmountSpend () : Float {
        return this.amount
    }

    //Method to display Record Object Details
    fun displayExpenseDetails() {

//        println("Record ID     : $recordId")
        println("Expense ID    : $expenseId")
        //println("User ID       : $userId")
        println("Amount        : $$amount")
        println("Date          : $date")
        println("Description   : $description")
        println("------------------------------------------\n") // To display details in well structured format
    }
}

/*
    Main class
 */
class ExpenseTracker  {

    private var userMap : MutableMap<String, User> = mutableMapOf()
    private var expenseTypeMap : MutableMap<String, ExpenseType> = mutableMapOf()
    private var expenseRecordMap : MutableMap<String, MutableList<ExpenseRecord>> = mutableMapOf() //TODO() need to check whether it need to be map(recordId, record)/ map(userId, record[])

    init {
        val expenseType = ExpenseType("uncategorized", "This type is default type")
        expenseTypeMap["uncategorized"] = expenseType
    }

    /*
    Utility Functions to check if the record is available or not
     */
    // Generic Method to accept all data types such as for USer, ExpenseType, ExpenseRecord
    private fun <String, V> containKey(dataMap : MutableMap<String, V>, identityKey : String) : Boolean {
        return dataMap.containsKey(identityKey)
    }

    // Method to check user credential is correct or not
    fun checkUserCredential(name : String, password : String): Int {
        // Check if the user is already sign in or not
        if ( containKey(userMap, name) ) {
            if (userMap[name]!!.isPasswordCorrect(password)) {
                println("User Credentials verified")
                return  1
            }
            else {
                println("Wrong Password")
                return -1
            }
        }
        println ("User Not signed In")
        return -2
    }

    // Method to Create new user
    fun signUpUser(name : String, password: String, income: Float) : Int {

        // If the User Already Available If yes return 1 to indicate user already available
        if (containKey(userMap, name)) {
            println("User All ready exists")
            return 0
        }
        // Create new User and return the user Id
        val user = User(
            userName = name, password = password,
            income = income,
        )
        userMap[name] = user
        println("User created")
        return 1
    }

    // Method to create new expense If it does not available
    fun createExpenseType (expenseType: String, description: String) : Boolean {
        // Check to confirm that the entered Expense type not already exists. Then create new expense type or return false to indicate It already exists
        if (!containKey(expenseTypeMap, expenseType)) {
            val expenseTypeObject = ExpenseType(expenseType, description)
            expenseTypeMap[expenseType] = expenseTypeObject
            return true
        }
        return false
    }

    //Method to display each expenseType available to make user to select expenseType to record Expense
    fun displayExpenseType() {
        println("\n============= Available Expense Types =============")
        for (expenseType in expenseTypeMap.values) {
            expenseType.displayExpenseTypeId()
        }
        println("===================================================\n")  // To display details in well structured format
    }

    //Method to record the expense For the user
    fun recordExpense(userName: String, expenseId: Int, amount: Float, date: String, description: String) : Int {
        if (!containKey(expenseRecordMap, userName)) {
            expenseRecordMap[userName] = mutableListOf()
        }
        val record = ExpenseRecord(expenseId, amount, date, description)
        expenseRecordMap[userName]!!.add(record)
        // Add the expense amount to amount spend in suer Object
        userMap[userName]!!.addAmountSpend(amount)
        return 1
    }

    // Method to delete user Record
    fun deleteUserRecord(userName: String, recordIndex : Int): Boolean {
        if (containKey(expenseRecordMap, userName)) {
            // Temporary variable store amount mentioned in record which need to reduce from amount spend in user Object
            val recordAmount = expenseRecordMap[userName]!![recordIndex].getAmountSpend()
            userMap[userName]!!.addAmountSpend(-recordAmount) // The amount need to be subtract from amount spend in user Object
            // Record number reference the index number but it start from 1 so subtract 1 to get index number
            expenseRecordMap[userName]!!.removeAt(recordIndex - 1)
            return true
        }
        return false
    }

    // Method to edit Record


    // Method to display the expense type of a particular user
    fun displayUserExpenseRecord(userName: String) {
        if (containKey(expenseRecordMap, userName)) {
            println("\n============= User Expense Records =============")
            // Here index field is used to mention the record number. User need to mention record number to delete
            for ((index, record) in expenseRecordMap[userName]!!.withIndex()) {
                println("Record No     : ${index + 1}")
                record.displayExpenseDetails()
            }
            println("===============================================\n")  // To display details in well structured format
        }
        else {
            println("No Record Found for the user")
        }
    }
}

fun recordUserExpense(app : ExpenseTracker, scanner : Scanner, userName: String) {

    // Menu driven to handle Expense Record
    while (true) {
        println("\n===== Record Expense Menu =====")
        println("1. Record Expense")
        println("2. Create New ExpenseType")
        println("3. Delete Record")
        println("4. Edit Record")
        println("5. Display Records")
        println("6. Exit the App")
        print("Enter your choice: ")

        when (scanner.nextInt()) {
            // Record Expense section
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
                    userName,
                    expenseId = expenseTypeId,
                    amount = expenseAmount,
                    date = expenseDate,
                    description = expenseDescription,
                )
                if (response == 1) {
                    println("Record Added Successfully")
                }

            }
            // Create new Expense Type section
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
            // Delete Record Section
            3 -> {
                app.displayUserExpenseRecord(userName)
                print("Enter Record Id to delete the record: ")
                val recordNo = scanner.nextInt() - 1 // get Record Id from user to delete the record subtract 1 as record Index start from 0
                if (app.deleteUserRecord(userName, recordNo)) {
                    println("Record Successfully Deleted")
                }
                else {
                    println("Error Occurred")
                }
            }
            4 -> {
                app.displayUserExpenseRecord(userName)
                print("Enter Record Id to delete the record: ")
                val recordNo = scanner.nextInt() // get Record Id from user to delete the record
            }
            // Displaying User Expense Record Section
            5 -> {
                app.displayUserExpenseRecord(userName)
            }
            6 -> {
                println("Exiting program. Goodbye!")
                break
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
                    recordUserExpense(app, scanner, userName)
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
                recordUserExpense(app, scanner, userName)
            }
            3 -> {
                println("Exiting program. Goodbye!")
                break
            }
            else -> println("Invalid choice. Please enter a valid option.")
        }
    }
}