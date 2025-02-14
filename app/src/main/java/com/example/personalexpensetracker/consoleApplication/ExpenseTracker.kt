package com.example.personalexpensetracker.consoleApplication
import java.util.Scanner
/*
    Main class which stores userDetails, ExpenseCategory, User's Expense Record
 */
class ExpenseTracker {
    private var userList: MutableList<User> = mutableListOf()
    private var expenseCategoryList: MutableList<ExpenseCategory> = mutableListOf()
    private var expenseRecordList: MutableList<ExpenseRecord> = mutableListOf()

    // Initializing the uncategorized expense as default expense while starting the expenseTrackerApplication
    init {
        val expenseCategory = ExpenseCategory("uncategorized", "This type is default type")
        expenseCategoryList.add(expenseCategory)
    }

    //Utility Methods
    // Get user from List using User Id
    private fun getUserFromList(userId: Int? = null, userName: String? = null): User?
    {
        if ( userName != null)
        {
            return userList.find { it.getUserName() == userName }
        }
        return userList.find { it.getUserId() == userId }
    }

    // Get Expense record From record List
    private fun getRecordFromList(recordId: Int): ExpenseRecord? {
        return expenseRecordList.find { it.getRecordId() == recordId }
    }

    // Method to Create new user
    private fun signUpUser(name: String, password: String): Response<Int>
    {
        // Create new User and return the user Id
        if (getUserFromList(userName = name) != null)
        {
            return Response(-1, null, "User Already exists. Please login.")
        }
        val user = User(name, password)
        userList.add(user)
        return Response(1, user.getUserId(), "User created successfully.")
    }

    // Method to check user credential is correct or not
    private fun checkUserCredential(name: String, password: String): Response<Int>
    {
        // Check if the user is already sign in or not
        val user = getUserFromList(userName = name) ?: return Response(-2, null, "User Not Signed In")

        return if (user.isPasswordCorrect(password))
        {
            Response(1, user.getUserId(), "User Credentials Verified")
        }
        else
        {
            Response(-1, null, "Wrong Password")
        }
    }

    // Method to change user Name
    private fun editUserName(userId: Int, userName: String): Response<User>
    {
        val user = getUserFromList(userId)
        user?.setName(userName)
        return Response(1, user, "User Name updated.")

    }

    // Method to change user Password
    private fun editUserPassword(userId: Int, oldPassword: String, newPassword: String): Response<User>
    {
        val user = getUserFromList(userId)
        if (!user!!.isPasswordCorrect(oldPassword))
        {
            return Response(-1, null, "Wrong Old Password Update Failed!!!")
        }
        user.setPassword(newPassword)
        return Response(1, user, "User Password updated.")
    }

    // Method to change user Income
    private fun setUserIncome(userId: Int, amount: Float): Response<User>
    {
        val user = getUserFromList(userId)
        user!!.setIncome(incomeAmount = amount)
        return Response(1, user, "User Income updated.")
    }
//    private fun editUserDetails(userId : Int, updateField : String, userName : String? = null, password: String? = null, income : Float? = null): Response<User>
//    {
//        val user = getUserFromList(userId)
//        if (userName != null)
//        {
//            user?.setName(userName)
//            return Response(1, user, "User Name updated.")
//        }
//        if ()
//
//    }

    // Method to Display User Details
    private fun displayUserDetails(userId: Int)
    {
        getUserFromList(userId)!!.displayUserDetails()
    }

    // Method to check Particular expense Category is present in the expense Category list using ExpenseId
    private fun isExpenseCategoryExist(expenseCategoryId: Int): Boolean
    {
        return expenseCategoryList.any { it.getExpenseCategoryId() == expenseCategoryId }
    }

    // Method to check Particular expense Category is present in the expense Category list using ExpenseId
    private fun isExpenseCategoryExist(expenseCategory: String): Boolean
    {
        return expenseCategoryList.any { it.getExpenseCategory() == expenseCategory }
    }

