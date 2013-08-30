package org.jboss.modules;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * @author: Kabir Khan
 */
public class ChangeVersionToJDK6 {
    public static void main(String[] args) throws Exception {
        if (args.length == 0){
            throw new IllegalStateException("Must specify the directory");
        }

        File dir = new File(args[0]);
        if (!dir.exists()){
            throw new IllegalStateException("Does not exist: " + dir.getAbsolutePath());
        }
        if (!dir.isDirectory()){
            throw new IllegalStateException("Not a directory: " + dir.getAbsolutePath());
        }
        parseDirectory(dir);
    }

    private static void parseDirectory(File dir) throws Exception {
        File[] files = dir.listFiles();
        if (files == null){
            throw new IllegalStateException("Internal error listing files for " + dir.getAbsolutePath());
        }
        for (File file : files) {
            if (file.isDirectory()){
                parseDirectory(file);
            } else if (file.getName().endsWith(".class")){
                weaveFile(file);
            }
        }
    }

    private static void weaveFile(File file) throws Exception {
        RandomAccessFile raf = new RandomAccessFile(file.getAbsolutePath(), "rw");
        try {
            raf.seek(7);
            raf.writeByte((byte)50);
        } finally {
            StreamUtil.safeClose(raf);
        }
    }
}
