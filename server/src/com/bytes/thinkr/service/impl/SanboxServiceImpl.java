package com.bytes.thinkr.service.impl;

import com.bytes.thinkr.model.assignment.*;

import java.util.ArrayList;
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
        Assignment assignment = new Assignment();

        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            questions.add(createQuestion());
        }
        assignment.setQuestions(questions);

        int pointTotal = 0;
        int pointEarned = 0;
        for (Question q : questions) {
            pointTotal += q.getAnswer().getPoint().getMax();
            pointEarned += q.getAnswer().getPoint().getEarned();
        }

        assignment.setScore(new Score((double) pointEarned/(double) pointTotal));
        return assignment;
    }
}
