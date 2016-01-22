/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory.data;

import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.account.Client;
import com.bytes.thinkr.model.entity.assignment.Answer;
import com.bytes.thinkr.model.entity.assignment.Assignment;
import com.bytes.thinkr.model.entity.assignment.Question;
import com.bytes.thinkr.model.entity.assignment.Task;
import com.bytes.thinkr.model.util.HibernateUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by Kent on 1/15/2016.
 */
public class AssignmentDataFactoryTest extends DataFactoryTest<Assignment> {

    @Test
    public void crud() {

        int entityCount = 100;
        String entityName = "Assignment";
        AssignmentDataFactory factory = AssignmentDataFactory.getInstance();

        AssignmentDataFactoryTest entityTest = new AssignmentDataFactoryTest();
        entityTest.create(factory, entityCount);

        List<Assignment> retrievedEntities = entityTest.retrieve(entityName, factory);
        Assert.assertThat(retrievedEntities.size(), is(entityCount));
    }

    /**
     * Hydration:
     * Assignment
     *  - Accounts(teacher, parent, student)
     *    - Client
     *  - Task
     *    - Questions
     *      - Answer
     * Commit order: Answers > Question > {Task, Client > Accounts} > Assignment
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

        List<Client> clients = new ArrayList<>();
        List<Account> accounts = entity.getAccounts();
        for (Account a : accounts) {
            clients.add(a.getClient());
        }

        HibernateUtil.commit(answers);
        HibernateUtil.commit(questions);
        HibernateUtil.commit(clients);
        HibernateUtil.commit(accounts);
        HibernateUtil.commit(task);

    }
}
