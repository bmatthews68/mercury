package com.btmatthews.mercury.sas.dao.hbase;

import com.btmatthews.mercury.sas.dao.RecipientDAO;
import com.btmatthews.mercury.sas.domain.Recipient;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 */
public final class HBaseRecipientDAO extends AbstractHBaseDAO<Recipient> implements RecipientDAO {

    private static final byte[] EMAIL_ATTR = "email".getBytes();
    private static final byte[] PASSWORD_ATTR = "password".getBytes();

    private HBaseRecipientDAO(final HTablePool tablePool) {
        super(tablePool, "recipients");
    }

    @Override
    public void putObject(final Put put, final Recipient recipient) {
        put.add(CF, EMAIL_ATTR, Bytes.toBytes(recipient.getEmail()));
        put.add(CF, PASSWORD_ATTR, recipient.getPassword());
    }

    @Override
    public Recipient getObject(final String id, final Result result) {
        final String email = Bytes.toString(result.getValue(CF, EMAIL_ATTR));
        final byte[] password = result.getValue(CF, PASSWORD_ATTR);
        return new Recipient(id, email, password);
    }
}
