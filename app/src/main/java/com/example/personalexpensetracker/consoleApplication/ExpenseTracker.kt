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

//     This object is used to generate automatic User ID everyTime Creating new  User Object
    companion object {
        private var idGenerator : Int = 0
        fun generateUserId(): Int {
            return ++idGenerator
        }
    }

    // Getter Method to access private variable UserId
    fun getUserId() : Int {
        return this.userId
    }

    // Getter Method to access private variable user Name
    fun getUserName() : String {
        return this.userName
    }

    // Method to verify the user name and password
    fun isPasswordCorrect(password : String) :Boolean {
        return this.password == password
    }

    // Setter method to chance user Name
    fun setName(name : String) {
        this.userName = name
    }
    fun setPassword(password: String) {
        this.password = password
    }
    fun setIncome (amount: Float) {
        this.income = amount
    }

    // Setter method to update the amount spend
    fun addAmountSpend(amount: Float) {
        this.amountSpend += amount
    }

    // Method to display User Object Details
    fun displayUserDetails() {
        println("\n========================= User Details =========================")
        println("User ID        : $userId")
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

    // Getter Method
    fun getExpenseType() : String {
        return this.expenseType
    }

    fun getExpenseTypeId() : Int {
        return this.expenseTypeId
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
    private var expenseTypeId : Int,
    private var userId : Int,
    private var amount : Float,
    private var date: String,
    private var description: String = ""
) {
    private var recordId: Int = generateRecordId()

//     This object is used to generate automatic Record ID everyTime Creating new  Record Object
    companion object {
        private var idGenerator: Int = 1
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

    // Getter Method
    fun getAmountSpend () : Float {
        return this.amount
    }

    // Setter Function
    fun setRecordAmount (amount: Float) {
        this.amount = amount
    }

    //Method to display Record Object Details
    fun displayExpenseDetails() {

        println("Record ID     : $recordId")
        println("Expense ID    : $expenseTypeId")
        println("User ID       : $userId")
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
    private var userList : MutableList<User> = mutableListOf()
    private var expenseTypeList : MutableList<ExpenseType> = mutableListOf()
    private var expenseRecordList : MutableList<ExpenseRecord> = mutableListOf()

    init {
        val expenseType = ExpenseType("uncategorized", "This type is default type")
        expenseTypeList.add(expenseType)
    }

    /*
    Utility Functions to check if the record is available or not
     */
    // Get user from List
    private fun getUserFromList(name : String) : User? {
        return userList.find { it.getUserName() == name }
    }

    // Get record From record List
    private fun getRecordFromList(recordId : Int) : ExpenseRecord? {
        return expenseRecordList.find { it.getRecordId() == recordId }
    }

    // Get user from List
    private fun getUserFromList(userId : Int) : User? {
        return userList.find { it.getUserId() == userId }
    }

    // Method to check user credential is correct or not
    fun checkUserCredential(name : String, password : String): Int {
        // Check if the user is already sign in or not
        val user = getUserFromList(name)
        if (user != null) {
            if (user.isPasswordCorrect(password)) {
                println("User Credentials verified")
                return user.getUserId()
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
        // Create new User and return the user Id
        val user = User(
            userName = name, password = password,
            income = income,
        )
        userList.add(user)
        println("User created")
        return user.getUserId()
    }

    // Method to change user Name
    fun editUserName(userId : Int, userName: String) : Int {
        val user = getUserFromList(userId)
        user!!.setName(userName)
        return 1
    }
    // Method to change user Password
    fun editUserPassword(userId : Int, password : String) : Int {
        val user = getUserFromList(userId)
        user!!.setPassword(password)
        return 1
    }

    // Method to change user Income
    fun editUserIncome(userId : Int, amount: Float) : Int {
        val user = getUserFromList(userId)
        user!!.setIncome(amount)
        return 1
    }

    fun isExpenseTypeExist(expenseTypeId : Int) : Boolean {
        return expenseTypeList.find { it.getExpenseTypeId() == expenseTypeId } != null
    }

    // Method to create new expense If it does not available
    fun createExpenseType (expenseType: String, description: String) : Boolean {
        // Check to confirm that the entered Expense type not already exists. Then create new expense type or return false to indicate It already exists
        if (expenseTypeList.find{ it.getExpenseType() == expenseType} != null) {
            val expenseTypeObject = ExpenseType(expenseType, description)
            expenseTypeList.add(expenseTypeObject)
            return true
        }
        return false
    }


    // Method to Display User Details
    fun displayUserDetails(userId: Int) {
        getUserFromList(userId)!!.displayUserDetails()
    }

    //Method to display each expenseType available to make user to select expenseType to record Expense
    fun displayExpenseType() {
        println("\n============= Available Expense Types =============")
        for (expenseType in expenseTypeList) {
            expenseType.displayExpenseTypeId()
        }
        println("===================================================\n")  // To display details in well structured format
    }

    //Method to record the expense For the user
    fun recordExpense(userId : Int, expenseId: Int, amount: Float, date: String, description: String) : Int {

        val record = ExpenseRecord(expenseId, userId, amount, date, description)
        expenseRecordList.add(record)
        val user = getUserFromList(userId)
        user!!.addAmountSpend(amount)
        return 1
    }

    // Method to delete user Expense Record
    fun deleteUserExpenseRecord(userId: Int, recordId : Int): Boolean {
        // Get the index the record to delete it
        val recordIndex = expenseRecordList.indexOfFirst { it.getRecordId() == recordId }
        if (recordIndex != -1) {
            // get the user object to reduce the amount spend as expense record deleted the amount should be subtracted from amount spend for that user
            val user = getUserFromList(userId)
            user!!.addAmountSpend(-expenseRecordList[recordIndex].getAmountSpend()) // The amount need to be subtract from amount spend in user Object
            // Delete the record at that particular index
            expenseRecordList.removeAt(recordIndex)
            return true
        }
        return false
    }

    // Method to edit Record
    fun editExpenseRecord(recordId : Int, amount : Float) : Boolean {
        val record = getRecordFromList(recordId)
        if (record != null) {
            // Subtract the existing amount in amount spend of user
            val user = getUserFromList(record.getUserId())
            val amountDiff = record.getAmountSpend() - amount // Calculate the amount different and update the user Object
            record.setRecordAmount(amount)
            user!!.addAmountSpend(-amountDiff)
            return true
        }
        return false
    }

    // Method to display the expense type of a particular user
    fun displayUserExpenseRecord(userId : Int) {
        var flag = 0 // To check at least one record is record
        for (record in expenseRecordList) {
            if (record.getUserId() == userId) {
                flag = 1
                println("\n============= User Expense Records =============")
                record.displayExpenseDetails()
                println("===============================================\n")  // To display details in well structured format
            }
        }
        if ( flag == 0) {
            println("No Record Found for the user")
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
        println("5. Display Records")
        println("6. Go back to user Section")
        print("Enter your choice: ")

        when (scanner.nextInt()) {
            // Record Expense section
            1 -> {
                app.displayExpenseType()
                print("Enter the Expense Type Id:")
                val expenseTypeId = scanner.nextInt()
                if (!app.isExpenseTypeExist(expenseTypeId)) {
                    println("Expense Type Not Found")
                    continue
                }
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
                app.displayUserExpenseRecord(userId)
                print("Enter Record Id to delete the record: ")
                val recordId = scanner.nextInt() // get Record Id from user to delete the record subtract 1 as record Index start from 0
                if (app.deleteUserExpenseRecord(recordId, userId)) {
                    println("Record Successfully Deleted")
                }
                else {
                    println("Error Occurred")
                }
            }
            4 -> {
                app.displayUserExpenseRecord(userId)
                print("Enter Record Id to Edit the record: ")
                val recordId = scanner.nextInt() // get Record Id from user to delete the record
                println ("Enter the Amount need to Edit:")
                val amount = scanner.nextFloat()
                app.editExpenseRecord(recordId, amount)
            }
            // Displaying User Expense Record Section
            5 -> {
                app.displayUserExpenseRecord(userId)
            }
            6 -> {
                println("Exiting program. Goodbye!")
                break
            }
            else -> println("Invalid choice. Please enter a valid option.")
        }
    }
}

// User Setting function
fun userDetailMenu ( app : ExpenseTracker, scanner : Scanner, userId : Int) {
    // Menu driven to handle User Login and Sign In
    while (true) {
        println("\n===== Menu =====")
        println("1. Edit User Name")
        println("2. Change Password")
        println("3. Change Income")
        println("4.Display User Details")
        println("5. Record Expense")
        println("6. Logout")
        print("Enter your choice: ")
        val option = scanner.nextInt()

        when (option) {
            1 -> {
                print("Enter User Name: ")
                val userName = scanner.next() // Read user input
                val response = app.editUserName(userId, userName)
                if (response == 1) {
                    println("Update Successful")
                }
            }

            2 -> {
                print("Enter User Password: ")
                val password = scanner.next() // Read user input
                val response = app.editUserPassword(userId, password)
                if (response == 1) {
                    println("Update Successful")
                }
            }

            3 -> {
                print("Enter User Income: ")
                val income = scanner.nextFloat() // Read user input
                val response = app.editUserIncome(userId, income)
                if (response == 1) {
                    println("Update Successful")
                }
            }
            4 -> {
                app.displayUserDetails(userId)
            }
            5 -> {
                recordUserExpense(app, scanner, userId)
            }

            6 -> {
                println("Exiting. Goodbye!")
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

    // Menu driven to handle User Login and Sign In
    while (true) {
        println("\n===== Menu =====")
        println("1. Login")
        println("2. Sign Up")
        println("3. Exit")
        print("Enter your choice: ")
        val option = scanner.nextInt()

        when (option) {
            1 -> {
                print("Enter User Name: ")
                val userName = scanner.next() // Read user input
                print("Enter Password: ")
                val password = scanner.next()
                val response = app.checkUserCredential(userName, password)
                if (response > 0) {
                    userDetailMenu(app, scanner, userId = response)
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
                userDetailMenu(app, scanner, userId = response)
            }
            3 -> {
                println("Exiting program. Goodbye!")
                break
            }
            else -> println("Invalid choice. Please enter a valid option.")
        }
    }
}
