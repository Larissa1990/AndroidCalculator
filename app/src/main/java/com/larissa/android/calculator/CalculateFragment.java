package com.larissa.android.calculator;

import android.content.res.Configuration;
import android.icu.number.CompactNotation;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.larissa.android.calculator.databinding.FragmentCalculateBinding;

import java.util.ArrayList;
import java.util.List;

public class CalculateFragment extends Fragment implements View.OnClickListener, MenuProvider {
    private FragmentCalculateBinding binding;
    private int screenOrientation;
    private CalculatorViewModel viewmodel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel=new ViewModelProvider(requireActivity()).get(CalculatorViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding=FragmentCalculateBinding.inflate(inflater,container,false);
        getActivity().addMenuProvider(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        screenOrientation=getActivity().getResources().getConfiguration().orientation;
        if(screenOrientation== Configuration.ORIENTATION_LANDSCAPE){
            binding.btnSin.setOnClickListener(this);
            binding.btnCos.setOnClickListener(this);
            binding.btnTan.setOnClickListener(this);
            binding.btnLog.setOnClickListener(this);
            binding.btnLn.setOnClickListener(this);
            binding.btnPi.setOnClickListener(this);
            binding.btnE.setOnClickListener(this);
            binding.btnFactorial.setOnClickListener(this);
            binding.btnSqrt.setOnClickListener(this);
            binding.btnPower.setOnClickListener(this);
        }
        binding.btnAdd.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
        binding.btnClear.setOnClickListener(this);
        binding.btnDivide.setOnClickListener(this);
        binding.btnEight.setOnClickListener(this);
        binding.btnEqual.setOnClickListener(this);
        binding.btnFive.setOnClickListener(this);
        binding.btnFour.setOnClickListener(this);
        binding.btnLeftBracket.setOnClickListener(this);
        binding.btnMinus.setOnClickListener(this);
        binding.btnMultiply.setOnClickListener(this);
        binding.btnNine.setOnClickListener(this);
        binding.btnOne.setOnClickListener(this);
        binding.btnPoint.setOnClickListener(this);
        binding.btnZero.setOnClickListener(this);
        binding.btnTwo.setOnClickListener(this);
        binding.btnThree.setOnClickListener(this);
        binding.btnSix.setOnClickListener(this);
        binding.btnSeven.setOnClickListener(this);
        binding.btnRightBracket.setOnClickListener(this);
        updateAnswerView();
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

        if(id==R.id.btn_divide){
            binding.btnZero.setEnabled(false);
            viewmodel.screenText+="/";
        }
        else{
            if(!binding.btnZero.isEnabled()) {
                binding.btnZero.setEnabled(true);
            }

            if(id==R.id.btn_clear){
                viewmodel.screenText="";
            }
            else if(id==R.id.btn_back){
                viewmodel.screenText=viewmodel.screenText.substring(0,viewmodel.screenText.length()-1);
            }
            else if(id==R.id.btn_sin){
                viewmodel.screenText+="sin(";
            }
            else if(id==R.id.btn_cos){
                viewmodel.screenText+="cos(";
            }
            else if(id==R.id.btn_tan){
                viewmodel.screenText+="tan(";
            }
            else if(id==R.id.btn_log){
                viewmodel.screenText+="log(";
            }
            else if(id==R.id.btn_ln){
                viewmodel.screenText+="ln(";
            }
            else if(id==R.id.btn_sqrt){
                viewmodel.screenText+="sqrt(";
            }
            else if(id==R.id.btn_equal){
                try{
                    CalculatorParser calculator=new CalculatorParser(viewmodel.screenText);
                    double ans=calculator.calc();
                    String expression= viewmodel.screenText;
                    viewmodel.screenText=String.valueOf(ans);
                    viewmodel.addExpression(expression,String.valueOf(ans));
                }catch (Exception ex){
                    viewmodel.screenText=ex.toString();
                }
            }
            else{
                if(id==R.id.btn_pi){
                    char lastTerm=viewmodel.screenText.charAt(viewmodel.screenText.length()-1);
                    if(Character.isDigit(lastTerm)){
                        viewmodel.screenText+="*pi";
                    }else{
                        viewmodel.screenText+="pi";
                    }
                }else{
                    viewmodel.screenText+=((Button) view).getText().toString();
                }
            }
        }

        updateAnswerView();
    }

    private void updateAnswerView(){
        binding.txAnswer.setText(viewmodel.screenText);
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.activity_main,menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_history:
                navigateToHistory();
                return true;
            default:
                return true;
        }
    }

    private void navigateToHistory(){
        NavDirections action=CalculateFragmentDirections.showHistory();
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
        getActivity().removeMenuProvider(this);
    }
}
