/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.IEntity;
import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.factory.data.AccountDataFactory;
import com.bytes.thinkr.model.factory.data.DataFactory;
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
        T entity = getEntityFactory().findById(id);
        assertTrue(entity.getId() == id);
    }

    @Test
    public void testFindByIdList() throws Exception {
        Long[] idList = {1l, 2l, 3l, 4l, 5l};
        List<T> entities = getEntityFactory().findByIdList(idList);
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
        assertTrue(getEntityFactory().delete(entities.get(0)));
    }

    @Test
    public void testDeleteAll() throws Exception {
        // Need to perform commit before deleting
        assertTrue(getEntityFactory().deleteAll(entities));
    }

}