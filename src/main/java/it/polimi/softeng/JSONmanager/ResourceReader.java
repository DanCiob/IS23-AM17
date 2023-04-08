package it.polimi.softeng.JSONmanager;

import java.io.InputStream;

public class ResourceReader {

    /**
     *
     * @param fileName is JSON file name
     * @return a stream from resource
     */
    public static InputStream getFileFromResourceAsStream(String fileName) {

        ClassLoader classLoader = ResourceReader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("Non-existent file" + fileName);
        } else {
            return inputStream;
        }

    }
}
