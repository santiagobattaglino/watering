package com.santiago.battaglino.esp32.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utilities for IO operations.
 */
@SuppressWarnings("checkstyle:nowhitespacebefore")
public enum IOUtils {
    ;

    private static final int BUFFER_SIZE = 1024 * 4;

    /**
     * Copies all bytes from the given input stream to the given output stream.
     * Caller is responsible for closing the streams.
     *
     * @param in  the input stream.
     * @param out the output stream.
     * @return the count of bytes copied.
     * @throws IOException if there is any IO exception during read or write.
     */
    public static long copy(InputStream in, OutputStream out)
            throws IOException {
        final byte[] buf = new byte[BUFFER_SIZE];
        long count = 0;
        int n;
        while ((n = in.read(buf)) > -1) {
            out.write(buf, 0, n);
            count += n;
        }
        return count;
    }
}
