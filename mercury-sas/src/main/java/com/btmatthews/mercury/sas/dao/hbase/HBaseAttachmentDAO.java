package com.btmatthews.mercury.sas.dao.hbase;

import com.btmatthews.mercury.sas.dao.AttachmentDAO;
import com.btmatthews.mercury.sas.domain.Attachment;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.joda.time.DateTime;

/**
 */
public class HBaseAttachmentDAO extends AbstractHBaseDAO<Attachment> implements AttachmentDAO {

    private static final byte[] FILENAME_ATTR = "filename".getBytes();
    private static final byte[] CONTENT_TYPE_ATTR = "contentType".getBytes();
    private static final byte[] CONTENT_ENCODING_ATTR = "contentEncoding".getBytes();
    private static final byte[] SIZE_ATTR = "size".getBytes();
    private static final byte[] TIMESTAMP_ATTR = "timestamp".getBytes();

    public HBaseAttachmentDAO(final HTablePool tablePool) {
        super(tablePool, "attachments");
    }

    @Override
    protected void putObject(final Put put, final Attachment attachment) {
        put.add(CF, FILENAME_ATTR, Bytes.toBytes(attachment.getFilename()));
        put.add(CF, CONTENT_TYPE_ATTR, Bytes.toBytes(attachment.getContentType()));
        put.add(CF, CONTENT_ENCODING_ATTR, Bytes.toBytes(attachment.getContentEncoding()));
        put.add(CF, SIZE_ATTR, Bytes.toBytes(attachment.getSize()));
        put.add(CF, TIMESTAMP_ATTR, Bytes.toBytes(attachment.getTimestamp().getMillis()));
    }

    @Override
    public Attachment getObject(final String id, final Result result) {
        final String filename = Bytes.toString(result.getValue(CF, FILENAME_ATTR));
        final String contentType = Bytes.toString(result.getValue(CF, CONTENT_TYPE_ATTR));
        final String contentEncoding = Bytes.toString(result.getValue(CF, CONTENT_ENCODING_ATTR));
        final long size = Bytes.toLong(result.getValue(CF, SIZE_ATTR));
        final DateTime timestamp = new DateTime(Bytes.toLong(result.getValue(CF, TIMESTAMP_ATTR)));
        return new Attachment(id, filename, contentType, contentEncoding, size, timestamp);
    }
}
