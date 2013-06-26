mySaxParser
===========

Simple API for XML (SAX), a programming interface for applications that need to parse XML documents. 
Lightweight alternative to XML Document Object Model (XMLDOM) parsers.

Example
===========

Define entity model class: 

```java
public class PersonModel
{
    private List<String> names;
    private String address;
    private String gender;
    private int age;
    
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
    
    //...
    
}

```

Define sax parser handler class that implements SaxHandler interface :

```java

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
    
    /...
    
}

```


