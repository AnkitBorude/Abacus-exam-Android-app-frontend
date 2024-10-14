package com.example.abacusapplication.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abacusapplication.R;
import com.example.abacusapplication.models.Question;

import java.util.Arrays;
import java.util.List;

public class McqAdapter extends RecyclerView.Adapter<McqAdapter.QuestionViewHolder> {

    private Context context;
    private List<Question> questionList;
    private int[] selectedAnswers;
    public McqAdapter(Context context, List<Question> questionList) {
        this.context = context;
        this.questionList = questionList;
        this.selectedAnswers = new int[questionList.size()];
        Arrays.fill(selectedAnswers, -1);
    }
    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.mcq_layout, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {

        Question question = questionList.get(position);
        holder.questionText.setText("Q. "+(position+1)+" "+question.getQuestion()+" = ?");
        holder.marksText.setText(" "+question.getMarks()+" Marks");
       //        // Bind options

        holder.optionA.setText(String.valueOf(question.getOptionA()));
        holder.optionB.setText(String.valueOf(question.getOptionB()));
        holder.optionC.setText(String.valueOf(question.getOptionC()));
        holder.optionD.setText(String.valueOf(question.getOptionD()));

        holder.optionsGroup.setOnCheckedChangeListener(null);

        holder.optionsGroup.clearCheck();

        if (selectedAnswers[position] != -1) {
            if (selectedAnswers[position]==question.getOptionA()) {
                holder.optionA.setChecked(true);
            } else if (selectedAnswers[position]==question.getOptionB()) {
                holder.optionB.setChecked(true);
            } else if (selectedAnswers[position]==question.getOptionC()) {
                holder.optionC.setChecked(true);
            } else if (selectedAnswers[position]==question.getOptionD()) {
                holder.optionD.setChecked(true);
            }
        }

        holder.optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == holder.optionA.getId()) {
                selectedAnswers[position] = question.getOptionA();
                Log.d("Selected", "A");
            } else if (checkedId == holder.optionB.getId()) {
                selectedAnswers[position] = question.getOptionB();
                Log.d("Selected", "B");
            } else if (checkedId == holder.optionC.getId()) {
                selectedAnswers[position] = question.getOptionC();
                Log.d("Selected", "C");
            } else if (checkedId == holder.optionD.getId()) {
                selectedAnswers[position] = question.getOptionD();
                Log.d("Selected", "D");
            }
        });
    }
    public int[] getSelectedAnswers() {
        return selectedAnswers;
    }
    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionText,marksText;
        RadioGroup optionsGroup;
        RadioButton optionA, optionB, optionC, optionD;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.question_text);
            optionsGroup = itemView.findViewById(R.id.options_group);
            marksText=itemView.findViewById(R.id.marks_per_question);
            optionA = itemView.findViewById(R.id.option_a);
            optionB = itemView.findViewById(R.id.option_b);
            optionC = itemView.findViewById(R.id.option_c);
            optionD = itemView.findViewById(R.id.option_d);
        }
    }
}
