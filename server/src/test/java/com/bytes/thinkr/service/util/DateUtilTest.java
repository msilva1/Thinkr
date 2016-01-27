/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.service.util;

import org.junit.Test;

/**
 * Created by Kent on 1/20/2016.
 */
public class DateUtilTest {

    @Test
    public void testCalcFutureDate() throws Exception {
        for (int i = 0; i < 10000000; i++) {
            DateUtil.calcFutureDate(100);
        }
    }
}