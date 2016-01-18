/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.db;

import com.bytes.thinkr.model.assignment.Answer;
import com.bytes.thinkr.model.assignment.Assignment;
import com.bytes.thinkr.model.assignment.Task;
import com.bytes.thinkr.model.assignment.Question;
import com.bytes.thinkr.model.entity.AssignmentEntityFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by Kent on 1/15/2016.
 */
public class AssignmentEntityTest extends EntityTest<Assignment>{

    @Test
    public void crud() {

        int entityCount = 100;
        String entityName = "Assignment";
        AssignmentEntityFactory factory = AssignmentEntityFactory.getInstance();

        AssignmentEntityTest entityTest = new AssignmentEntityTest();
        entityTest.create(factory, entityCount);

        List<Assignment> retrievedEntities = entityTest.retrieve(entityName, factory);
        Assert.assertThat(retrievedEntities.size(), is(entityCount));
    }

    /**
     * Hydration:
     * Assignment
     *  - Task
     *    - Questions
     *      - Answer
     * Commit order: Answers > Question > Task > Assignment
     *
     * @param entity The Assignment to commit
     */
    @Override
    protected void beforeEntitySave(Assignment entity) {

        Task task = entity.getTask();
        List<Question> questions = entity.getTask().getQuestions();
        List<Answer> answers = new ArrayList<>();
        for (Question q : questions) {
            answers.add(q.getAnswer());
        }

        HibernateUtil.commit(task);
        HibernateUtil.commit(answers);
        HibernateUtil.commit(questions);
    }
}
