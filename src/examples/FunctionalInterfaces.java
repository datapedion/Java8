package examples;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Any interface with a single abstract method is a functional interface.
 *
 */
public class FunctionalInterfaces {

	@FunctionalInterface
	public interface Functional {

		public String appendText( final String text, final String appendage );
	}

	private static int supplierMemory = 0;

	public static void main( final String[] args ) {

		final String[] initialValues = { "Hello", "World", "!" };
		final List<String> words = Arrays.asList( initialValues );

		final String sentence = words.stream().map( w -> applyFunction( w, s -> s.toUpperCase() ) ).collect( Collectors.joining( " " ) );
		System.out.println( sentence );

		words.stream().map( w -> applyMyFunctional( w, " ", ( s, t ) -> s + t ) ).forEach( System.out::print );
		System.out.println();

		final String alotHappened = words
				.stream()
				//.parallelStream()
				.collect( () -> initialValues[supplierMemory++],
				( t, u ) -> System.out.println( "stream( " + t + " )" + "-supplier( " + u + " )" ),
				// The combiner is only used when the stream is used in parallel
				( t, u ) -> System.out.println( "combiner( " + t + "<->" + u + ")" ) );

		System.out.println( alotHappened );

		words.replaceAll( s -> s.toUpperCase() );
		words.replaceAll( s -> s + " " );
		words.forEach( System.out::print );
		System.out.println();

	}

	public static String applyFunction( final String value, final Function<String, String> f ) {

		return f.apply( value );
	}

	public static String applyMyFunctional( final String value, final String appendage, final Functional f ) {

		return f.appendText( value, appendage );
	}

}
