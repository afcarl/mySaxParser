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

Define sax parser handler class that implements `SaxHandler` interface.
You should implement `startDocument`, `endDocument`, `characters`, `startElement`, `endElement` methods:

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
    
    //...
    
}

```

And launch it:

```java
SaxHandler handler = new PersonHandler();

SaxParser test = new SaxParser(fullFilePath, handler);

test.parse();

```

input:

```xml
<main title="main" color="red">
    <person>
        <name>DAn</name>
        <age>23</age>
    </person>
    <age>89</age>

    <person>
        <name>Den</name>
        <age>20</age>
        <gender>Male</gender>
    </person>


    <person>
        <name>DAh</name>
        <age>25</age>
    </person>


    <person>
        <name>DAzzz</name>
        <name>Pakhomov</name>
        <age>21</age>
    </person>
</main>

```

output:

```
document start
document end
PersonModel{names= DAn, address='null', gender='null', age=23}
PersonModel{names= Den, address='null', gender='Male', age=20}
PersonModel{names= DAh, address='null', gender='null', age=25}
PersonModel{names= DAzzz Pakhomov, address='null', gender='null', age=21}
```