    // Method to create new expense If it does not available
    private fun createExpenseCategory(expenseCategoryName: String, description: String): Response<ExpenseCategory?>
    {
        // Check to confirm that the entered Expense type not already exists. Then create new expense type or return false to indicate It already exists
        if (isExpenseCategoryExist(expenseCategoryName))
        {
            return Response(-1, null, "Expense Category Already Exists.")
        }

        val expenseCategory = ExpenseCategory(expenseCategoryName, description)
        expenseCategoryList.add(expenseCategory)
        return Response(1, expenseCategory, "Expense Category Created Successfully.")
    }

    //Method to display each expenseCategory available to make user to select expenseCategory to record Expense
    private fun displayExpenseCategory()
    {
        println("\n============= Available Expense Category's =============")
        for (expenseCategory in expenseCategoryList)
        {
            expenseCategory.displayExpenseCategoryId()
        }
        println("===================================================\n")  // To display details in well structured format
    }

    // Method to display all Details of expense record
    private fun displayExpenseCategoryDetails(expenseCategory: ExpenseCategory? = null) {
        println("\n============= User Expense Category =============") // To display details in well structured format
        if (expenseCategory != null)
        {
            expenseCategory.displayExpenseCategory()
        }
        else
        {
            expenseCategoryList.forEach { it.displayExpenseCategory() }
        }
        println("===============================================\n")
    }

    //Method to record the expense For the user
    private fun recordExpense( userId: Int, expenseId: Int, amount: Float, date: String, description: String): Response<ExpenseRecord>
    {

        val record = ExpenseRecord(expenseId, userId, amount, date, description)
        expenseRecordList.add(record)
        val user = getUserFromList(userId)
        user!!.addAmountSpend(amount) //  Update the amount spend in user Object when expense is recorded
        return Response(1, record, "Expense Record Created")
    }

    // Method to edit Record
    private fun editExpenseRecord(recordId: Int, amount: Float): Response<ExpenseRecord>
    {
        val record = getRecordFromList(recordId)
        if (record != null)
        {
            // Subtract the existing amount in amount spend of user
            val user = getUserFromList(record.getUserId())
            val amountDiff = record.getAmountSpend() - amount // Calculate the amount different and update the user Object
            record.setRecordAmount(amount)
            user!!.addAmountSpend(-amountDiff)
            return Response(1, record, "Expense Record Updated")
        }
        return Response(-1, null, "Unable to update the Record")
    }

    // Method to delete user Expense Record
    private fun deleteUserExpenseRecord(userId: Int, recordId: Int): Response<ExpenseRecord>
    {
        // Get the index the record to delete it
        val recordIndex = expenseRecordList.indexOfFirst { it.getRecordId() == recordId }
        if (recordIndex != -1)
        {
            // Before deleting the record amount spend field in User Need to be modified
            val user = getUserFromList(userId)
            user!!.addAmountSpend(-expenseRecordList[recordIndex].getAmountSpend()) // The amount need to be subtract from amount spend in user Object
            // Delete the record at that particular index
            expenseRecordList.removeAt(recordIndex)
            return Response(1, null, "Expense Record Deleted")
        }
        return Response(-1, null, "Unable to Delete the Record")
    }

    private fun isUserExpenseRecordExists(userId: Int): Boolean
    {
        return expenseRecordList.any { it.getUserId() == userId }
    }

    // Method to display the expense type of a particular user
    private fun displayUserExpenseRecord(userId: Int? = null, userExpenseRecord: ExpenseRecord? = null)
    {
        println("\n============= Expense Records =============") // To display details in well structured format
        if (userId != null)
        {
            for (record in expenseRecordList)
            {
                if (record.getUserId() == userId)
                {
                    record.displayExpenseDetails()
                }
            }
        } else
        {
            userExpenseRecord?.displayExpenseDetails()
        }
        println("===============================================\n")
    }


