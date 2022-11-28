package com.larissa.android.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private CalculatorViewModel viewmodel;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel=new ViewModelProvider(requireActivity()).get(CalculatorViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_history,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateUI();
    }

    private void updateUI(){
        HistoryItemAdapter adapter=new HistoryItemAdapter();
        recyclerView.setAdapter(adapter);
    }

    private class HistoryItemHolder extends RecyclerView.ViewHolder{
        private TextView expressionTextView;
        private TextView answerTextView;
        private String expression;

        public HistoryItemHolder(View itemView){
            super(itemView);
            expressionTextView=itemView.findViewById(R.id.tx_expression);
            answerTextView=itemView.findViewById(R.id.tx_answer);
            itemView.setOnClickListener(view -> {
                NavDirections action=HistoryFragmentDirections.showExpression();
                viewmodel.screenText=expression;
                Navigation.findNavController(view).navigate(action);
            });
        }

        public void bind(String question, String answer){
            expression=question;
            expressionTextView.setText(question);
            answerTextView.setText(answer);
        }
    }


    private class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemHolder>{
        private List<String>expressions;
        private List<String>answers;

        public HistoryItemAdapter(){
            expressions=viewmodel.getHistory();
            answers=viewmodel.getAnswers();
        }

        @NonNull
        @Override
        public HistoryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=getLayoutInflater().inflate(R.layout.history_item,parent,false);
            return new HistoryItemHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HistoryItemHolder holder, int position) {
            holder.bind(expressions.get(position),answers.get(position));
        }

        @Override
        public int getItemCount() {
            return expressions.size();
        }
    }

}
