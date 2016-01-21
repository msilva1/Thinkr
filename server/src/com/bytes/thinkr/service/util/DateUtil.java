/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.service.util;

import java.util.Date;

/**
 * Created by Kent on 1/20/2016.
 */
public class DateUtil {

    /**
     * Returns a future date.
     * This implementation is 20x faster than: c.add(Calendar.DAY_OF_MONTH, days);
     * @param days the number of days to add to the current date
     * @return the future date
     */
    public static Date calcFutureDate(int days) {
        return new Date(System.currentTimeMillis() + (86400000 * days));
    }
}
