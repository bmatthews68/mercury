package com.btmatthews.mercury.sas.service;

import com.btmatthews.mercury.sas.domain.Attachment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface AttachmentService {

    Attachment getAttachmentMetadata(String attachmentId);

    void streamAttachment(String attachmentId, OutputStream outputStream) throws IOException;

    void streamAttachmentChunk(String attachmentId, long offset, int size, OutputStream outputStream) throws IOException;

    Attachment storeAttachment(String filename, String contentType, String contentEncoding, InputStream inputStream) throws IOException;

    void deleteAttachment(String attachmentId) throws IOException;
}