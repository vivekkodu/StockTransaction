package com.sahaj.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for main method.
 */
public class AppTest 
    extends TestCase
{
    private static final String INPUT_FILE_NAME = "src\\test\\java\\com\\sahaj\\app\\SahajInput.xls";

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testApp()
    {
        App.main(new String[] {INPUT_FILE_NAME});
        assertTrue( true );
    }
}
