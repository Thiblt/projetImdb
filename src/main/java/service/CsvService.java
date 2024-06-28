package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvService {
	
	 /**
     * Reads the content of a file and returns it as a List of strings.
     *
     * @param filePath the path to the file
     * @return a List of strings representing the file content
     */
    public static List<String> readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path);
    }

}
