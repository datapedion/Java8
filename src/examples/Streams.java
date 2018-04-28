package examples;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * <p>
 * For a complete explanation of streams see the package documentation of {@link java.util.stream}
 * </p>
 * <p>
 * Streams must nether be used twice. For every operation you have to use a new {@link Stream}. <br/>
 * There are (more or less) two types of methods for streams: intermediate and terminal methods. After a terminal method the stream cannot be used any more.
 * Streams are just a "view" not a copy of the list. For each element of the underlying list all operations of the filter chain will be evaluated individually
 * and only on call of a terminal function, i.e. they are evaluated lazily. So intermediate functions are not evaluated until a terminal function is called. A
 * stream source should not be modified but can be, see {@link Stream}. Also have a look on {@link Stream} to see which functions are intermediate or terminal.
 * </p>
 *
 */
public class Streams {

	private static final Integer[]	numbersArray	= new Integer[] { 1, 3, 2, 4, 5, 7, 6, 8 };
	private static final String[]	numbersAsStringsArray	= new String[] { "1", "3", "2", "4", "5", "7", "6", "8", "9" };
	private static final String[]			numbersToRemove			= new String[] { "3", "6", "9" };
	private static final String[]	stringsArray	= new String[] { "eins", "zwei", "drei", "vier" };

	private static final Predicate<Integer>	evenOnly		= i -> i % 2 == 0 ? true : false;

	@Test
	public void simpleStreams() {

		final List<Integer> numbers = Arrays.asList( numbersArray );
		System.out.println( "Sorting:" );
		numbers.stream().sorted().forEach( ( final Integer i ) -> {
			System.out.print( i );
			System.out.print( " " );
		} );
		System.out.println();
		System.out.println( "Sum of numbers: " + numbers.stream().reduce( Integer::sum ).get() );

		System.out.println( "Sum of even numbers: " + numbers.stream().filter( evenOnly ).reduce( Integer::sum ).get() );

		// Example for the "fluid programming" paradigm.
		System.out.println( "Sum of even numbers lower than 5: " + numbers.stream()
		.filter( i -> i < 5 ? true : false )
		.filter( evenOnly )
		.reduce( Integer::sum ).get() );

		final List<String> strings = Arrays.asList( stringsArray );
		System.out.println( "Concatenating: " + strings.stream().reduce( String::concat ).get() );
		System.out.print( "Only those with \"r\" : " );
		strings.stream().filter( s -> s.contains( "r" ) ? true : false ).forEach( s -> System.out.print( s + " " ) );
	}

	/**
	 * This method is an example that nothing happens until a terminal method is called. Especially interesting is the output:
	 * <table border="1">
	 * <tbody>
	 * <tr>
	 * <td>1</td>
	 * <td>2</td>
	 * <td>3</td>
	 * <td>4</td>
	 * <td>5</td>
	 * <td>6</td>
	 * <td>7</td>
	 * <td>8</td>
	 * <td>9</td>
	 * </tr>
	 * <tr>
	 * <td></td>
	 * <td>2</td>
	 * <td></td>
	 * <td>4</td>
	 * <td></td>
	 * <td>6</td>
	 * <td></td>
	 * <td>8</td>
	 * <td></td>
	 * <tr>
	 * <tr>
	 * <td></td>
	 * <td></td>
	 * <td></td>
	 * <td></td>
	 * <td></td>
	 * <td>6</td>
	 * <td></td>
	 * <td>8</td>
	 * <td></td>
	 * <tr>
	 * </tbody>
	 * </table>
	 *
	 * For each value in a stream all intermediate functions and the terminal functions are applied to that value before the next value is processed. So if you
	 * look at the table the processing is done top to down first and afterwards left to right. So the output will look like 1 2 2 3 4 4 5 6 6 6 .... This shows
	 * the application of the functions.
	 */
	@Test
	public void nothingHappensUntil() {

		// This will not trigger any computations, because there is no terminal function used
		final Stream<Integer> workloadDefined = Stream.of( numbersAsStringsArray )
				.map( s -> Integer.parseInt( s ) )
				.sorted()
				.peek( ( final Integer i ) -> System.out.println( i ) )
				.filter( evenOnly )
				.peek( i -> System.out.println( i ) )
				.filter( i -> i > 5 ? true : false )
				.peek( i -> System.out.println( i ) );

		System.out.println( "Until this point nothing happend. Now we call the terminal function" );
		// toArray is one of the terminal functions
		final Integer[] processedData = workloadDefined.toArray( Integer[]::new );
		System.out.println( "Output of the processed data" );
		// forEach is another of the terminal functions
		Stream.of( processedData ).forEach( s -> System.out.println( s ) );
	}

