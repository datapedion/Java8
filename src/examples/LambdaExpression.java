package examples;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Test;

public class LambdaExpression {

	@Test
	public void interfaceDefaultImplementation() {

		System.out.println( ToUpperCase.toUpperCase( "hello world!" ) );
	}

	private interface ToUpperCase {

		public static String toUpperCase( final String value ) {

			return value.toUpperCase();
		}

	}

	/**
	 * The lambda expression is (probably) creating an anonymous implementation of the {@link Consumer Consumer
	 * interface} with the given lambda expression as
	 * the body of the {@link Consumer#accept(Object) Consumer accept method}.
	 */
	@Test
	public void expressionExamples() {

		final List<String> list = Arrays.asList( new String[] { "Hello", "World" } );

		System.out.println( "Anonymous method body" );
		// This is like defining an anonymous method body.
		list.forEach( ( String s ) -> {
			final String upperCase = s.toUpperCase();
			System.out.println( upperCase );
		} );

		System.out.println( "Single statement" );
		// This is just calling one method without taking any more actions.
		list.forEach( ( String s ) -> System.out.println( s ) );

		System.out.println( "Single statement abbreviated" );
		// The same as before, just a bit shorter
		list.forEach( s -> System.out.println( s ) );

		System.out.println( "Shortest lambda" );
		// Most concise way and does exactly the same as the previous example
		list.forEach( System.out::println );

		System.out.println( "Old school" );
		// And this is with a Consumer implementation
		list.forEach( new PrintUpperCase() );

		System.out.println( "Static method" );
		// Static method
		list.forEach( LambdaExpression::printUpperCase );

		System.out.println( "Instance method" );
		// Instance method
		final PrintUpperCase printer = new PrintUpperCase();
		list.forEach( printer::accept );

		System.out.println( "This instance method" );
		// Local instance method with this qualifier
		list.forEach( this::instancePrint );
	}

	private void instancePrint( final String text ) {

		System.out.println( text );
	}

	private static final void printUpperCase( String s ) {

		System.out.println( s.toUpperCase() );
	}

	private class PrintUpperCase implements Consumer<String> {

		@Override
		public void accept( String t ) {

			System.out.println( t.toUpperCase() );
		}
	}

}
