package com.example.qr_readerexample.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by lmonte on 02/05/16.
 */
public class FileUtil {

    private static final String BREAK_LINE = "\n";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public static methods.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public static String read(final File file) throws IOException {
        final StringBuilder builder = new StringBuilder();
        final FileReader fileReader = new FileReader(file);
        final Scanner scanner = new Scanner(fileReader);
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
            builder.append(BREAK_LINE);
        }
        return builder.toString();
    }

    public static byte[] getBytesFromFile(final File file) throws IOException {
        final long length = file.length();
        final byte[] bytes = new byte[(int) length];
        final InputStream is = new FileInputStream(file);
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        return bytes;
    }
}