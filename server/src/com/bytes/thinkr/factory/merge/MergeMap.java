/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory.merge;

import java.util.WeakHashMap;

/**
 * Created by Kent on 1/23/2016.
 */
public class MergeMap<K extends IMergeField, V> extends WeakHashMap<K, V> {

    @Override
    public V put(K key, V value) {
        if (value == null) {
            return null;
        }
        return super.put(key, value);
    }
}
