package com.btmatthews.mercury.sas.domain;

public final class Recipient implements DatabaseEntity {

    private final String id;
    private final String email;
    private final byte[] password;

    public Recipient(final String id, final String email, final byte[] password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getPassword() {
        return password;
    }
}