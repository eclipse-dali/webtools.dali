/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public abstract class AbstractStringBuilderToolsTests
	extends TestCase
{
	public AbstractStringBuilderToolsTests(String name) {
		super(name);
	}

	// ********** char[] **********

	public void testConvertToCharArray() throws Exception {
		this.verifyConvertToCharArray("fred");
		this.verifyConvertToCharArray("f");
		this.verifyConvertToCharArray("");
	}

	protected abstract void verifyConvertToCharArray(String string) throws Exception;


	// ********** reverse **********

	public void testReverse() throws Exception {
		this.verifyReverse("derf", "fred");
		this.verifyReverse("wilma", "amliw");
		this.verifyReverse("f", "f");
		this.verifyReverse("", "");
	}

	protected abstract void verifyReverse(String expected, String string) throws Exception;

	// ********** padding/truncating/centering/repeating **********

	public void testCenter() throws Exception {
		this.verifyCenter("fred", "fred", 4);
		this.verifyCenter(" fred ", "fred", 6);
		this.verifyCenter(" fred  ", "fred", 7);
		this.verifyCenter("re", "fred", 2);
		this.verifyCenter("fre", "fred", 3);
		this.verifyCenter("", "fred", 0);
	}

	protected abstract void verifyCenter(String expected, String s, int len) throws Exception;

	public void testPad() throws Exception {
		this.verifyPad("fred", "fred", 4);
		this.verifyPad("fred  ", "fred", 6);

		boolean exCaught = false;
		try {
			this.verifyPad("BOGUS", "fred", 2);
			fail();
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	protected abstract void verifyPad(String expected, String string, int length) throws Exception;

	public void testFit() throws Exception {
		this.verifyFit("fred", "fred", 4);
		this.verifyFit("fred  ", "fred", 6);
		this.verifyFit("fr", "fred", 2);
		this.verifyFit("", "fred", 0);
	}

	protected abstract void verifyFit(String expected, String string, int length) throws Exception;

	public void testZeroPad() throws Exception {
		this.verifyZeroPad("1234", "1234", 4);
		this.verifyZeroPad("001234", "1234", 6);

		boolean exCaught = false;
		try {
			this.verifyZeroPad("BOGUS", "1234", 2);
			fail();
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	protected abstract void verifyZeroPad(String expected, String string, int length) throws Exception;

	public void testZeroFit() throws Exception {
		this.verifyZeroFit("1234", "1234", 4);
		this.verifyZeroFit("001234", "1234", 6);
		this.verifyZeroFit("34", "1234", 2);
		this.verifyZeroFit("", "1234", 0);
	}

	protected abstract void verifyZeroFit(String expected, String string, int length) throws Exception;

	public void testRepeat() throws Exception {
		this.verifyRepeat("", "1234", 0);
		this.verifyRepeat("12", "1234", 2);
		this.verifyRepeat("1234", "1234", 4);
		this.verifyRepeat("123412", "1234", 6);
		this.verifyRepeat("12341234", "1234", 8);
		this.verifyRepeat("123412341234123412341", "1234", 21);
	}

	protected abstract void verifyRepeat(String expected, String string, int length) throws Exception;

	public void testSeparate() throws Exception {
		this.verifySeparate("012345", "012345", '-', 22);
		this.verifySeparate("012345", "012345",  '-', 6);
		this.verifySeparate("01234-5", "012345",  '-', 5);
		this.verifySeparate("0123-45", "012345",  '-', 4);
		this.verifySeparate("012-345", "012345",  '-', 3);
		this.verifySeparate("01-23-45", "012345",  '-', 2);
		this.verifySeparate("0-1-2-3-4-5", "012345",  '-', 1);

		boolean exCaught = false;
		try {
			this.verifySeparate("012345", "012345", '-', 0);
			fail();
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	protected abstract void verifySeparate(String expected, String string, char separator, int segmentLength) throws Exception;

	public void testDelimit() throws Exception {
		this.verifyDelimit("123Employee123", "Employee", "123");
		this.verifyDelimit("123123123", "123", "123");
		this.verifyDelimit("123123", "", "123");
		this.verifyDelimit("", "", "");
		this.verifyDelimit("123", "123", "");
		this.verifyDelimit("x123x", "123", "x");
	}

	protected abstract void verifyDelimit(String expected, String string, String delimiter) throws Exception;

	public void testQuote() throws Exception {
		this.verifyQuote("\"Employee\"", "Employee");
		this.verifyQuote("\"123\"", "123");
		this.verifyQuote("\"\"", "");
		this.verifyQuote("\"Emp\"\"loyee\"", "Emp\"loyee");
	}

	protected abstract void verifyQuote(String expected, String string) throws Exception;

	public void testRemoveFirstOccurrence() throws Exception {
		this.verifyRemoveFirstOccurrence("Employee", "Emplo&yee", '&');
		this.verifyRemoveFirstOccurrence("Employee&", "Emplo&yee&", '&');
		this.verifyRemoveFirstOccurrence("Employee Foo", "Employee &Foo", '&');
		this.verifyRemoveFirstOccurrence("Employee", "Employee&", '&');
		this.verifyRemoveFirstOccurrence("Employee", "&Employee", '&');
		this.verifyRemoveFirstOccurrence("Employee", "Employee", '&');
	}

	protected abstract void verifyRemoveFirstOccurrence(String expected, String string, char charToRemove) throws Exception;

	public void testRemoveAllOccurrences() throws Exception {
		this.verifyRemoveAllOccurrences("EmployeeFred", "Employee Fred", ' ');
		this.verifyRemoveAllOccurrences("Employee", " Employee ", ' ');
		this.verifyRemoveAllOccurrences("EmployeeFoo", "Employee   Foo", ' ');
		this.verifyRemoveAllOccurrences("EmployeeFoo", " Emp loyee   Foo", ' ');
		this.verifyRemoveAllOccurrences(" Emp loyee   Foo", " Emp loyee   Foo", '&');
	}

	protected abstract void verifyRemoveAllOccurrences(String expected, String string, char charToRemove) throws Exception;

	public void testRemoveAllSpaces() throws Exception {
		this.verifyRemoveAllSpaces("EmployeeFred", "Employee Fred");
		this.verifyRemoveAllSpaces("Employee", " Employee ");
		this.verifyRemoveAllSpaces("EmployeeFoo", "Employee   Foo");
		this.verifyRemoveAllSpaces("EmployeeFoo", " Emp loyee   Foo");
		this.verifyRemoveAllSpaces("Employee", "Employee");
	}

	protected abstract void verifyRemoveAllSpaces(String expected, String string) throws Exception;

	public void testRemoveAllWhitespace() throws Exception {
		this.verifyRemoveAllWhitespace("EmployeeFred", "Employee Fred\t");
		this.verifyRemoveAllWhitespace("Employee", "\tEmployee\n");
		this.verifyRemoveAllWhitespace("EmployeeFoo", "Employee \t Foo");
		this.verifyRemoveAllWhitespace("EmployeeFoo", " Emp\tloyee \n Foo");
		this.verifyRemoveAllWhitespace("EmployeeFoo", "EmployeeFoo");
	}

	protected abstract void verifyRemoveAllWhitespace(String expected, String string) throws Exception;

	public void testCompressWhitespace() throws Exception {
		this.verifyCompressWhitespace("Employee Fred ", "Employee      Fred\t");
		this.verifyCompressWhitespace(" Employee ", "\tEmployee  \n");
		this.verifyCompressWhitespace("Employee Foo", "Employee \t Foo");
		this.verifyCompressWhitespace(" Emp loyee Foo ", " Emp\tloyee \n Foo ");
		this.verifyCompressWhitespace("EmployeeFoo", "EmployeeFoo");
	}

	protected abstract void verifyCompressWhitespace(String expected, String string) throws Exception;

	public void testCapitalize() throws Exception {
		this.verifyCapitalize("Oracle", "Oracle");
		this.verifyCapitalize("Oracle", "oracle");
		this.verifyCapitalize("   ", "   ");
		this.verifyCapitalize("ORACLE", "ORACLE");
		this.verifyCapitalize("", "");
		this.verifyCapitalize("A", "a");
		this.verifyCapitalize("\u00C9cole", "\u00E9cole"); // e'cole -> E'cole
	}

	protected abstract void verifyCapitalize(String expected, String string) throws Exception;

	public void testUncapitalize() throws Exception {
		this.verifyUncapitalize("oracle", "Oracle");
		this.verifyUncapitalize("oracle", "oracle");
		this.verifyUncapitalize("   ", "   ");
		this.verifyUncapitalize("ORACLE", "ORACLE");
		this.verifyUncapitalize("", "");
		this.verifyUncapitalize("a", "A");
		this.verifyUncapitalize("\u00E9cole", "\u00C9cole"); // E'cole -> e'cole
	}

	protected abstract void verifyUncapitalize(String expected, String string) throws Exception;

	public void testConvertToHexString() throws Exception {
		this.verifyConvertToHexString("74657374", "test"); // UTF-8 values
		this.verifyConvertToHexString("", "");
	}

	public void testConvertToHexString_negative() throws Exception {
		this.verifyConvertToHexString(this.getHexCafe(), "caf\u00E9"); // UTF-8 values
	}

	private String getHexCafe() {
		return AbstractStringToolsTests.getHexCafe();
	}

	protected abstract void verifyConvertToHexString(String expected, String string) throws Exception;

	public void testConvertCamelCaseToAllCaps() throws Exception {
		this.verifyConvertCamelCaseToAllCaps("TEST", "test");
		this.verifyConvertCamelCaseToAllCaps("TEST", "TEST");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST", "testTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST", "TestTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST_TEST", "testTESTTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST_TEST", "TestTESTTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST_TEST_T", "TestTESTTestT");
		this.verifyConvertCamelCaseToAllCaps("", "");
	}

	protected abstract void verifyConvertCamelCaseToAllCaps(String expected, String string) throws Exception;

	public void testConvertCamelCaseToAllCapsMaxLength() throws Exception {
		this.verifyConvertCamelCaseToAllCapsMaxLength("", "test", 0);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST", "test", 4);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST", "test", 8);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST", "test", 55);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST", "TEST", 4);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST", "TEST", 8);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST", "TEST", 55);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST", "testTest", 4);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_", "testTest", 5);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TES", "testTest", 8);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TEST", "testTest", 55);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST", "TestTest", 4);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TES", "TestTest", 8);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TEST", "TestTest", 55);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST", "testTESTTest", 4);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TES", "testTESTTest", 8);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TEST_TEST", "testTESTTest", 55);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST", "TestTESTTest", 4);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TES", "TestTESTTest", 8);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TEST_TEST", "TestTESTTest", 55);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST", "TestTESTTestT", 4);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TES", "TestTESTTestT", 8);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TEST_TEST_T", "TestTESTTestT", 55);
		this.verifyConvertCamelCaseToAllCapsMaxLength("", "", 4);
		this.verifyConvertCamelCaseToAllCapsMaxLength("", "", 8);
		this.verifyConvertCamelCaseToAllCapsMaxLength("", "", 55);
	}

	protected abstract void verifyConvertCamelCaseToAllCapsMaxLength(String expected, String string, int maxLength) throws Exception;

	// ********** convert all-caps to camel case **********

	public void testConvertAllCapsToCamelCase() throws Exception {
		this.verifyConvertAllCapsToCamelCase("", "");
		this.verifyConvertAllCapsToCamelCase("", "", true);
		this.verifyConvertAllCapsToCamelCase("", "", false);
		this.verifyConvertAllCapsToCamelCase("test", "TEST", false);
		this.verifyConvertAllCapsToCamelCase("test", "TEST_", false);
		this.verifyConvertAllCapsToCamelCase("test", "TEST____", false);
		this.verifyConvertAllCapsToCamelCase("Test", "TEST");
		this.verifyConvertAllCapsToCamelCase("Test", "TEST", true);
		this.verifyConvertAllCapsToCamelCase("test", "TeST", false);
		this.verifyConvertAllCapsToCamelCase("testTest", "TEST_TEST", false);
		this.verifyConvertAllCapsToCamelCase("testTest", "TEST___TEST", false);
		this.verifyConvertAllCapsToCamelCase("TestTest", "TEST_TEST");
		this.verifyConvertAllCapsToCamelCase("TestTest", "TEST_TEST", true);
		this.verifyConvertAllCapsToCamelCase("testTestTest", "TEST_TEST_TEST", false);
		this.verifyConvertAllCapsToCamelCase("TestTestTest", "TEST_TEST_TEST");
		this.verifyConvertAllCapsToCamelCase("TestTestTest", "TEST_TEST_TEST", true);
		this.verifyConvertAllCapsToCamelCase("testTestTestT", "TEST_TEST_TEST_T", false);
		this.verifyConvertAllCapsToCamelCase("testTestTestT", "_TEST_TEST_TEST_T", false);
		this.verifyConvertAllCapsToCamelCase("testTestTestT", "__TEST_TEST_TEST_T", false);
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "TEST_TEST_TEST_T");
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "TEST_TEST_TEST_T", true);
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "_TEST_TEST_TEST_T");
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "_TEST_TEST_TEST_T", true);
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "__TEST_TEST_TEST_T");
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "__TEST_TEST_TEST_T", true);
	}

	protected abstract void verifyConvertAllCapsToCamelCase(String expected, String string) throws Exception;

	protected abstract void verifyConvertAllCapsToCamelCase(String expected, String string, boolean capFirst) throws Exception;

	public void testConvertAllCapsToCamelCaseLowercase() throws Exception {
		this.verifyConvertAllCapsToCamelCase("test", "test", false);
		this.verifyConvertAllCapsToCamelCase("test", "test_", false);
		this.verifyConvertAllCapsToCamelCase("test", "test____", false);
		this.verifyConvertAllCapsToCamelCase("Test", "test", true);
		this.verifyConvertAllCapsToCamelCase("test", "test", false);
		this.verifyConvertAllCapsToCamelCase("testTest", "test_test", false);
		this.verifyConvertAllCapsToCamelCase("testTest", "test___test", false);
		this.verifyConvertAllCapsToCamelCase("TestTest", "test_test", true);
		this.verifyConvertAllCapsToCamelCase("testTestTest", "test_test_test", false);
		this.verifyConvertAllCapsToCamelCase("TestTestTest", "test_test_test", true);
		this.verifyConvertAllCapsToCamelCase("testTestTestT", "test_test_test_t", false);
		this.verifyConvertAllCapsToCamelCase("testTestTestT", "_test_test_test_t", false);
		this.verifyConvertAllCapsToCamelCase("testTestTestT", "__test_test_test_t", false);
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "test_test_test_t", true);
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "_test_test_test_t", true);
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "__test_test_test_t", true);
	}

	public void testUndelimit() throws Exception {
		this.verifyUndelimit("foo", "\"foo\"");
		this.verifyUndelimit("", "\"\"");
		this.verifyUndelimit("foo", "'foo'");
		this.verifyUndelimit("fo\"o", "\"fo\"\"o\"");
		this.verifyUndelimit("foo\"", "\"foo\"\"\"");
		this.verifyUndelimit("\"foo", "\"\"\"foo\"");
		this.verifyUndelimit("foo", "[foo]");
		this.verifyUndelimit("\"", "\"\"\"");
		this.verifyUndelimit("foo\"", "\"foo\"bar\"");
		this.verifyUndelimit("foo\"", "\"foo\"\"");

		boolean exCaught = false;
		try {
			this.verifyUndelimit("BOGUS", "x");
			fail();
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	protected abstract void verifyUndelimit(String expected, String s) throws Exception;

	public void testUndelimitCount() throws Exception {
		this.verifyUndelimitCount("o", "\"foo\"", 2);
		this.verifyUndelimitCount("", "\"\"\"\"", 2);
		this.verifyUndelimitCount("foo", "XXfooXX", 2);
		this.verifyUndelimitCount("\"foo\"", "\"foo\"", 0);

		boolean exCaught = false;
		try {
			this.verifyUndelimitCount("BOGUS", "XXXX", 5);
			fail();
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	protected abstract void verifyUndelimitCount(String expected, String s, int count) throws Exception;

	public void testConvertToJavaStringLiteral() throws Exception {
		this.verifyConvertToJavaStringLiteral("\"\"", "");
		this.verifyConvertToJavaStringLiteral("\"\\\"\\\"\"", "\"\"");
		this.verifyConvertToJavaStringLiteral("\"'foo'\"", "'foo'");
		this.verifyConvertToJavaStringLiteral("\"foo\\bbar\"", "foo\bbar");
		this.verifyConvertToJavaStringLiteral("\"foo\\n\\tbar\"", "foo\n\tbar");
		this.verifyConvertToJavaStringLiteral("\"foo\\\"bar\"", "foo\"bar");
		this.verifyConvertToJavaStringLiteral("\"foo\\\\bar\"", "foo\\bar");
		this.verifyConvertToJavaStringLiteral("\"\\ffoo\\f\"", "\ffoo\f");
		this.verifyConvertToJavaStringLiteral("\"\\rfoo\\r\"", "\rfoo\r");
	}

	protected abstract void verifyConvertToJavaStringLiteral(String expected, String s) throws Exception;

	public void testConvertToJavaStringLiteralContent() throws Exception {
		this.verifyConvertToJavaStringLiteralContent("", "");
		this.verifyConvertToJavaStringLiteralContent("\\\"\\\"", "\"\"");
		this.verifyConvertToJavaStringLiteralContent("'foo'", "'foo'");
		this.verifyConvertToJavaStringLiteralContent("foo\\bbar", "foo\bbar");
		this.verifyConvertToJavaStringLiteralContent("foo\\n\\tbar", "foo\n\tbar");
		this.verifyConvertToJavaStringLiteralContent("foo\\\"bar", "foo\"bar");
		this.verifyConvertToJavaStringLiteralContent("foo\\\\bar", "foo\\bar");
	}

	protected abstract void verifyConvertToJavaStringLiteralContent(String expected, String s) throws Exception;

	// ********** converting to XML **********

	public void testConvertToXmlAttributeValue() throws Exception {
		this.verifyConvertToXmlAttributeValue("\"\"", "");
		this.verifyConvertToXmlAttributeValue("'\"'", "\"");
		this.verifyConvertToXmlAttributeValue("'\"\"'", "\"\"");
		this.verifyConvertToXmlAttributeValue("\"'\"", "'");
		this.verifyConvertToXmlAttributeValue("\"''\"", "''");
		this.verifyConvertToXmlAttributeValue("\"&quot;'&quot;\"", "\"'\"");
		this.verifyConvertToXmlAttributeValue("\"&quot;''&quot;\"", "\"''\"");
		this.verifyConvertToXmlAttributeValue("\"'foo'\"", "'foo'");
		this.verifyConvertToXmlAttributeValue("'\"foo\"'", "\"foo\"");
		this.verifyConvertToXmlAttributeValue("\"&quot;foo&quot; 'bar'\"", "\"foo\" 'bar'");
		this.verifyConvertToXmlAttributeValue("\"foo &amp; bar\"", "foo & bar");
		this.verifyConvertToXmlAttributeValue("'\"foo &amp; bar\"'", "\"foo & bar\"");
		this.verifyConvertToXmlAttributeValue("\"foo &lt;&lt;&lt; bar\"", "foo <<< bar");
		this.verifyConvertToXmlAttributeValue("'\"foo &lt;&lt;&lt; bar\"'", "\"foo <<< bar\"");
	}

	protected abstract void verifyConvertToXmlAttributeValue(String expected, String string) throws Exception;

	public void testConvertToDoubleQuotedXmlAttributeValue() throws Exception {
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"\"", "");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;\"", "\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;&quot;\"", "\"\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"'\"", "'");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"''\"", "''");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;'&quot;\"", "\"'\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;''&quot;\"", "\"''\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"'foo'\"", "'foo'");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;foo&quot;\"", "\"foo\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;foo&quot; 'bar'\"", "\"foo\" 'bar'");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"foo &amp; bar\"", "foo & bar");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;foo &amp; bar&quot;\"", "\"foo & bar\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"foo &lt;&lt;&lt; bar\"", "foo <<< bar");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;foo &lt;&lt;&lt; bar&quot;\"", "\"foo <<< bar\"");
	}

	protected abstract void verifyConvertToDoubleQuotedXmlAttributeValue(String expected, String string) throws Exception;

	public void testConvertToDoubleQuotedXmlAttributeValueContent() throws Exception {
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("", "");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;", "\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;&quot;", "\"\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("'", "'");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("''", "''");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;'&quot;", "\"'\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;''&quot;", "\"''\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("'foo'", "'foo'");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;foo&quot;", "\"foo\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;foo&quot; 'bar'", "\"foo\" 'bar'");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("foo &amp; bar", "foo & bar");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;foo &amp; bar&quot;", "\"foo & bar\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("foo &lt;&lt;&lt; bar", "foo <<< bar");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;foo &lt;&lt;&lt; bar&quot;", "\"foo <<< bar\"");
	}

	protected abstract void verifyConvertToDoubleQuotedXmlAttributeValueContent(String expected, String string) throws Exception;

	public void testConvertToSingleQuotedXmlAttributeValue() throws Exception {
		this.verifyConvertToSingleQuotedXmlAttributeValue("''", "");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"'", "\"");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"\"'", "\"\"");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'&apos;'", "'");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'&apos;&apos;'", "''");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"&apos;\"'", "\"'\"");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"&apos;&apos;\"'", "\"''\"");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'&apos;foo&apos;'", "'foo'");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"foo\"'", "\"foo\"");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"foo\" &apos;bar&apos;'", "\"foo\" 'bar'");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'foo &amp; bar'", "foo & bar");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"foo &amp; bar\"'", "\"foo & bar\"");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'foo &lt;&lt;&lt; bar'", "foo <<< bar");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"foo &lt;&lt;&lt; bar\"'", "\"foo <<< bar\"");
	}

	protected abstract void verifyConvertToSingleQuotedXmlAttributeValue(String expected, String string) throws Exception;

	public void testConvertToSingleQuotedXmlAttributeValueContent() throws Exception {
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("", "");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"", "\"");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"\"", "\"\"");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("&apos;", "'");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("&apos;&apos;", "''");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"&apos;\"", "\"'\"");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"&apos;&apos;\"", "\"''\"");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("&apos;foo&apos;", "'foo'");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"foo\"", "\"foo\"");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"foo\" &apos;bar&apos;", "\"foo\" 'bar'");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("foo &amp; bar", "foo & bar");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"foo &amp; bar\"", "\"foo & bar\"");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("foo &lt;&lt;&lt; bar", "foo <<< bar");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"foo &lt;&lt;&lt; bar\"", "\"foo <<< bar\"");
	}

	protected abstract void verifyConvertToSingleQuotedXmlAttributeValueContent(String expected, String string) throws Exception;

	public void testConvertToXmlElementText() throws Exception {
		this.verifyConvertToXmlElementText("", "");
		this.verifyConvertToXmlElementText("\"", "\"");
		this.verifyConvertToXmlElementText("\"\"", "\"\"");
		this.verifyConvertToXmlElementText("'", "'");
		this.verifyConvertToXmlElementText("''", "''");
		this.verifyConvertToXmlElementText("\"'\"", "\"'\"");
		this.verifyConvertToXmlElementText("\"''\"", "\"''\"");
		this.verifyConvertToXmlElementText("'foo'", "'foo'");
		this.verifyConvertToXmlElementText("foo &amp; bar", "foo & bar");
		this.verifyConvertToXmlElementText("foo &amp;", "foo &");
		this.verifyConvertToXmlElementText("&amp; bar", "& bar");
		this.verifyConvertToXmlElementText("\"foo &amp; bar\"", "\"foo & bar\"");
		this.verifyConvertToXmlElementText("foo &lt;&lt;&lt; bar", "foo <<< bar");
		this.verifyConvertToXmlElementText("\"foo &lt;&lt;&lt; bar\"", "\"foo <<< bar\"");
	}

	protected abstract void verifyConvertToXmlElementText(String expected, String string) throws Exception;

	public void testConvertToXmlElementCDATA() throws Exception {
		String START = "<![CDATA[";
		String END = "]]>";
		this.verifyConvertToXmlElementCDATA(START + END, "");
		this.verifyConvertToXmlElementCDATA(START + "\"" + END, "\"");
		this.verifyConvertToXmlElementCDATA(START + "\"\"" + END, "\"\"");
		this.verifyConvertToXmlElementCDATA(START + "'" + END, "'");
		this.verifyConvertToXmlElementCDATA(START + "''" + END, "''");
		this.verifyConvertToXmlElementCDATA(START + "\"'\"" + END, "\"'\"");
		this.verifyConvertToXmlElementCDATA(START + "\"''\"" + END, "\"''\"");
		this.verifyConvertToXmlElementCDATA(START + "'foo'" + END, "'foo'");
		this.verifyConvertToXmlElementCDATA(START + "foo & bar" + END, "foo & bar");
		this.verifyConvertToXmlElementCDATA(START + "foo &" + END, "foo &");
		this.verifyConvertToXmlElementCDATA(START + "& bar" + END, "& bar");
		this.verifyConvertToXmlElementCDATA(START + "\"foo & bar\"" + END, "\"foo & bar\"");
		this.verifyConvertToXmlElementCDATA(START + "foo <<< bar" + END, "foo <<< bar");
		this.verifyConvertToXmlElementCDATA(START + "\"foo <<< bar\"" + END, "\"foo <<< bar\"");
		this.verifyConvertToXmlElementCDATA(START + "\"foo <&< bar\"" + END, "\"foo <&< bar\"");
		this.verifyConvertToXmlElementCDATA(START + "\"foo <]< bar\"" + END, "\"foo <]< bar\"");
		this.verifyConvertToXmlElementCDATA(START + "\"foo <]]< bar\"" + END, "\"foo <]]< bar\"");
		this.verifyConvertToXmlElementCDATA(START + "\"foo <]]&gt;< bar\"" + END, "\"foo <]]>< bar\"");
		this.verifyConvertToXmlElementCDATA(START + "foo <]" + END, "foo <]");
		this.verifyConvertToXmlElementCDATA(START + "foo <]]" + END, "foo <]]");
		this.verifyConvertToXmlElementCDATA(START + "foo <]]&gt;" + END, "foo <]]>");
		this.verifyConvertToXmlElementCDATA(START + "]foo" + END, "]foo");
		this.verifyConvertToXmlElementCDATA(START + "]]foo" + END, "]]foo");
		this.verifyConvertToXmlElementCDATA(START + "]]&gt;foo" + END, "]]>foo");
	}

	protected abstract void verifyConvertToXmlElementCDATA(String expected, String string) throws Exception;

	public void testConvertToXmlElementCDATAContent() throws Exception {
		this.verifyConvertToXmlElementCDATAContent("", "");
		this.verifyConvertToXmlElementCDATAContent("\"", "\"");
		this.verifyConvertToXmlElementCDATAContent("\"\"", "\"\"");
		this.verifyConvertToXmlElementCDATAContent("'", "'");
		this.verifyConvertToXmlElementCDATAContent("''", "''");
		this.verifyConvertToXmlElementCDATAContent("\"'\"", "\"'\"");
		this.verifyConvertToXmlElementCDATAContent("\"''\"", "\"''\"");
		this.verifyConvertToXmlElementCDATAContent("'foo'", "'foo'");
		this.verifyConvertToXmlElementCDATAContent("foo & bar", "foo & bar");
		this.verifyConvertToXmlElementCDATAContent("foo &", "foo &");
		this.verifyConvertToXmlElementCDATAContent("& bar", "& bar");
		this.verifyConvertToXmlElementCDATAContent("\"foo & bar\"", "\"foo & bar\"");
		this.verifyConvertToXmlElementCDATAContent("foo <<< bar", "foo <<< bar");
		this.verifyConvertToXmlElementCDATAContent("\"foo <<< bar\"", "\"foo <<< bar\"");
		this.verifyConvertToXmlElementCDATAContent("\"foo <&< bar\"", "\"foo <&< bar\"");
		this.verifyConvertToXmlElementCDATAContent("\"foo <]< bar\"", "\"foo <]< bar\"");
		this.verifyConvertToXmlElementCDATAContent("\"foo <]]< bar\"", "\"foo <]]< bar\"");
		this.verifyConvertToXmlElementCDATAContent("\"foo <]]&gt;< bar\"", "\"foo <]]>< bar\"");
		this.verifyConvertToXmlElementCDATAContent("foo <]", "foo <]");
		this.verifyConvertToXmlElementCDATAContent("foo <]]", "foo <]]");
		this.verifyConvertToXmlElementCDATAContent("foo <]]&gt;", "foo <]]>");
		this.verifyConvertToXmlElementCDATAContent("]foo", "]foo");
		this.verifyConvertToXmlElementCDATAContent("]]foo", "]]foo");
		this.verifyConvertToXmlElementCDATAContent("]]&gt;foo", "]]>foo");
	}

	protected abstract void verifyConvertToXmlElementCDATAContent(String expected, String string) throws Exception;

	// ********** constructor **********

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(this.getToolsClass());
			fail("bogus: " + at); //$NON-NLS-1$
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof InvocationTargetException) {
				if (ex.getCause().getCause() instanceof UnsupportedOperationException) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}

	protected String getSimpleToolsClassName() {
		return this.getToolsClass().getSimpleName();
	}

	protected abstract Class<?> getToolsClass();
}
