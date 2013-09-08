package com.btmatthews.mercury.sas.dao.hbase;

import com.btmatthews.mercury.sas.dao.DAO;
import com.btmatthews.mercury.sas.domain.DatabaseEntity;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 */
public abstract class AbstractHBaseDAO<T extends DatabaseEntity> implements DAO<T> {

    protected static final byte[] CF = "cf".getBytes();
    private final HTablePool tablePool;
    private final String tableName;

    protected AbstractHBaseDAO(final HTablePool tablePool, final String tableName) {
        this.tablePool = tablePool;
        this.tableName = tableName;
    }

    @Override
    public int count() {
        try {
            final HTableInterface table = tablePool.getTable(tableName);
            try {
                return 0;
            } finally {
                table.close();
            }
        } catch (final IOException e) {
            return 0;
        }
    }

    @Override
    public final List<T> list(final int offset, final int size) {
        try {
            final HTableInterface table = tablePool.getTable(tableName);
            try {
                return new ArrayList<T>();
            } finally {
                table.close();
            }
        } catch (final IOException e) {
            return new ArrayList<T>();
        }
    }

    @Override
    public final void create(final T obj) {
        try {
            final HTableInterface table = tablePool.getTable(tableName);
            try {
                final Put put = new Put(Bytes.toBytes(obj.getId()));
                putObject(put, obj);
                table.put(put);
            } finally {
                table.close();
            }
        } catch (final IOException e) {
        }
    }

    @Override
    public final T read(final String id) {
        try {
            final HTableInterface table = tablePool.getTable(tableName);
            try {
                final Get get = new Get(Bytes.toBytes(id));
                final Result result = table.get(get);
                return getObject(id, result);
            } finally {
                table.close();
            }
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public final void update(final T obj) {
        try {
            final HTableInterface table = tablePool.getTable(tableName);
            try {
                final Put put = new Put(Bytes.toBytes(obj.getId()));
                putObject(put, obj);
                table.put(put);
            } finally {
                table.close();
            }
        } catch (final IOException e) {
        }
    }

    @Override
    public final void destroy(final String id) {
        try {
            final HTableInterface table = tablePool.getTable(tableName);
            try {
                final Delete delete = new Delete(Bytes.toBytes(id));
                table.delete(delete);
            } finally {
                table.close();
            }
        } catch (final IOException e) {
        }
    }

    protected abstract void putObject(Put put, T obj);

    protected abstract T getObject(String id, Result result);
}
