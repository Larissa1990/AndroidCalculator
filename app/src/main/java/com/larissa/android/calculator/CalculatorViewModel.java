package com.larissa.android.calculator;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CalculatorViewModel extends ViewModel {
    private List<String> expressions;
    private List<String> answers;
    public String screenText="";

    public CalculatorViewModel(){
        expressions=new ArrayList<>();
        answers=new ArrayList<>();
    }

    public void addExpression(String expression, String answer){

        expressions.add(expression);
        answers.add(answer);
    }

    public List<String> getHistory(){
        return expressions;
    }

    public List<String>getAnswers(){
        return answers;
    }

}
