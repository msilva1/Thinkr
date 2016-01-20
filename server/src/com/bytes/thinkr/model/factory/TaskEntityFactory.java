/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.assignment.Question;
import com.bytes.thinkr.model.entity.assignment.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskEntityFactory extends EntityFactory<Task> {

    private static TaskEntityFactory instance;

    public static TaskEntityFactory getInstance() {
        if (instance == null) {
            instance = new TaskEntityFactory();
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
            questions.add(QuestionEntityFactory.getInstance().create(i));
        }
        task.setQuestions(questions);

        return task;
    }

    @Override
    public String getDisplayInfo(Task entity) {
        return entity.getName();
    }

    @Override
    public String getEntityName() {
        return Task.class.getSimpleName();
    }

    @Override
    protected boolean saveSubEntities(List<Task> entity) {
        return false;
    }
}
