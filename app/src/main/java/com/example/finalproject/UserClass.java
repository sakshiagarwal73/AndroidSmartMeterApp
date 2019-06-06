package com.example.finalproject;

public class UserClass  {

    private String Email;
    private String Name;
    private int Consumer_Id;

    UserClass(int Consumer_Id,String Email,String Name)
    {
        this.Consumer_Id = Consumer_Id;
        this.Email = Email;
        this.Name = Name;
    }

    int getId()
    {
        return this.Consumer_Id;
    }

    String getEmail()
    {
        return this.Email;
    }

    String getName()
    {
        return this.Name;
    }
}