	@Test
	public void streamFilePaths() throws IOException {

		final File workingDir = new File( System.getProperty( "user.dir" ) );
		System.out.println( "Absoluter Pfad: " + workingDir.getAbsolutePath() );

		System.out.println( "Directories only" );
		final Stream<Path> folders = Files.list( workingDir.toPath() ).filter( ( final Path p ) -> p.toFile().isDirectory() );
		folders.forEach( System.out::println );
		folders.close();

		System.out.println( "Files only" );
		final Stream<Path> files = Files.list( workingDir.toPath() ).filter( ( final Path p ) -> p.toFile().isFile() );
		files.forEach( System.out::println );
		files.close();
	}

	/**
	 * Think of map like changing the type of the stream and of flatmap like changing the complete Stream (throught combining multiple streams....)
	 */
	@Test
	public void streamFlatAndFlatMap() {

		final List<TestObject> testObjects = new ArrayList<>();
		final List<TestObject> valuesToRemove = new ArrayList<>();
		final List<List<TestObject>> testList = new ArrayList<>();
		testList.add( Arrays.asList( new TestObject( "2" ) ) );
		testList.add( Arrays.asList( new TestObject( "4" ) ) );
		testList.add( Arrays.asList( new TestObject( "8" ) ) );
		//Another way of createing the testList
		//List<List<TestObject>> testList = Stream.of( 2, 4, 8 ).map( i -> Arrays.asList( new TestObject( String.valueOf( i ) ) ) ).collect( Collectors.toList() );

		// More or less an identity function
		Stream.of( 2, 4, 8 )
		.map( i -> String.valueOf( i ) ) // Convert to Strings
		.map( s -> new TestObject( s ) ) // Convert to TestObjects
		.map( t -> Arrays.asList( t ) ) // Convert to Lists
		.collect( Collectors.toList() ).stream() // "Convert"/Collect all in one list
		.flatMap( list -> list.stream() ) // Convert all lists to streams and work on each stream
		.map( t -> t.getValue() ) // Convert to Strings
		.map( s -> Integer.valueOf( s ) ) // Convert to ints
		.forEach( System.out::println );

		for ( final String value : numbersAsStringsArray ) {

			testObjects.add( new TestObject( value ) );
		}

		for ( final String value : numbersToRemove ) {

			valuesToRemove.add( new TestObject( value ) );
		}

		final List<String> selectedValues = testObjects.stream()
				.map( t -> t.getValue() )
				.sorted()
				.collect( Collectors.toList() );

		for ( final String selectedValue : selectedValues ) {
			System.out.println( selectedValue );
		}

		System.out.println( "Removing values of given list from test list" );
		final List<String> removableValues = valuesToRemove.stream().map( t -> t.getValue() ).collect( Collectors.toList() );
		final List<TestObject> remainingValues = testObjects.stream()
				.filter( t -> !removableValues.contains( t.getValue() ) )
				.collect( Collectors.toList() );
		remainingValues.stream().forEach( System.out::println );

		System.out.println( "Keeping values of given list in test list" );
		final List<TestObject> keptValues = testObjects.stream()
				.filter( t -> testList.stream().flatMap( list -> list.stream() ).anyMatch( s -> t.getValue().equals( s.getValue() ) ) )
				.collect( Collectors.toList() );
		keptValues.stream().forEach( System.out::println );
	}

	@Test
	public void ofOfOf() {

		final String[] first = { "1", "2", "3" };
		final String[] second = { "4", "5", "6" };

		Stream.concat( Arrays.stream( first ), Arrays.stream( second ) ).forEach( s -> System.out.println( s ) );

	}

	public class TestObject {

		private final String value;

		public TestObject( final String value ) {

			this.value = value;
		}

		public String getValue() {

			return value;
		}

		@Override
		public String toString() {

			return "TestObject [value=" + value + "]";
		}

	}
}
