mySaxParser
===========

Simple API for XML (SAX), a programming interface for applications that need to parse XML documents. 
Lightweight alternative to XML Document Object Model (XMLDOM) parsers.

Example
===========

Define entity model class: 

```java
public class EntityModel
{
    private String entityProperty1;
    private String entityProperty2;
    
    public int getEntityProperty1()
    {
        return this.entityProperty1;
    }

    public void getEntityProperty2(int newProperty)
    {
        this.entityProperty1 = newProperty;
    }
    
    //...
}

```

