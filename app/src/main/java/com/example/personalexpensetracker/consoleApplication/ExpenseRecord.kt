package com.example.personalexpensetracker.consoleApplication

/*
    This class is used to record expenses related to which user
 */
data class ExpenseRecord
    (
    private var expenseCategoryId : Int,
    private var userId : Int,
    private var amount : Float,
    private var date: String,
    private var description: String = ""
)
{
    private var recordId: Int = generateRecordId()

    //This object is used to generate automatic Record ID everyTime Creating new  Record Object
    companion object
    {
        private var idGenerator: Int = 0
        fun generateRecordId(): Int
        {
            return ++idGenerator
        }
    }

    // Getter Methods
    fun getRecordId () : Int
    {
        return recordId
    }
    fun getUserId() :Int
    {
        return userId
    }
    fun getAmountSpend () : Float
    {
        return this.amount
    }

    // Setter Function
    fun setRecordAmount (amount: Float)
    {
        this.amount = amount
    }
    // Setter function
    fun setRecordDate(date : String)
    {
        this.date = date
    }

    //Method to display Record Object Details
    fun displayExpenseDetails()
    {

        println("Record ID     : $recordId")
        println("Expense ID    : $expenseCategoryId")
        println("User ID       : $userId")
        println("Amount        : $$amount")
        println("Date          : $date")
        println("Description   : $description")
        println("------------------------------------------\n") // To display details in well structured format
    }
}