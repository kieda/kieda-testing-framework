/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.io.File;

/**
 *
 * @author kieda
 */
public class CorruptedFileError extends Error {
    /**
     * Creates a new instance of
     * <code>CorruptedFile</code> without detail message.
     */
    public CorruptedFileError() {
        super("Corrupt file!");
    }
    /**
     * Constructs an instance of
     * <code>CorruptedFile</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CorruptedFileError(File  path) {
        super(path.getName() + " has been corrupted!");
    }
}
