package com.btmatthews.mercury.sas.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface AttachmentService {

    void streamAttachment(String attachmentId, OutputStream outputStream) throws IOException;

    void streamAttachmentChunk(String attachmentId, long offset, int size, OutputStream outputStream) throws IOException;

    void uploadAttachment(String attachmentId, InputStream inputStream) throws IOException;

    void deleteAttachment(String attachmentId) throws IOException;
}