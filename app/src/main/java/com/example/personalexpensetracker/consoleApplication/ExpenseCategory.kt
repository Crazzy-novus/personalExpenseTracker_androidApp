package com.example.personalexpensetracker.consoleApplication

/*
    This is class Declaration for Expense Category. Expense Category Id is automatically generated and it is unique.
    Description field is optional
 */

class ExpenseCategory (
    private val expenseCategory: String,
    private var description : String = ""
)
{
    private var expenseCategoryId: Int = generateExpenseCategoryId()

    // This object is used to generate automatic Expense ID everyTime Creating new  Expense Object
    companion object
    {
        private var idGenerator: Int = 0
        fun generateExpenseCategoryId(): Int
        {
            return ++idGenerator
        }
    }

    // Getter Method
    fun getExpenseCategory() : String
    {
        return this.expenseCategory
    }
    fun getExpenseCategoryId() : Int
    {
        return this.expenseCategoryId
    }
/*
    In this function Flag is used to indicate whether the function is called to display hole Expense Category
    Details or displayed to notify the available category to user while recording Expense
 */
    fun displayExpenseCategory(flag : Int = 0)
    {
        println("Expense Category ID    : $expenseCategoryId")
        println("Expense Category       : $expenseCategory")
        if (flag == 0)
        {
            println("Description        : $description")
        }
        println("------------------------------------------\n") // To display details in well structured format
    }
}
