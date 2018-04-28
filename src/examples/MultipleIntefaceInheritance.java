package examples;


public class MultipleIntefaceInheritance {

	public static final void main( String[] args ) {

		final Implementation object = new Implementation();
		object.print();
	}

	private interface A {

		@SuppressWarnings( "unused" )
		default public void print() {
			
			System.out.println( "Interface A" );
		}
	}

	private interface B extends A {

		@Override
		default public void print() {

			System.out.println( "Interface B" );
		}
	}

	@SuppressWarnings( "unused" )
	private interface BA {

		default public void print() {

			System.out.println( "Interface BA" );
		}
	}

	private static class Implementation implements B {
	}

	/*
	 * This type of implementation is not allowed by the compiler. He can tell that there is an ambiguous method
	 * private static class NotAllowedImplementation implements B, BA {
	 * }
	 */
}
