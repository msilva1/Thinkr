package com.android.thinkr.data;

import com.bytes.hack.model.Answer;
import com.bytes.hack.model.Assignment;
import com.bytes.hack.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanclinton on 1/3/16.
 */

//public Assignment(String name, Assignment.Category category, List<Question> question, int duration)
public class AssignmentData {
    private static List<Assignment> assignmentList;

    public static List<Assignment> getAssignments(){
        List<Question> qs = new ArrayList<>();

        //build answer list
        List<String> answers = new ArrayList<>();
        answers.add("2");
        answers.add("4");
        answers.add("6");
        answers.add("8");
        answers.add("10");

        //build questions
        //question1
        Question q = new Question();
        q.setSubject(Question.Subject.Math);
        q.setQuestion("2+2");

        Answer a = new Answer();
        a.setAnswer(answers);
        a.setType(Answer.Type.Choice);

        q.setAnswer(a);
        qs.add(q);

        //question2
        q = new Question();
        q.setSubject(Question.Subject.Math);
        q.setQuestion("1+1");

        a = new Answer();
        a.setAnswer(answers);
        a.setType(Answer.Type.Choice);

        q.setAnswer(a);
        qs.add(q);


        //question3
        q = new Question();
        q.setSubject(Question.Subject.Math);
        q.setQuestion("5+5");

        a = new Answer();
        a.setAnswer(answers);
        a.setType(Answer.Type.Choice);

        q.setAnswer(a);
        qs.add(q);

        //question4
        q = new Question();
        q.setSubject(Question.Subject.Math);
        q.setQuestion("3+3");

        a = new Answer();
        a.setAnswer(answers);
        a.setType(Answer.Type.Choice);

        q.setAnswer(a);
        qs.add(q);

        //build assignmentList
        assignmentList = new ArrayList<Assignment>();

        Assignment assignment = new Assignment("Bobby's Homework", Assignment.Category.Homework,qs,100);

        assignmentList.add(assignment);

        return assignmentList;
    }
}
