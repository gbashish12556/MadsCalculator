package com.example.madscalculator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CalculatorUtils
{
    ArrayList<Character> operators;
    private float lastAns = (float) 0.0;
    public CalculatorUtils()
    {
        operators=new ArrayList<>();
        operators.add('*');
        operators.add('+');
        operators.add('/');
        operators.add('-');
    }
    
    
    public String findPostfix(String inputP)//this will return the postfix expression of given input
    {
        if(!validateInput(inputP))
            return "Invalid expression";
        String temp="";
        ArrayList<Character> al=new ArrayList<>();
        al.add('(');
        char ch;
        inputP=inputP+")";
        for(int i=0;i<inputP.length();i++)
        {
            ch=inputP.charAt(i);
           if(ch=='(')
               al.add('(');
           else if(operators.contains(ch))
           {
               al.add(ch);
               temp=temp+pop(al,ch);
           }    
           else if(ch==')')
               temp=temp+pop(al);
           else
           {
               while(ch!='(' && ch!=')' && !operators.contains(ch))
               {
                   temp=temp+ch;
                   i=i+1;
                   ch=inputP.charAt(i);
               }
               temp=temp+" ";
               i=i-1;
           }
           
        }
        return temp;
    }

    
    String pop(ArrayList<Character> al, char ch)// pop all the operators of higher precedence than ch and add them to
                                    //temp
    {
        String temp="";
        for (int i = al.size()-1;  al.get(i)!='(' && i>=0;   i--)
        {
            if(operators.indexOf(al.get(i))<operators.indexOf(ch))
            {
                temp=temp+al.get(i);
                al.remove(i);
            }    
        }
        return temp;
    }

    public boolean validateInput(String input) {
        if(input.split("[^\\d]").length == 0 || input.length() == 0)
            return false;

        Pattern pattern;
        Matcher matcher;

        //Check if first charter is operator
        String first = String.valueOf(input.charAt(0));
        pattern = Pattern.compile("[*-+^\\/]{1}");
        matcher = pattern.matcher(first);
        if (matcher.find())
            return false;

        pattern = pattern.compile("[^\\d|+*.\\/-]");
        matcher = pattern.matcher(input);
        if (matcher.find())
            return false;


        //to check if 2 consecutive operators are there...
        pattern = pattern.compile("[*-+\\/]{2}");
        matcher = pattern.matcher(input);
        Boolean matchFound = matcher.find();
        Log.d("matchFound",matchFound.toString());
        if (matchFound)
            return false;

        return true;
    }
    

    public Boolean validateLastCharacter(String string){
        if(string.equals("")){
            return true;
        }else {
            String last = String.valueOf(string.charAt(string.length() - 1));
            Pattern pattern = Pattern.compile("[*-+^\\/]{1}");
            Matcher matcher = pattern.matcher(last);
            if (matcher.find())
                return true;
            return false;
        }
    }
    String pop(ArrayList<Character> al)// pop all operators until '(' reached and add them to temp
    {
        String temp="";
        for (int i = al.size()-1;  al.get(i)!='(' && i>=0;   i--)
        {
                temp=temp+al.get(i);
                al.remove(i);
        }
        al.remove(al.size()-1);
        return temp;
    }

    
    public float evaluatePostfix(String inputP)//this will return the final result after evaluating postfix expression
    {
        Stack<Float> stack=new Stack<>();
        char ch;
        float inputA;
        float inputB;
        for (int i = 0; i < inputP.length(); i++)
        {
            ch=inputP.charAt(i);
            
            if(operators.contains(ch))
            {
                inputB=stack.pop();
                inputA=stack.pop();
                switch(ch)
                {
                    case '/' :
                        stack.push(inputA/inputB);
                        break;
                    case '*' :
                        stack.push(inputA*inputB);
                        break;
                    case '+' :
                        stack.push(inputA+inputB);
                        break;
                    case '-' :
                        stack.push(inputA-inputB);
                        break;
                }
            }
            
            
            else
            {
                String temp="";
                while(!operators.contains(ch) && i<inputP.length() && ch!=' ')
                {
                    temp=temp+ch;
                    i=i+1;
                    ch=inputP.charAt(i);
                }
                stack.push((Float.parseFloat(temp)));
            }
                
        }
        lastAns = stack.pop();
        return lastAns;
    }

    public float getLastStoredAns() {
        return lastAns;
    }
}
