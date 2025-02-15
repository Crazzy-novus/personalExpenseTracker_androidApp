package com.example.personalexpensetracker.consoleApplication


/*
    Data class representing a User with essential details such as username,
     password, Auto generated UserId, income and total amount Spend
 */

data class User (
    private var userName: String,
    private var phoneNumber: String,
    private var password : String
)
{
    private var userId : Int = generateUserId()
    private var income : Float = 0F
    private var amountSpend : Float = 0F // Total amount spend as expense

    // This object is used to generate automatic User ID everyTime Creating new  User Object
    companion object
    {
        private var idGenerator : Int = 0
        fun generateUserId(): Int
        {
            return ++idGenerator
        }
    }

    // Getter Method to access private variable UserId
    fun getUserId() : Int
    {
        return this.userId
    }

    // Getter Method to access private variable user Name
    fun getUserName() : String
    {
        return this.userName
    }
    // Getter Method to access private variable user Phone number
    fun getUserPhoneNumber() : String
    {
        return this.phoneNumber
    }

    // Method to verify the password of the user
    fun isPasswordCorrect(password : String) :Boolean
    {
        return this.password == password
    }

    // Setter methods to set value for the private member User name
    fun setName(name : String)
    {
        this.userName = name
    }
    // Setter methods to set value for the private member User password
    fun setPassword(password: String)
    {
        this.password = password
    }
    // Setter methods to set value for the private member Income
    fun setIncome (incomeAmount: Float)
    {
        this.income = incomeAmount
    }
    fun addAmountSpend(amount: Float)
    {
        this.amountSpend += amount
    }

    // Method to display User Object Details
    fun displayUserDetails()
    {
        println("\n========================= User Details =========================")
        println("User ID        : $userId")
        println("User Name      : $userName")
        println("Income         : $$income")
        println("Amount Spent   : $$amountSpend")
        println("Remaining      : $${income - amountSpend}")
        println("==============================================================\n")  // To display details in well structured format
    }
}