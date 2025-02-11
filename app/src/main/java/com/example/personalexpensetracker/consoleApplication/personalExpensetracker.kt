package com.example.personalexpensetracker.consoleApplication
//
///*
//    This is class Declaration for user. Amount spend will be 0 and the
//    remaining balance will be the income amount as no amount is spent at the time of User object creation
// */
//class User1
//{
//    private var _userName : String
//    private var _userId : Int
//    private var _income : Float
//    private var _amountSpend : Float
//    private var _remainingAmount : Float
//
//    // This object is used to generate automatic userId while Creating new User Object
//    companion object {
//        private var idGenerator : Int = 0
//        fun generateUserId(): Int {
//            return ++idGenerator
//        }
//    }
//    constructor()
//    {
//        _userId = generateUserId()
//        _userName = ""
//        _income = 0F
//        _amountSpend = 0F
//        _remainingAmount = 0F
//    }
//
//    /*
//        Parameterized constructor which has default value Used to create user with userName remaining
//        variable can be assigned later
//     */
//
//    constructor(userName : String, income : Float = 0F, amountSpend : Float = 0F)
//    {
//        _userId = generateUserId()
//        this._userName = userName
//        this._income = income
//        this._amountSpend = amountSpend
//        this._remainingAmount = _income - amountSpend
//    }
//
//    // Getter function to get USerId
//    fun getUserId() : Int
//    {
//        return _userId
//    }
//
//    fun setUserName(name : String) {
//        _userName = name
//    }
//
//    fun setIncome (amount : Float) {
//        _income = amount
//    }
//
//    fun addExpenseAmount (expenseAmount : Float) {
//        _amountSpend += expenseAmount
//
//        // Update the Remaining amount as it decrease while expense is recorded
//        _remainingAmount -= expenseAmount
//    }
//
//    // Function to display User Object Details
//    fun displayUserDetails()
//    {
//        println("User Object Details")
//        println("UserId:$_userId")
//        println("User Name:$_userName")
//        println("Income:$_income")
//        println("AmountSpend:$_amountSpend")
//        println("Remaining Amount:$_remainingAmount")
//    }
//}
//
///*
//    This cass is to declare the Expense object Template
// */
//class Expense1 ( expenseName: String, description : String )
//{
//    private var expenseId : Int
//    private val expenseName : String // Expense Name is immutable as once name is initialized it cannot be changed
//    private var description : String
//    // This object is used to generate automatic Expense ID everyTime Creating new  Expense Object
//    companion object {
//        private var idGenerator : Int = 0
//        fun getNextExpenseId(): Int {
//            return ++idGenerator
//        }
//    }
//
//    init {
//        expenseId = getNextExpenseId()
//        this.expenseName = expenseName
//        this.description = description
//    }
//
//    // Getter function to get Expense ID
//    fun getexpenseId() : Int
//    {
//        return expenseId
//    }
//
//    // Function to display Expense Object Details
//    fun displayExpenseDetails()
//    {
//        println("EXpense Object Details")
//        println("Expense Name:$expenseName")
//        println("Expense Id:$expenseId")
//        println("Description:$description")
//    }
//}
//
///*
//    This class is used to record expenses related to which user
// */
//class ExpenseRecord1 (expenseId : Int, userId : Int, amount : Float, description: String = "", date: String = "")
//{
//    private var recordId : Int
//    private var expenseId : Int
//    private var userId : Int
//    private var amount : Float
//    private var description : String
//    private var date : String
//
//    // This object is used to generate automatic Record ID everyTime while recording new expense
//    companion object {
//        private var idGenerator : Int = 0
//        fun getNextRecordId(): Int {
//            return ++idGenerator
//        }
//    }
//
//    init {
//        recordId = getNextRecordId()
//        this.expenseId = expenseId
//        this.userId = userId
//        this.description = description
//        this.date = date
//        this.amount = amount
//    }
//
//    // Getter function to get Expense Record Id
//    fun getExpenseRecordId() : Int
//    {
//        return recordId
//    }
//
//
//    // Function to display Record Object Details
//    fun displayExpenseDetails()
//    {
//        println("Record  Details")
//        println("Expense ID:$expenseId")
//        println("user Id:$userId")
//        println("Description:$description")
//        println ("Amount: $amount")
//        println("Date: $date")
//    }
//}
//
//
//fun main()
//{
////    val expenseRecords = mutableListOf<ExpenseRecord>() // This is a array of expense records
////    var user1 = User (
////        userName = "Durai", income = 16200F,
////    )
////    var user2 = User (
////        userName = "Vignesh", income = 19500F,
////    )
////    var foodExpense = Expense (
////        expenseName = "Food Expense",
////        description = "This expense is used to record cost used for food"
////    )
////    var petrolExpense = Expense(
////        expenseName = "Petrol",
////        description = "Petrol Charges For Bike"
////    )
////
////    var record1 = ExpenseRecord(
////        expenseId = foodExpense.expenseId,
////        userId = user1.userId,
////        amount = 500F,
////        description = "KFC",
////        date = "10/03/2025"
////    )
////    var record2 = ExpenseRecord(
////        expenseId = petrolExpense.expenseId,
////        userId = user1.userId,
////        amount = 800F,
////        description = "Indian Petrol Bunk",
////        date = "10/03/2025"
////    )
////
////    expenseRecords.add(record1)
////    expenseRecords.add(record2)
////
////    for (record in expenseRecords) {
////        record.displayExpenseDetails()
////    }
//
//}
//