    // Function to check User enter the correct input format
    private fun getIntegerInput(scanner: Scanner, prompt: String): Int
    {

        var tempInputVariable: Int = -1
        do
        {
            if (scanner.hasNextInt())
            {
                tempInputVariable = scanner.nextInt()

                if (tempInputVariable <= 0)
                {
                    print("Input Cannot be Negative/Zero! Please enter a valid $prompt:")
                }
            }
            else
            {
                print("Invalid input! Please enter a valid $prompt:")
                scanner.next() // Consume invalid input
            }
        } while (tempInputVariable <= 0)
        return tempInputVariable
    }

    // Function to check User enter the correct input format
    private fun getFloatInput(scanner: Scanner, prompt: String): Float
    {

        var tempInputVariable = 0F
        do
        {
            if (scanner.hasNextFloat())
            {
                tempInputVariable = scanner.nextFloat()
                if (tempInputVariable <= 0)
                {
                    print("Input Cannot be Negative/Zero! Please enter a valid $prompt:")
                }
            } else
            {
                print("Invalid input! Please enter a valid $prompt:")
                scanner.nextLine() // Consume invalid input
            }
        } while (tempInputVariable <= 0)
        return tempInputVariable
    }

    // Function to manage Expense Category's
    private fun expenseCategoryMenu(scanner: Scanner)
    {
        while (true)
        {
            displayExpenseCategoryDetails()
            println("\n===== Expense Category Menu =====")
            println("Enter the corresponding number to perform that Action")
            println("1 -> Create New ExpenseCategory")
            //println("2 -> Display Expense Category")
            println("3 -> Go back to Expense Record Section")
            when (getIntegerInput(scanner, "Option"))
            {
                // Create new Expense Category section
                1 -> {
                    print("Enter Expense Category: ")
                    scanner.nextLine() // To clear buffer in console
                    val expenseCategory = scanner.nextLine() // Read user input
                    print("Enter Description: ")
                    val description = scanner.nextLine()
                    val response =
                        createExpenseCategory(expenseCategory, description)
                    if (response.responseCode == 1) {
                        println(response.message)
                        displayExpenseCategoryDetails(response.data!!)
                    } else {
                        println(response.message)
                        displayExpenseCategory()
                    }
                }
            }
        }
    }

    // Function to handle Record Expense Section
    private fun recordUserExpense( scanner: Scanner, userId: Int)
    {

        // Menu driven to handle Expense Record
        while (true)
        {
            println("\n===== Record Expense Menu =====")
            println("Enter the corresponding number to perform that Action")
            println("1 -> Record Expense")
            println("2 -> Edit Record")
            println("3 -> Delete Record")
            println("4 -> Display Records")
            println("5 -> Display Expense Category")
            println("6 -> Go back to user Section")
            print("Enter your choice: ")

            when (getIntegerInput(scanner, "Option"))
            {
                // Record Expense section
                1 -> {
                    displayExpenseCategory()
                    print("Enter the Expense Category Id:")
                    val expenseCategoryId = getIntegerInput(scanner, "Expense Category")
                    if (!isExpenseCategoryExist(expenseCategoryId))
                    {
                        println("Expense Category Not Found")
                        continue
                    }
                    print("Enter Amount Spend:")
                    val expenseAmount = getFloatInput(scanner, "Amount")
                    print("Enter description for this expense: ")
                    scanner.nextLine() // To clear buffer
                    val expenseDescription = scanner.nextLine()
                    print("Enter the date of expense: ")
                    val expenseDate = scanner.nextLine()
                    val response = recordExpense(
                        userId,
                        expenseId = expenseCategoryId,
                        amount = expenseAmount,
                        date = expenseDate,
                        description = expenseDescription,
                    )
                    if (response.responseCode == 1)
                    {
                        println(response.message)
                        displayUserExpenseRecord(userExpenseRecord = response.data)
                    } else
                    {
                        println(response.message)
                    }
                }
                // Edit Record Section
                2 -> {
                    if (isUserExpenseRecordExists(userId = userId))
                    {
                        displayUserExpenseRecord(userId)
                        print("Enter Record Id to Edit the record: ")
                        val recordId = getIntegerInput(
                            scanner,
                            "Record Id"
                        ) // get Record Id from user to delete the record
                        print("Enter the Amount need to Edit:")
                        val amount = getFloatInput(scanner, "Amount")
                        val response = editExpenseRecord(recordId, amount)
                        if (response.responseCode == 1)
                        {
                            println(response.message)
                            displayUserExpenseRecord(userExpenseRecord = response.data)
                        }
                        else
                        {
                            println(response.message)
                        }
                    }
                    else
                    {
                        println("No Record Found for the user to Edit")
                    }
                }
                // Delete Record Section
                3 -> {
                    if (isUserExpenseRecordExists(userId))
                    {
                        displayUserExpenseRecord(userId) // Display the available expense Category to select
                        print("Enter Record Id to delete the record: ")
                        val recordId = getIntegerInput(
                            scanner,
                            "Record Id"
                        ) // get Record Id from user to delete the record subtract 1 as record Index start from 0
                        val response = deleteUserExpenseRecord(userId, recordId)
                        if (response.responseCode == 1)
                        {
                            println(response.message)
                        }
                        else
                        {
                            println(response.message)
                        }
                    }
                    else
                    {
                        println("No Record Found for the user to Delete")
                    }
                }
                // Displaying User Expense Record Section
                4 -> {
                    displayUserExpenseRecord(userId)
                }
                // Move to display expense category section
                5 -> {
                    expenseCategoryMenu( scanner)
                }

                6 -> {
                    break
                }

                else -> println("Invalid choice. Please enter a valid option.")
            }
        }
    }

