package com.btmatthews.mercury.sas.service.hdfs;

import com.btmatthews.mercury.sas.dao.AttachmentDAO;
import com.btmatthews.mercury.sas.dao.LogEntryDAO;
import com.btmatthews.mercury.sas.domain.Attachment;
import com.btmatthews.mercury.sas.service.AttachmentService;
import com.btmatthews.mercury.sas.service.IdentifierService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TestHDFSAttachmentService {

    public AttachmentService attachmentService;
    @Mock
    private IdentifierService identifierService;
    @Mock
    private AttachmentDAO attachmentDao;
    @Mock
    private LogEntryDAO logEntryDao;
    @Mock
    private OutputStream outputStream;

    @Before
    public void setup() throws IOException {
        initMocks(this);
        attachmentService = new HDFSAttachmentService(identifierService, attachmentDao, logEntryDao, "/usr/local/hadoop-1.2.1/conf/core-site.xml");
    }

    @Test
    public void uploadAttachment() throws IOException {
        final InputStream inputStream = getClass().getResourceAsStream("data.zip");
        when(identifierService.generateId()).thenReturn("a22b02d0-17a4-11e3-8ffd-0800200c9a66");
        final Attachment attachment = attachmentService.storeAttachment("data.zip", "application/zip", "", inputStream);
        assertNotNull(attachment);
    }

    @Test
    public void streamAttachmentChunk() throws IOException {
        attachmentService.streamAttachmentChunk("a22b02d0-17a4-11e3-8ffd-0800200c9a66", 0L, 8192, outputStream);
    }
}