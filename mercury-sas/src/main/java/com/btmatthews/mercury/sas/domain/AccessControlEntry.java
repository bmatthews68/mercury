package com.btmatthews.mercury.sas.domain;

public final class AccessControlEntry implements DatabaseEntity {

    private final String id;
    private final String attachmentId;
    private final String recipientId;

    public AccessControlEntry(final String id, final String attachmentId, final String recipientId) {
        this.id = id;
        this.attachmentId = attachmentId;
        this.recipientId = recipientId;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public String getRecipientId() {
        return recipientId;
    }
}
