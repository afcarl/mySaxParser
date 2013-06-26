package com.company;

import java.util.Map;

public interface SaxHandler
{
    void startDocument();

    void endDocument();

    void characters(String textNode);

    void startElement(String tagName, Map<String, String> attributes);

    void endElement(String tagName);
}
