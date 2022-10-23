package com.larissa.android.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.larissa.android.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String KEY_SCREEN="screen";
    private ActivityMainBinding mActivityMainBinding;
    private String screenText="";
    private int screenOrientation;
    private CalculatorParser calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mActivityMainBinding.getRoot());
        screenOrientation=getResources().getConfiguration().orientation;
        if(screenOrientation== Configuration.ORIENTATION_LANDSCAPE){
            mActivityMainBinding.btnSin.setOnClickListener(this);
        }

        mActivityMainBinding.btnAdd.setOnClickListener(this);
        mActivityMainBinding.btnBack.setOnClickListener(this);
        mActivityMainBinding.btnClear.setOnClickListener(this);
        mActivityMainBinding.btnDivide.setOnClickListener(this);
        mActivityMainBinding.btnEight.setOnClickListener(this);
        mActivityMainBinding.btnEqual.setOnClickListener(this);
        mActivityMainBinding.btnFive.setOnClickListener(this);
        mActivityMainBinding.btnFour.setOnClickListener(this);
        mActivityMainBinding.btnLeftBracket.setOnClickListener(this);
        mActivityMainBinding.btnMinus.setOnClickListener(this);
        mActivityMainBinding.btnMultiply.setOnClickListener(this);
        mActivityMainBinding.btnNine.setOnClickListener(this);
        mActivityMainBinding.btnOne.setOnClickListener(this);
        mActivityMainBinding.btnPoint.setOnClickListener(this);
        mActivityMainBinding.btnZero.setOnClickListener(this);
        mActivityMainBinding.btnTwo.setOnClickListener(this);
        mActivityMainBinding.btnThree.setOnClickListener(this);
        mActivityMainBinding.btnSix.setOnClickListener(this);
        mActivityMainBinding.btnSeven.setOnClickListener(this);
        mActivityMainBinding.btnRightBracket.setOnClickListener(this);

        if(savedInstanceState!=null){
            screenText=savedInstanceState.getString(KEY_SCREEN,"");
            mActivityMainBinding.txAnswer.setText(screenText);
        }
    }

    @Override
    public void onClick(View view){
        int id=view.getId();
        if(id==R.id.btn_divide){
            mActivityMainBinding.btnZero.setEnabled(false);
            screenText+="/";}
        else{
            if(!mActivityMainBinding.btnZero.isEnabled()) {
                mActivityMainBinding.btnZero.setEnabled(true);
            }

            if(id==R.id.btn_clear){
                screenText="";
            }
            else if(id==R.id.btn_back){
                screenText=screenText.substring(0,screenText.length()-1);
            }
            else if(id==R.id.btn_sin){
                screenText+="sin(";
            }
            else if(id==R.id.btn_equal){
//                String answer=Calculator.calculate(screenText);
//                screenText=answer;
                try{
                    calculator=new CalculatorParser(screenText);
                    double ans=calculator.calc();
                    screenText=String.valueOf(ans);
                }catch (Exception ex){
                    screenText=ex.toString();
                }
            }
            else{
                screenText+=((Button) view).getText().toString();
            }
        }

        updateAnswerView();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_SCREEN,screenText);
    }

    private void updateAnswerView(){
        mActivityMainBinding.txAnswer.setText(screenText);
    }

}