package com.example.personalexpensetracker.consoleApplication

//Utility Class
// Generic response class for consistent responses across the application
class Response <T>
    (
    val responseCode : Int, // Success (1) or Failure (-1)
    val data : T?, // Actual data being returned
    val message : String // Holds a success message if operation succeed or null

)
