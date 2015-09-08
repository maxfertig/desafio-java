package test.java.org.pojoparse.util;

import java.util.ArrayList;
import java.util.List;


import junit.framework.TestCase;
import main.java.org.pojoparse.util.PojoParse;

public class PojoParseTest extends TestCase {
	
	public void testParsePojo() {
		PojoTest person = new PojoTest("Name One", "Orange Street", 104); 
		String expected = "address,name,addressNumber\r\n"
						+ "Orange Street,Name One,104";
		String actual = PojoParse.parseToCsv(person).toString();

		assertEquals(expected, actual);
	}
	
	public void testParsePojoList() {
		List<Object> persons = new ArrayList<Object>();
		persons.add(new PojoTest("Name One", "Orange Street", 104));
		persons.add(new PojoTest("Name Two", "Blue Street", 109));
		persons.add(new PojoTest("Name Three", "Red Street", 21));
		
		String expected = "address,name,addressNumber\r\n"
						+ "Orange Street,Name One,104\r\n"
						+ "Blue Street,Name Two,109\r\n"
						+ "Red Street,Name Three,21";
		String actual = PojoParse.parseToCsv(persons).toString();

		assertEquals(expected, actual);
	}
	
}
