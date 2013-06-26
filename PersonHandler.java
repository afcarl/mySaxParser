package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class PersonHandler implements SaxHandler
{
    private List<PersonModel> people;
    private PersonModel currentPerson;
    private boolean insidePersonNode;
    private boolean insidePersonNameNode;
    private boolean insidePersonAgeNode;
    private boolean insidePersonGenderNode;
    private boolean insidePersonAddressNode;

    public void startDocument()
    {
        this.people = new LinkedList<PersonModel>();
        System.out.println("document start");
    }

    public void endDocument()
    {
        System.out.println("document end");

        for (PersonModel person : this.people)
        {
            System.out.println( person.toString() );
        }
    }

    public void characters(String textNode)
    {
        if (this.insidePersonAgeNode)
        {
            int age = Integer.parseInt(textNode);
            this.currentPerson.setAge(age);
            return;
        }

        if (this.insidePersonGenderNode)
        {
            this.currentPerson.setGender(textNode);
            return;
        }

        if (this.insidePersonNameNode)
        {
            this.currentPerson.addName(textNode);
            return;
        }

        if (this.insidePersonAddressNode)
        {
            this.currentPerson.setAddress(textNode);
            return;
        }
    }

    public void startElement(String tagName, Map<String, String> attributes)
    {
        if (tagName.equalsIgnoreCase("person"))
        {
            this.insidePersonNode = true;
            this.currentPerson = new PersonModel();
            this.people.add(this.currentPerson);
            return;
        }

        if (this.insidePersonNode)
        {
            if (tagName.equalsIgnoreCase("age"))
            {
                this.insidePersonAgeNode = true;
            }

            if (tagName.equalsIgnoreCase("name"))
            {
                this.insidePersonNameNode = true;
            }

            if (tagName.equalsIgnoreCase("gender"))
            {
                this.insidePersonGenderNode = true;
            }

            if (tagName.equalsIgnoreCase("address"))
            {
                this.insidePersonAddressNode = true;
            }
        }

    }

    public void endElement(String tagName)
    {
        if (tagName.equalsIgnoreCase("person"))
        {
            this.insidePersonNode = false;
            return;
        }

        if (this.insidePersonNode)
        {
            if (tagName.equalsIgnoreCase("age"))
            {
                this.insidePersonAgeNode = false;
            }

            if (tagName.equalsIgnoreCase("name"))
            {
                this.insidePersonNameNode = false;
            }

            if (tagName.equalsIgnoreCase("gender"))
            {
                this.insidePersonGenderNode = false;
            }

            if (tagName.equalsIgnoreCase("address"))
            {
                this.insidePersonAddressNode = false;
            }
        }

    }
}
