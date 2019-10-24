package com.letv4545.ajay_mac.quizapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.letv4545.ajay_mac.quizapplication.R;
import com.letv4545.ajay_mac.quizapplication.database.QuizReport;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    Context context;
   ArrayList<QuizReport> arrayList;
    public MyAdapter(Context context,ArrayList<QuizReport> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        DetailsViewHolder dHolder;
        if (convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.quiz_report_list_items,null);
            /*TextView txtCustId=(TextView)view.findViewById(R.id.textbillCId);
            TextView txtCustName=(TextView)view.findViewById(R.id.textBillName);
            TextView txtCustEmail=(TextView)view.findViewById(R.id.textbillEmail);
            TextView txtCustGender=(TextView)view.findViewById(R.id.textBillGender);
            TextView txtCustBillDate=(TextView)view.findViewById(R.id.textBillDate);
            TextView txtCustBillUnits=(TextView)view.findViewById(R.id.textBillUnits);
            TextView txtCustBillAmount=(TextView)view.findViewById(R.id.textBillAmount);*/
            dHolder=new DetailsViewHolder(convertView);
            convertView.setTag(dHolder);
        }
        else
        {
            dHolder=(DetailsViewHolder)convertView.getTag();

        }
        QuizReport quizReport=arrayList.get(i);
        //NumberFormat format=NumberFormat.getCurrencyInstance();

        dHolder.txtQuizNo.setText("Q.No."+Integer.toString(arrayList.indexOf(quizReport)+1)+".");
        //dHolder.txtEmail.setText(quizReport.getUserEmail());
        dHolder.txtQuestion.setText(quizReport.getQuestion());
        dHolder.txtOption1.setText(quizReport.getOption1());
        dHolder.txtOption2.setText(quizReport.getOption2());
        dHolder.txtOption3.setText(quizReport.getOption3());
        dHolder.txtOption4.setText(quizReport.getOption4());
        dHolder.txtUserAnswer.setText(Integer.toString(quizReport.getUserAns()));
        //dHolder.txtCategory.setText(quizReport.getCategory());
        dHolder.txtCorrectAnswer.setText(Integer.toString(quizReport.getAnswerNo()));
        //dHolder.txtQuizNo.setText(Integer.toString(quizReport.getQuizNo()));
        int userAns=quizReport.getUserAns();
        int correctAns=quizReport.getAnswerNo();
        if(userAns==correctAns){
            dHolder.imgreportSign.setImageResource(R.drawable.correct_ans);
        }
        else{
            dHolder.imgreportSign.setImageResource(R.drawable.wrong_ans);
        }
        return convertView;
    }
    private class DetailsViewHolder
    {
        //TextView txtEmail;
        ImageView imgreportSign;
        TextView txtQuizNo;
        TextView txtQuestion;
        TextView txtOption1;
        TextView txtOption2;
        TextView txtOption3;
        TextView txtOption4;
        TextView txtUserAnswer;
        //TextView txtCategory;
        TextView txtCorrectAnswer;
        //TextView txtQuizNo;

        public DetailsViewHolder(View view)
        {
            //txtEmail=(TextView)view.findViewById(R.id.textUEmail);
            imgreportSign=(ImageView)view.findViewById(R.id.imageRepSign);
            txtQuizNo=(TextView)view.findViewById(R.id.textRQuesNo);
            txtQuestion=(TextView)view.findViewById(R.id.textRQuestion);
            txtOption1=(TextView)view.findViewById(R.id.textROption1);
            txtOption2=(TextView)view.findViewById(R.id.textROption2);
            txtOption3=(TextView)view.findViewById(R.id.textROption3);
            txtOption4=(TextView)view.findViewById(R.id.textROption4);
            txtUserAnswer=(TextView)view.findViewById(R.id.textUserAns);
            //txtCategory=(TextView)view.findViewById(R.id.textCategory);
            txtCorrectAnswer=(TextView)view.findViewById(R.id.textCorrectAns);
            //txtQuizNo=(TextView)view.findViewById(R.id.textQuizNom);
        }
    }
}
