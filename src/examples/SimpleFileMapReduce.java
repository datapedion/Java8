package examples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleFileMapReduce {

	public static void main( String[] args ) {

		Arrays.stream( args ).map( Paths::get )
				.collect( Collectors.toList() )
				.parallelStream()
				.map( path -> {

					final Map<String, Integer> countedWords = new HashMap<>();

					try ( BufferedReader reader = new BufferedReader( new FileReader( path.toFile() ) ) ) {

						reader.lines()
								.flatMap( s -> Stream.of( s.split( "\\s+" ) ) )
								.forEach( s -> SimpleFileMapReduce.addWord( countedWords, s, 1 ) );
					} catch ( Exception e ) {}

					return countedWords;
				} ).reduce( ( wordList1, wordList2 ) -> {

					final Map<String, Integer> countedWords = new HashMap<>();
					wordList1.forEach( ( s, i ) -> SimpleFileMapReduce.addWord( countedWords, s, i ) );
					wordList2.forEach( ( s, i ) -> SimpleFileMapReduce.addWord( countedWords, s, i ) );
					return countedWords;
				} )
				.ifPresent( map -> map.forEach( ( s, i ) -> System.out.println( s + "->" + i ) ) );
	}

	public static void addWord( final Map<String, Integer> countedWords, String word, int increment ) {

		countedWords.put( word, countedWords.getOrDefault( word, 0 ) + increment );
	}
}
