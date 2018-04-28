package examples;

import java.util.function.Function;

import org.junit.Test;


public class HigherOrderFunctions {

	private final Function<Integer, Integer>	increment	= i -> ++i;						// or i + 1
	private final Function<Integer, String>		intToString	= i -> String.valueOf( i );
	private final Function<Integer, String>		composed	= increment.andThen( intToString );

	@Test
	public void higherOrderFunctions() {

		System.out.println( composed.apply( 10 ) );

	}


}
