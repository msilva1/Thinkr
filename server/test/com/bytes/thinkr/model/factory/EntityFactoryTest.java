/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.FactoryResponse;
import com.bytes.thinkr.model.FactoryResponseList;
import com.bytes.thinkr.model.entity.IEntity;
import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.factory.data.AccountDataFactory;
import com.bytes.thinkr.model.factory.data.DataFactory;
import org.eclipse.jdt.internal.compiler.ast.AssertStatement;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by Kent on 1/19/2016.
 */
public abstract class EntityFactoryTest<T extends IEntity> {

    private List<T> entities;

    @Before
    public void init() {
        entities = getDataFactory().generate(getCount());
    }

    /**
     * Get the number of entities for list related queries
     * @return the number of entities
     */
    protected int getCount() {return 100;}

    /**
     * Get the <tt>DataFactory</tt> for the corresponding entity
     * @return the DataFactory
     */
    protected abstract DataFactory<T> getDataFactory();

    /**
     * Get the data access factory for the corresponding entity
     * @return the data access factory
     */
    protected abstract EntityFactory<T> getEntityFactory();

    @Test
    public void testFindById() throws Exception {
        Long id = 1l;
        FactoryResponse<T> response = getEntityFactory().findById(id);
        if (response.getEntity() != null) {
            assertTrue(response.getEntity().getId() == id);
        } else {
            System.out.print("Unable to find " + id);
        }
    }

    @Test
    public void testFindByIdList() throws Exception {
        Long[] idList = {1l, 2l, 3l, 4l, 5l};
        FactoryResponseList<T> response = getEntityFactory().findByIdList(idList);
        List<T> entities = response.getEntities();
        assertThat(entities.size(), is(idList.length));
    }

    @Test
    public void testSave() throws Exception {
        assertTrue(getEntityFactory().save(entities.get(0)));
    }

    @Test
    public void testSaveAll() throws Exception {
        assertTrue(getEntityFactory().saveAll(entities));
    }

    @Test
    public void testDelete() throws Exception {

        // Need to perform commit before deleting
        FactoryResponse<T> response = getEntityFactory().findById(201L);
        if (response.getEntity() != null) {
            assertTrue(getEntityFactory().delete(response.getEntity()));
        } else {
            System.out.print("No data to be deleted.");
        }
    }

    @Test
    public void testDeleteAll() throws Exception {

        // Need to perform commit before deleting
        FactoryResponseList<T> response = getEntityFactory().findAll();
        entities = response.getEntities();
        if (entities.size() != 0) {
            System.out.print("Deleting " + entities.size() + " rows");
            assertTrue(getEntityFactory().deleteAll(entities));
        } else {
            System.out.print("No data to be deleted.");
        }
    }

}