    private fun userSettingsMenu( scanner: Scanner, userId: Int ) {
        // Menu driven to handle User Login and Sign In
        displayUserDetails(userId) // Display User Details inside the user Settings Menu
        while (true)
        {
            println("\n===== Setting Menu =====")
            println("Enter the corresponding number to perform that Action")
            println("1 -> Edit Name")
            println("2 -> Change Password")
            println("3 -> Set/Change Income")
            println("4 -> Go Back to Main Menu")
            print("Enter your choice: ")
            when (getIntegerInput(scanner, "Option"))
            {
                // Edit User Name section
                1 -> {
                    print("Enter User Name: ")
                    scanner.nextLine() // To clear buffer in console
                    val userName = scanner.nextLine()
                    val response = editUserName(userId, userName)
                    if (response.responseCode == 1)
                    {
                        println(response.message)
                        response.data!!.displayUserDetails()
                    } else
                    {
                        println(response.message)
                    }
                }
                // Edit User Password section
                2 -> {
                    print("Enter Old Password: ")
                    scanner.nextLine() // To clear buffer in console
                    val oldPassword =
                        scanner.nextLine() // Get old password to verify the user is authenticated
                    print("Enter New Password: ")
                    val newPassword = scanner.nextLine()
                    val response =
                        editUserPassword(userId, oldPassword, newPassword)
                    if (response.responseCode == 1)
                    {
                        println(response.message)
                        response.data!!.displayUserDetails()
                    }
                    else
                    {
                        println(response.message)
                    }
                }
                // Edit User Income section
                3 -> {
                    print("Enter Income: ")
                    val income = getFloatInput(scanner, "Amount")
                    val response = setUserIncome(userId, income)
                    if (response.responseCode == 1)
                    {
                        println(response.message)
                        response.data!!.displayUserDetails()
                    }
                    else
                    {
                        println(response.message)
                    }
                }

                4 -> {
                    break
                }

                else -> println("Invalid choice. Please enter a valid option.")
            }
        }
    }

