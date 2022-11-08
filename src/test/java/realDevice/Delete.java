package realDevice;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Delete {
	
	@Test
	public static void testmethod() {
		System.out.println("testmethod");
	}
	
	@Test
	public static void testmethod2() {
		System.out.println("testmethod2");
	}
	
	@BeforeSuite
	public static void suite() {
		System.out.println("suite");
	}
	
	@BeforeTest
	public static void test() {
		System.out.println("beforetest");
	}
	
	@BeforeClass
	public static void classs() {
		System.out.println("class");
	}
	
	@BeforeMethod
	public static void method() {
		System.out.println("method");
	}
	
	@BeforeMethod
	public static void method2() {
		System.out.println("method2");
	}
	

	

}
