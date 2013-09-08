package com.btmatthews.mercury.sas.domain;

import org.joda.time.DateTime;

public final class Attachment implements DatabaseEntity {

    private final String id;
    private final String filename;
    private final String contentType;
    private final String contentEncoding;
    private final long size;
    private final DateTime timestamp;

    public Attachment(final String id, final String filename, final String contentType, final String contentEncoding, final long size, final DateTime timestamp) {
        this.id = id;
        this.filename = filename;
        this.contentType = contentType;
        this.contentEncoding = contentEncoding;
        this.size = size;
        this.timestamp = timestamp;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getContentType() {
        return contentType;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public long getSize() {
        return size;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }
}
