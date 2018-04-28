package examples;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;

public class CurryingSortOf {

	interface CurriedBiFunction<T, U, R> extends BiFunction<T, U, R> {

		default Function<U, R> curry( final T t ) {

			return u -> apply( t, u );
			// translates to
//			CurriedBiFunction<T, U, R> that = this;
//			return new Function<U, R>() {
//			 
//				@Override
//				public R apply( U u ) {
//			
//					return that.apply( t, u );
//				}
//			};
		}
	}

	class Currying implements CurriedBiFunction<Integer, Integer, Integer> {

		@Override
		public Integer apply( final Integer t, final Integer u ) {

			return t + u;
		}

	}

	class CurryingWithoutInterface {

		public Function<Integer, Integer> curry( final Integer t ) {

			return new Function<Integer, Integer>() {

				@Override
				public Integer apply( Integer u ) {

					return t + u;
				}
			};
		}

	}

	@Test
	public void testCurrying() {

		final Currying currying = new Currying();
		final Function<Integer, Integer> curriedFunction = currying.curry( 3 );

		final Integer curriedValueOne = curriedFunction.apply( 6 );
		System.out.println( "Curried value one: " + curriedValueOne );

		final Integer curriedValueTwo = curriedFunction.apply( 16 );
		System.out.println( "Curried value two: " + curriedValueTwo );
		
		final CurryingWithoutInterface currying2 = new CurryingWithoutInterface();
		final Function<Integer, Integer> curryFunction2 = currying2.curry( 4 );
		System.out.println( "Curried value without default implementation: " + curryFunction2.apply( 6 ) );
	}
}
