package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class SaxParser
{
    private String fullFilePath;
    private BufferedReader inputStream;
    private SaxHandler handler;

    public SaxParser(String fullFilePath, SaxHandler handler)
    {
        this.fullFilePath = fullFilePath;
        this.handler = handler;

        try
        {
            this.inputStream = new BufferedReader(new FileReader(fullFilePath));
        }
        catch (IOException x)
        {
            System.err.println(x);
        }
    }

    public void parse()
    {
        Stack tags = new Stack();
        StringBuilder tagName = new StringBuilder();
        StringBuilder attributes = new StringBuilder();
        StringBuilder textNode = new StringBuilder();
        boolean insideTag = false;
        boolean tagNameFetched = true;
        boolean closingTag = false;

        try
        {
            this.handler.startDocument();

            int code;
            while ( (code = this.inputStream.read()) != -1)
            {

                char character = (char) code;
                //System.out.println(character);

                if (character == '<')
                {
                    insideTag = true;
                    tagNameFetched = false;

                    //Check whether it was a text node. If so, raise event
                    if (textNode.length() > 0)
                    {
                        String textNodeContent = textNode.toString().trim();
                        if (textNodeContent.length() > 0)
                        {
                            //Callback characters event
                            this.handler.characters(textNodeContent);
                        }
                        textNode = new StringBuilder();
                    }
                    continue;
                }

                //Currently inside tag
                if (insideTag)
                {
                    //Closing tag: character '/' in buffer and empty tag name => '</' detected
                    if (character == '/' && tagName.length() == 0 && !tagNameFetched)
                    {
                        closingTag = true;
                        continue;
                    }

                    //End of tag name or append new character to tag name
                    if ( (character == ' ' || character == '>' || character == '/') && !tagNameFetched )
                    {
                        tagNameFetched = true;

                        if (closingTag)
                        {
                            String closingTagString = tagName.toString();
                            String openTagString = (String) tags.peek();

                            //Closing tag != Open tag => Error
                            if (!openTagString.equals(closingTagString))
                            {
                                System.err.println("Not valid xml");
                                return;
                            }
                        }
                        else
                        {
                            //Push new tag name on top of the stack
                            tags.push(tagName.toString());
                        }

                        tagName = new StringBuilder();
                    }

                    if (tagNameFetched)
                    {
                        if (closingTag)
                        {
                            if (character == '>')
                            {
                                insideTag = false;
                                closingTag = false;
                                String openTagString = (String) tags.pop();

                                //Callback elementEnd
                                this.handler.endElement(openTagString);
                            }
                        }
                        else
                        {
                            if (character == '>' || character == '/')
                            {
                                insideTag = (character == '/');
                                closingTag = (character == '/');
                                String tag = (String) tags.peek();
                                String attributesString = attributes.toString().trim();
                                Map<String, String> parsedAttributes = SaxParser.parseAttributes(attributesString);

                                //Callback elementStart with arguments
                                this.handler.startElement(tag, parsedAttributes);

                                attributes = new StringBuilder();
                            }
                            else
                            {
                                attributes.append(character);
                            }
                        }
                    }
                    else
                    {
                        tagName.append(character);
                    }

                }
                else
                {
                    textNode.append(character);
                }
            }

            this.handler.endDocument();
        }
        catch (IOException x)
        {
            System.err.println(x);
        }

    }

    public static void printHashMap(Map<String, String> map)
    {
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println( key + ": " + value);
        }
    }

    public static Map<String, String> parseAttributes(String attributesString)
    {
        Map<String, String> result = new HashMap<String, String>();

        if (attributesString.length() == 0)
        {
            return result;
        }

        //Split string by spaces
        String[] tokens = attributesString.split("\\s+");

        for (String token : tokens)
        {
            String[] pair = token.split("=");
            if (pair.length == 2)
            {
                //Remove quotes
                String valueWithoutQuotes = pair[1].substring(1, pair[1].length()-1);
                result.put(pair[0], valueWithoutQuotes);
            }
            else
            {
                result.put(pair[0], null);
            }
        }

        return result;
    }
}
