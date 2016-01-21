/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory.data;

import com.bytes.thinkr.model.entity.assignment.Question;
import com.bytes.thinkr.model.entity.assignment.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDataFactory extends DataFactory<Task> {

    private static TaskDataFactory instance;

    public static TaskDataFactory getInstance() {
        if (instance == null) {
            instance = new TaskDataFactory();
        }
        return instance;
    }


    @Override
    protected Task create(int i) {

        Task task = new Task();
        task.setName("Auto generated assignment " + i);
        task.setDuration((int) (Math.random()*90));

        List<Question> questions = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            questions.add(QuestionDataFactory.getInstance().create(i));
        }
        task.setQuestions(questions);

        return task;
    }
}
