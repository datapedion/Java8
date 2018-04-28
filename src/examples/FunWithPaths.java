package examples;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class FunWithPaths {

	@Rule
	public TestName	nameOfTest	= new TestName();

	final Path srcFolder = Paths.get( "./src" );

	@Before
	public void printName() {

		System.out.println( "-----------------------------------" );
		System.out.println( nameOfTest.getMethodName() );
		System.out.println( "-----------------------------------" );
	}

	@Test
	public void pathBasics() {

		System.out.println( "Path as is: " + srcFolder );
		System.out.println( "To absolute Path: " + srcFolder.toAbsolutePath() );
		System.out.println( "To normalized absolute Path: " + srcFolder.normalize().toAbsolutePath() );
	}

	@Test
	public void joinPaths() {

		final Path child = Paths.get( "paths" );
		System.out.println( "Basic path: " + srcFolder.normalize().toAbsolutePath() );
		System.out.println( "Path resolve: " + srcFolder.resolve( child ).normalize().toAbsolutePath() );
	}

	@Test
	public void relativizePaths() {

		final Path child = Paths.get( "paths" );
		final Path userHome = Paths.get( System.getProperty( "user.home" ) );
		System.out.println( "Basic path: " + srcFolder.normalize().toAbsolutePath() );
		System.out.println( "Root component of basic path: " + srcFolder.getRoot() );
		System.out.println( "Child path: " + child.normalize().toAbsolutePath() );
		System.out.println( "Path relativized with child: " + srcFolder.relativize( child ) );
		System.out.println( "Home path: " + userHome.normalize().toAbsolutePath() );
		System.out.println( "Root component of userHome: " + userHome.getRoot() );
		try {

			System.out.println( "Path relativized with user home: " + srcFolder.relativize( userHome ) );
		} catch ( final IllegalArgumentException e ) {

			System.out.println( e.getMessage() );
		}
		System.out.println( "Absolute basic path relativized with user home: " + srcFolder.toAbsolutePath().relativize( userHome ) );
	}

}
