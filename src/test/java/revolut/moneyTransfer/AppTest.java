package revolut.moneyTransfer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    @org.junit.Test
	public void test(){
		Double f1 =  100.99d;
		Double f2 =  5.55d;
		Double f3 =  f1 -f2;
		System.out.println(">>>>>>>>>>>>>>> "+f3);
		
	}
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
