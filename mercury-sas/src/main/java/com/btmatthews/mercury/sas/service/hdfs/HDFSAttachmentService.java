package com.btmatthews.mercury.sas.service.hdfs;

import com.btmatthews.mercury.sas.dao.AttachmentDAO;
import com.btmatthews.mercury.sas.dao.LogEntryDAO;
import com.btmatthews.mercury.sas.domain.Attachment;
import com.btmatthews.mercury.sas.domain.LogEntry;
import com.btmatthews.mercury.sas.domain.LogType;
import com.btmatthews.mercury.sas.service.AttachmentService;
import com.btmatthews.mercury.sas.service.IdentifierService;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HDFSAttachmentService implements AttachmentService {

    private final FileSystem fileSystem;
    private IdentifierService identifierService;
    private AttachmentDAO attachmentDao;
    private LogEntryDAO logEntryDao;

    public HDFSAttachmentService(final IdentifierService identifierService, final AttachmentDAO attachmentDao, final LogEntryDAO logEntryDao, final String... configurationFiles) throws IOException {
        this.identifierService = identifierService;
        this.attachmentDao = attachmentDao;
        this.logEntryDao = logEntryDao;
        final Configuration configuration = new Configuration();
        for (final String configurationFile : configurationFiles) {
            configuration.addResource(new Path(configurationFile));
        }
        this.fileSystem = FileSystem.get(configuration);
    }

    @Override
    public Attachment getAttachmentMetadata(final String attachmentId) {
        return attachmentDao.read(attachmentId);
    }

    @Override
    public void streamAttachment(final String attachmentId, final OutputStream outputStream) throws IOException {
        final Path path = new Path(attachmentId);
        if (fileSystem.exists(path)) {
            final FSDataInputStream inputStream = fileSystem.open(path);
            try {
                IOUtils.copyLarge(inputStream, outputStream);
            } finally {
                inputStream.close();
            }
        }
    }

    @Override
    public void streamAttachmentChunk(final String attachmentId, final long offset, final int size, final OutputStream outputStream) throws IOException {
        final Path path = new Path(attachmentId);
        if (fileSystem.exists(path)) {
            final byte[] buffer = new byte[size];
            final FSDataInputStream inputStream = fileSystem.open(path);
            try {
                final int actualSize = inputStream.read(offset, buffer, 0, size);
                if (actualSize > 0) {
                    outputStream.write(buffer, 0, actualSize);
                }
            } finally {
                inputStream.close();
            }
        }
    }

    @Override
    public Attachment storeAttachment(final String filename, final String contentType, final String contentEncoding, final InputStream inputStream) throws IOException {
        final String attachmentId = identifierService.generateId();
        final Path path = new Path(attachmentId);
        if (!fileSystem.exists(path)) {
            final FSDataOutputStream outputStream = fileSystem.create(path);
            try {
                IOUtils.copy(inputStream, outputStream);
            } finally {
                outputStream.close();
            }
        }
        final Attachment attachment = new Attachment(attachmentId, filename, contentType, contentEncoding, 0L, DateTime.now());
        attachmentDao.create(attachment);
        final String logEntryId = identifierService.generateId();
        final LogEntry logEntry = new LogEntry(logEntryId, LogType.CREATED_ATTACHMENT, null, attachment, DateTime.now());
        logEntryDao.create(logEntry);
        return attachment;
    }

    @Override
    public void deleteAttachment(final String attachmentId) throws IOException {
        final Path path = new Path(attachmentId);
        if (fileSystem.exists(path)) {
            fileSystem.delete(path, true);
        }
    }
}
