package com.company;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        System.out.print("Enter full path to your xml file: \n");

        Scanner user_input = new Scanner( System.in );

        String fullFilePath = user_input.next();

        SaxHandler handler = new PersonHandler();

        SaxParser test = new SaxParser(fullFilePath, handler);

        test.parse();
    }
}