    // This function
    private fun userDetailMenu( scanner: Scanner, userId: Int)
    {
        // Menu driven to handle User Login and Sign In
        displayUserDetails(userId)
        while (true)
        {
            println("\n===== Main Menu =====")
            println("Enter the corresponding number to perform that Action")
            println("1 -> User Settings")
            println("2 -> Record Expense")
            println("3 -> Logout")
            print("Enter your choice: ")
            when (getIntegerInput(scanner, "Option"))
            {
                // Display User Details
                1 -> {
                    userSettingsMenu( scanner, userId)
                }
                // Record Expense Menu part
                2 -> {
                    recordUserExpense( scanner, userId)
                }
                // User Logout option part
                3 -> {
                    userValidationMenu( scanner) // Move back to login in Screen as user logout from the expenseTrackerApplication
                    break
                }

                else -> println("Invalid choice. Please enter a valid option.")
            }
        }
    }

// User Validation Menu which allow the user to login or logout

    private fun userValidationMenu(scanner: Scanner) {
        // Menu driven to handle User Login and Sign In
        while (true)
        {
            println("\n===== Menu =====")
            println("Enter the corresponding number to perform that Action")
            println("1 -> Login")
            println("2 -> Sign Up New USer")
            println("3 -> Exit")
            print("Enter your choice: ")

            when (getIntegerInput(scanner, "Option"))
            {
                // Login User Section
                1 -> {
                    print("Enter User Name:")
                    scanner.nextLine() // To clear buffer in console
                    val userName = scanner.nextLine()
                    print("Enter Password: ")
                    val password = scanner.nextLine()
                    val response = checkUserCredential(userName, password)
                    if (response.responseCode == 1)
                    {
                        println(response.message)
                        response.data?.let { userDetailMenu(scanner, userId = it ) } // Check for data is not null
                    } else {
                        println(response.message)
                    }
                }
                // Sign in user Section
                2 -> {
                    print("Enter User Name: ")
                    scanner.nextLine() // To clear buffer in console
                    val userName = scanner.nextLine()
                    print("Enter Password: ")
                    val password = scanner.nextLine()
                    val response = signUpUser(userName, password)
                    if (response.responseCode == 1)
                    {
                        println(response.message)
                        response.data?.let { userDetailMenu(scanner, userId = it) } //  Check for data is not null
                    }
                    else
                    {
                        println(response.message)
                    }
                }

                3 -> {
                    println("Exiting program. Goodbye!")
                    break
                }

                else -> println("Invalid choice. Please enter a valid option.")
            }
        }
    }

    fun main(scanner: Scanner)
    {
        println("Welcome to Personal Expense Tracker")
        // Menu driven to handle User Login and Sign In
        while (true)
        {
            println("\n===== Menu =====")
            println("Enter the corresponding number to perform that Action")
            println("1 -> Sign Up")
            println("2 -> Login")
            println("2 -> Exit")
            print("Enter your choice: ")

            when (getIntegerInput(scanner, "Option"))
            {
                // Login User Section
                1 -> {
                    print("Enter User Name: ")
                    scanner.nextLine() // To clear buffer in console
                    val userName = scanner.nextLine()
                    print("Enter Password: ")
                    val password = scanner.nextLine()
                    val response = signUpUser(userName, password)
                    if (response.responseCode == 1)
                    {
                        println(response.message)
                        response.data?.let {
                            userDetailMenu(

                                scanner,
                                userId = it
                            )
                        }
                    } else
                    {
                        println(response.message)
                    }
                }
                // Login User Section
                2 -> {
                    print("Enter User Name:")
                    scanner.nextLine() // To clear buffer in console
                    val userName = scanner.nextLine()
                    print("Enter Password: ")
                    val password = scanner.nextLine()
                    val response = checkUserCredential(userName, password)
                    if (response.responseCode == 1)
                    {
                        println(response.message)
                        response.data?.let { userDetailMenu( scanner, userId = it) } // Check for data is not null
                    }
                    else
                    {
                        println(response.message)
                    }
                }
                3 -> {
                    println("Exiting program. Goodbye!")
                    break
                }
                else -> println("Invalid choice. Please enter a valid option.")
            }
        }
    }
}
fun main() {
    println("Welcome to Personal Expense Tracker")
    // Initialization
    val scanner = Scanner(System.`in`)
    val expenseTrackerApp1 = ExpenseTracker()
    expenseTrackerApp1.main(scanner)
}
