package examples;

import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

public class StreamBinaryOperators {

	private static final Boolean[] booleans = { false, false, false, true, false, true };
	public static void main( final String[] args ) {

		final BinaryOperator<Boolean> or = new BooleanOrBinaryOperator();
		final Predicate<Boolean> boolFilter = new PredicateFilter();

		System.out.println( "Final reduced boolean value: " + Arrays.stream( booleans ).reduce( or::apply ).get() );
		System.out.println( "Final reduced boolean value (implicit operation): " + Arrays.stream( booleans ).reduce( ( a, b ) -> a || b ).get() );
		System.out.println( "First found by filter: " + Arrays.stream( booleans ).filter( boolFilter ).findFirst().get() );
		Arrays.stream( booleans ).filter( b -> b ).findFirst().get(); // Shorter way of writing it
	}

	private static class BooleanOrBinaryOperator implements BinaryOperator<Boolean> {

		@Override
		public Boolean apply( final Boolean t, final Boolean u ) {

			System.out.println( "Operating on " + t + " and " + u );
			return t || u;
		}
	}

	private static class PredicateFilter implements Predicate<Boolean> {

		/*
		 * We return the value as is, so only "true" values will pass the filter
		 */
		@Override
		public boolean test( final Boolean t ) {

			return t;
		}

	}

}
