package com.btmatthews.mercury.sas.domain;

import org.joda.time.DateTime;

public final class LogEntry implements DatabaseEntity {

    private String id;
    private LogType type;
    private Recipient recipient;
    private Attachment attachment;
    private DateTime timestamp;

    public LogEntry(final String id, final LogType type, final Recipient recipient, final Attachment attachment,
                    final DateTime timestamp) {
        this.id = id;
        this.type = type;
        this.recipient = recipient;
        this.attachment = attachment;
        this.timestamp = timestamp;
    }

    @Override
    public String getId() {
        return id;
    }

    public LogType getType() {
        return type;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }
}
