package com.company;


import java.util.LinkedList;
import java.util.List;

public class PersonModel
{
    private List<String> names;
    private String address;
    private String gender;
    private int age;

    @Override
    public String toString()
    {
        StringBuilder allNames = new StringBuilder();

        for (String name : this.names)
        {
            allNames.append(" " + name);
        }

        return "PersonModel{" +
                "names=" + allNames.toString() +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }

    public PersonModel()
    {
        this.names = new LinkedList<String>();
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public List<String> getNames()
    {
        return names;
    }

    public void setNames(List<String> names)
    {
        this.names = names;
    }

    public void addName(String name)
    {
        this.names.add(name);
    }
}
