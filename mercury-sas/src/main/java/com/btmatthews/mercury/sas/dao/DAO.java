package com.btmatthews.mercury.sas.dao;

import com.btmatthews.mercury.sas.domain.Attachment;

import java.util.List;

/**
 *
 */
public interface DAO<T> {

    int count();

    List<T> list(int offset, int size);

    void create(T obj);

    T read(String id);

    void update(T obj);

    void destroy(String id);
}
