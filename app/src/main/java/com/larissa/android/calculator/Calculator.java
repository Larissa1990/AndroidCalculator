package com.larissa.android.calculator;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class Calculator {
    public static Double factorial(Double n){
        int num=n.intValue();
        int ans=1;
        while(num!=1){
            ans*=num;
            num-=1;
        }
        return Double.valueOf(ans);
    }



    public static String calculate(String expression){
        String _expression=expression.replace('X','*');
        List<String>tokens=postfixExpression(_expression);
        return postfixCalculate(tokens);
    }

    private static List<String>postfixExpression(String expression){
        List<String>nums=new ArrayList<>();
        Deque<String>symbols=new LinkedList<>();
        String param="";
        String symbol="";
        for(int i=0;i<expression.length();i++){
            char currentChar=expression.charAt(i);

            if(Character.isDigit(currentChar)){
                param+=String.valueOf(currentChar);
                if(i==expression.length()-1)
                    nums.add(param);
            }
            else if(currentChar=='.'){
                param+='.';
            }
            else if(currentChar=='*' || currentChar=='/'){
                nums.add(param);
                param="";
                symbols.addLast(String.valueOf(currentChar));
            }else if(currentChar=='+' || currentChar=='-'){
                nums.add(param);
                param="";
                if(symbols.size()!=0){
                    symbol=symbols.pollLast();
                    if(symbol.equals('*') || symbol.equals("/")){
                        nums.add(symbol);
                        while(symbols.size()!=0){
                            symbol=symbols.pollLast();
                            nums.add(symbol);
                        }
                    }else{
                        symbols.addLast(symbol);
                    }
                }
                symbols.addLast(String.valueOf(currentChar));
            }else if(currentChar=='('){
                nums.add(String.valueOf(currentChar));
                symbols.addLast(String.valueOf(currentChar));
            }else if(currentChar==')'){
                nums.add(param);
                param="";
                symbol=symbols.pollLast();
                while(!symbol.equals("(")){
                    nums.add(symbol);
                    symbol=symbols.pollLast();
                }
            }
        }
        while(symbols.size()!=0){
            symbol=symbols.pollLast();
            nums.add(symbol);
        }
        return nums;
    }

    private static String postfixCalculate(List<String>tokens){
        try{
            Deque<Double>nums=new LinkedList<>();
            for(String i:tokens){
                if(i.equals("+") || i.equals("-") || i.equals("*") || i.equals("/")){
                    Double p1=nums.pollLast();
                    Double p2=nums.pollLast();
                    switch (i){
                        case "+":
                            nums.addLast(p1+p2);
                            break;
                        case "-":
                            nums.addLast(p2-p1);
                            break;
                        case "*":
                            nums.addLast(p1*p2);
                            break;
                        case "/":
                            nums.addLast(p2/p1);
                            break;
                    }
                }else{
                    nums.addLast(Double.valueOf(i));
                }
            }
            return String.valueOf(nums.pollLast());
        }catch (Exception ex){
            return "Invalid Expression";
        }

    }


}
