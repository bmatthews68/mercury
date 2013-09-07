package com.btmatthews.mercury.sas.service.hdfs;

import com.btmatthews.mercury.sas.service.AttachmentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.MockitoAnnotations.initMocks;

public class TestHDFSAttachmentService {

    @Mock
    private OutputStream outputStream;

    public AttachmentService attachmentService;

    @Before
    public void setup() throws IOException {
        initMocks(this);
        attachmentService = new HDFSAttachmentService();
    }

    @Test
    public void streamAttachmentChunk() throws IOException {
        attachmentService.streamAttachmentChunk("a22b02d0-17a4-11e3-8ffd-0800200c9a66", 0L, 8192, outputStream);
    }
}