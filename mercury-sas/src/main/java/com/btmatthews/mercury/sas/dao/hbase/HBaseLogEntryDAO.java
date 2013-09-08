package com.btmatthews.mercury.sas.dao.hbase;

import com.btmatthews.mercury.sas.dao.LogEntryDAO;
import com.btmatthews.mercury.sas.domain.Attachment;
import com.btmatthews.mercury.sas.domain.LogEntry;
import com.btmatthews.mercury.sas.domain.LogType;
import com.btmatthews.mercury.sas.domain.Recipient;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.joda.time.DateTime;

/**
 */
public final class HBaseLogEntryDAO extends AbstractHBaseDAO<LogEntry> implements LogEntryDAO {

    private static final byte[] TYPE_ATTR = "type".getBytes();
    private static final byte[] RECIPIENT_ID_ATTR = "recipientId".getBytes();
    private static final byte[] ATTACHMENT_ID_ATTR = "attachmentId".getBytes();
    private static final byte[] TIMESTAMP_ATTR = "timestamp".getBytes();

    private HBaseLogEntryDAO(final HTablePool tablePool) {
        super(tablePool, "log_entries");
    }

    @Override
    public void putObject(final Put put, final LogEntry logEntry) {
        put.add(CF, TYPE_ATTR, Bytes.toBytes(logEntry.getType().name()));
        if (logEntry.getRecipient() != null) {
            put.add(CF, RECIPIENT_ID_ATTR, Bytes.toBytes(logEntry.getRecipient().getId()));
        }
        if (logEntry.getAttachment() != null) {
            put.add(CF, ATTACHMENT_ID_ATTR, Bytes.toBytes(logEntry.getAttachment().getId()));
        }
        put.add(CF, TIMESTAMP_ATTR, Bytes.toBytes(logEntry.getTimestamp().getMillis()));
    }

    @Override
    public LogEntry getObject(final String id, final Result result) {
        final LogType type = LogType.valueOf(Bytes.toString(result.getValue(CF, TYPE_ATTR)));
        final Recipient recipient = null;
        final Attachment attachment = null;
        final DateTime timestamp = new DateTime(Bytes.toLong(result.getValue(CF, TIMESTAMP_ATTR)));
        return new LogEntry(id, type, recipient, attachment, timestamp);
    }
}
