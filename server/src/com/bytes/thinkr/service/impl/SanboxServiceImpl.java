package com.bytes.thinkr.service.impl;

import com.bytes.thinkr.model.entity.assignment.Answer;
import com.bytes.thinkr.model.entity.assignment.Assignment;
import com.bytes.thinkr.model.entity.assignment.Point;
import com.bytes.thinkr.model.entity.assignment.Question;
import com.bytes.thinkr.model.factory.AssignmentEntityFactory;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Kent on 1/8/2016.
 */
public class SanboxServiceImpl {

    private static final Logger LOGGER = Logger.getLogger(SanboxServiceImpl.class.getName());

    public static Answer createAnswer(){
        Answer answer = new Answer();
        int p = (Math.random() > 0.5) ?1: 0;
        Point point = new Point(p);
        point.setMax(1);
        answer.setPoint(point);
        answer.addAnswer("This is answer 1");
        answer.addAnswer("This is answer 2");
        answer.addAnswer("This is answer 3");
        answer.addAnswer("This is answer 4");
        return answer;
    }

    public static Question createQuestion(){

        Question question = new Question();
        question.setAttempted(true);
        Question.Subject[] subjects = Question.Subject.values();
        question.setSubject(subjects[(int) (Math.random() * (subjects.length-1))]);
        question.setAnswer(createAnswer());
        return question;
    }

    public static Assignment createAssignment() {

        List<Assignment> assignments = AssignmentEntityFactory.getInstance().generate(1);
        return assignments.get(0);
    }
}
