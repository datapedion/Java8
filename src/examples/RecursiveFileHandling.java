package examples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RecursiveFileHandling {
public static void main( String[] args ) throws IOException {
		
		String path = "test";
		Files.find(Paths.get(path),
		           Integer.MAX_VALUE,
		           (filePath, fileAttr) -> fileAttr.isRegularFile())
		        .forEach(System.out::println);

	}
}
