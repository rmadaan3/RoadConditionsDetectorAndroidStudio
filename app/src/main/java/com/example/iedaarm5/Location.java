package com.example.iedaarm5;

public class Location
{
    private String fird;
    //empty constructor to avoid error
    public Location()
    {

    }
    public Location(String fird) 
    {
        this.fird = fird;
    }

    public String getFird() 
    {
        return fird;
    }

    public void setFird(String fird) 
    {
        this.fird = fird;
    }
}
