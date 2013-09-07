package com.btmatthews.mercury.sas.service.hdfs;

import com.btmatthews.mercury.sas.service.AttachmentService;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HDFSAttachmentService implements AttachmentService {

    private final FileSystem fileSystem;

    public HDFSAttachmentService(final String... configurationFiles) throws IOException {
        final Configuration configuration = new Configuration();
        for (final String configurationFile : configurationFiles) {
            configuration.addResource(new Path(configurationFile));
        }
        fileSystem = FileSystem.get(configuration);
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
    public void uploadAttachment(final String attachmentId, final InputStream inputStream) throws IOException {
        final Path path = new Path(attachmentId);
        if (!fileSystem.exists(path)) {
            final FSDataOutputStream outputStream = fileSystem.create(path);
            try {
                IOUtils.copy(inputStream, outputStream);
            } finally {
                outputStream.close();
            }
        }
    }

    @Override
    public void deleteAttachment(final String attachmentId) throws IOException {
        final Path path = new Path(attachmentId);
        if (fileSystem.exists(path)) {
            fileSystem.delete(path, true);
        }
    }
}
