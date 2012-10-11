/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.util.Arrays;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ByteArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;

@SuppressWarnings("nls")
public class StringToolsTests
	extends TestCase
{
	public StringToolsTests(String name) {
		super(name);
	}

	// ********** padding/truncating/centering **********

	public void testCenter() {
		assertEquals("fred", StringTools.center("fred", 4));
		assertEquals(" fred ", StringTools.center("fred", 6));
		assertEquals(" fred  ", StringTools.center("fred", 7));
		assertEquals("re", StringTools.center("fred", 2));
		assertEquals("fre", StringTools.center("fred", 3));
	}

	public void testPad() {
		assertEquals("fred", StringTools.pad("fred", 4));
		assertEquals("fred  ", StringTools.pad("fred", 6));
		boolean exThrown = false;
		try {
			assertEquals("fr", StringTools.pad("fred", 2));
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testFit() {
		assertEquals("fred", StringTools.fit("fred", 4));
		assertEquals("fred  ", StringTools.fit("fred", 6));
		assertEquals("fr", StringTools.fit("fred", 2));
	}

	public void testZeroPad() {
		assertEquals("1234", StringTools.zeroPad("1234", 4));
		assertEquals("001234", StringTools.zeroPad("1234", 6));
		boolean exThrown = false;
		try {
			assertEquals("12", StringTools.zeroPad("1234", 2));
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testZeroFit() {
		assertEquals("1234", StringTools.zeroFit("1234", 4));
		assertEquals("001234", StringTools.zeroFit("1234", 6));
		assertEquals("34", StringTools.zeroFit("1234", 2));
	}

	// ********** separating **********

	public void testSeparateCharInt() {
		this.verifySeparate("012345", '-', 22, "012345");
		this.verifySeparate("012345", '-',  6, "012345");
		this.verifySeparate("012345", '-',  5, "01234-5");
		this.verifySeparate("012345", '-',  4, "0123-45");
		this.verifySeparate("012345", '-',  3, "012-345");
		this.verifySeparate("012345", '-',  2, "01-23-45");
		this.verifySeparate("012345", '-',  1, "0-1-2-3-4-5");
	}

	private void verifySeparate(String string, char separator, int segmentLength, String expected) {
		assertEquals(expected, StringTools.separate(string, separator, segmentLength));
	}

	// ********** delimiting **********

	public void testDelimit() {
		this.verifyDelimit("Employee", "123", "123Employee123");
		this.verifyDelimit("123", "123", "123123123");
		this.verifyDelimit("", "123", "123123");
	}

	private void verifyDelimit(String string, String delimiter, String expectedString) {
		assertEquals(expectedString, StringTools.delimit(string, delimiter));
	}

	public void testQuote() {
		this.verifyQuote("Employee", "\"Employee\"");
		this.verifyQuote("123", "\"123\"");
		this.verifyQuote("", "\"\"");
		this.verifyQuote("Emp\"loyee", "\"Emp\"\"loyee\"");
	}

	private void verifyQuote(String string, String expectedString) {
		assertEquals(expectedString, StringTools.quote(string));
	}

	// ********** removing characters **********

	public void testRemoveFirstOccurrence() {
		this.verifyRemoveFirstOccurrence("Emplo&yee", '&', "Employee");
		this.verifyRemoveFirstOccurrence("Emplo&yee&", '&', "Employee&");
		this.verifyRemoveFirstOccurrence("Employee &Foo", '&', "Employee Foo");
		this.verifyRemoveFirstOccurrence("Employee&", '&', "Employee");
		this.verifyRemoveFirstOccurrence("&Employee", '&', "Employee");
	}

	private void verifyRemoveFirstOccurrence(String string, char charToRemove, String expectedString) {
		assertEquals(expectedString, StringTools.removeFirstOccurrence(string, charToRemove));
	}

	public void testRemoveAllOccurrences() {
		this.verifyRemoveAllOccurrences("Employee Fred", ' ', "EmployeeFred");
		this.verifyRemoveAllOccurrences(" Employee ", ' ', "Employee");
		this.verifyRemoveAllOccurrences("Employee   Foo", ' ', "EmployeeFoo");
		this.verifyRemoveAllOccurrences(" Emp loyee   Foo", ' ', "EmployeeFoo");
	}

	private void verifyRemoveAllOccurrences(String string, char charToRemove, String expectedString) {
		assertEquals(expectedString, StringTools.removeAllOccurrences(string, charToRemove));
	}

	public void testRemoveAllWhitespace() {
		this.verifyRemoveAllWhitespace("Employee Fred\t", "EmployeeFred");
		this.verifyRemoveAllWhitespace("\tEmployee\n", "Employee");
		this.verifyRemoveAllWhitespace("Employee \t Foo", "EmployeeFoo");
		this.verifyRemoveAllWhitespace(" Emp\tloyee \n Foo", "EmployeeFoo");
	}

	private void verifyRemoveAllWhitespace(String string, String expectedString) {
		assertEquals(expectedString, StringTools.removeAllWhitespace(string));
	}

	public void testCompressWhitespace() {
		this.verifyCompressWhitespace("Employee      Fred\t", "Employee Fred ");
		this.verifyCompressWhitespace("\tEmployee  \n", " Employee ");
		this.verifyCompressWhitespace("Employee \t Foo", "Employee Foo");
		this.verifyCompressWhitespace(" Emp\tloyee \n Foo ", " Emp loyee Foo ");
	}

	private void verifyCompressWhitespace(String string, String expectedString) {
		assertEquals(expectedString, StringTools.compressWhitespace(string));
	}

	// ********** common prefix **********

	public void testCommonPrefixLength() {
		assertEquals(3, StringTools.commonPrefixLength("fooZZZ", "fooBBB"));
		assertEquals(3, StringTools.commonPrefixLength("foo", "fooBBB"));
		assertEquals(3, StringTools.commonPrefixLength("fooZZZ", "foo"));
		assertEquals(3, StringTools.commonPrefixLength("foo", "foo"));
	}

	public void testCommonPrefixLengthMax() {
		assertEquals(2, StringTools.commonPrefixLength("fooZZZ", "fooBBB", 2));
		assertEquals(2, StringTools.commonPrefixLength("foo", "fooBBB", 2));
		assertEquals(2, StringTools.commonPrefixLength("fooZZZ", "foo", 2));
		assertEquals(2, StringTools.commonPrefixLength("foo", "foo", 2));
	}

	// ********** capitalization **********

	public void testCapitalize() {
		this.verifyCapitalize("Oracle", "Oracle");
		this.verifyCapitalize("Oracle", "oracle");
		this.verifyCapitalize("   ", "   ");
		this.verifyCapitalize("ORACLE", "ORACLE");
		this.verifyCapitalize("", "");
		this.verifyCapitalize("A", "a");
		this.verifyCapitalize("\u00C9cole", "\u00E9cole"); // ï¿½cole->ï¿½COLE
	}

	private void verifyCapitalize(String expected, String string) {
		assertEquals(expected, StringTools.capitalize(string));
	}

	public void testUncapitalize() {
		this.verifyUncapitalize("oracle", "Oracle");
		this.verifyUncapitalize("oracle", "oracle");
		this.verifyUncapitalize("   ", "   ");
		this.verifyUncapitalize("ORACLE", "ORACLE");
		this.verifyUncapitalize("", "");
		this.verifyUncapitalize("a", "A");
		this.verifyUncapitalize("\u00E9cole", "\u00C9cole"); // ï¿½cole->ï¿½COLE
	}

	private void verifyUncapitalize(String expected, String string) {
		assertEquals(expected, StringTools.uncapitalize(string));
	}

	// ********** queries **********

	public void testIsBlank() {
		assertTrue(StringTools.isBlank((String) null));
		assertTrue(StringTools.isBlank(""));
		assertTrue(StringTools.isBlank("      "));
		assertTrue(StringTools.isBlank("      \t\t   "));
		assertTrue(StringTools.isBlank("      \t\t   " + StringTools.CR));
	}

	public void testEquals() {
		assertTrue(ObjectTools.equals((String) null, (String) null));
		assertFalse(ObjectTools.equals(null, "asdf"));
		assertFalse(ObjectTools.equals("asdf", null));
		assertTrue(ObjectTools.equals("asdf", "asdf"));
		assertFalse(ObjectTools.equals("asdf", "ASDF"));
	}

	public void testEqualsIgnoreCase() {
		assertTrue(StringTools.equalsIgnoreCase((String) null, (String) null));
		assertFalse(StringTools.equalsIgnoreCase(null, "asdf"));
		assertFalse(StringTools.equalsIgnoreCase("asdf", null));
		assertTrue(StringTools.equalsIgnoreCase("asdf", "asdf"));
		assertTrue(StringTools.equalsIgnoreCase("asdf", "ASDF"));
	}

	public void testStartsWithIgnoreCase() {
		assertTrue(StringTools.startsWithIgnoreCase("asdf", "as"));
		assertTrue(StringTools.startsWithIgnoreCase("asdf", "aS"));
		assertTrue(StringTools.startsWithIgnoreCase("asdf", ""));
		assertTrue(StringTools.startsWithIgnoreCase("asdf", "A"));

		assertFalse(StringTools.startsWithIgnoreCase("asdf", "bsdf"));
		assertFalse(StringTools.startsWithIgnoreCase("asdf", "g"));
		assertFalse(StringTools.startsWithIgnoreCase("asdf", "asdg"));
		assertFalse(StringTools.startsWithIgnoreCase("asdf", "asdfg"));
		assertFalse(StringTools.startsWithIgnoreCase("asdf", "asdfgggggg"));
	}

	public void testIsUppercase() {
		this.verifyIsUppercase("FOO");
		this.verifyIsUppercase("FOO2");
		this.verifyIsUppercase("F O O");
		this.denyIsUppercase("Foo");
		this.denyIsUppercase("");
	}

	private void verifyIsUppercase(String s) {
		assertTrue(StringTools.isUppercase(s));
	}

	private void denyIsUppercase(String s) {
		assertFalse(StringTools.isUppercase(s));
	}

	public void testIsLowercase() {
		this.verifyIsLowercase("foo");
		this.verifyIsLowercase("foo2");
		this.verifyIsLowercase("f o o");
		this.denyIsLowercase("Foo");
		this.denyIsLowercase("");
	}

	private void verifyIsLowercase(String s) {
		assertTrue(StringTools.isLowercase(s));
	}

	private void denyIsLowercase(String s) {
		assertFalse(StringTools.isLowercase(s));
	}

	// ********** byte arrays **********

	public void testConvertHexStringToByteArray_empty() throws Exception {
		String s = StringTools.EMPTY_STRING;
		byte[] byteArray = StringTools.convertHexStringToByteArray(s);
		assertEquals(0, byteArray.length);
		assertTrue(Arrays.equals(ByteArrayTools.EMPTY_BYTE_ARRAY, byteArray));
	}

	public void testConvertHexStringToByteArray_oddLength() throws Exception {
		String s = "CAFEE";
		boolean exCaught = false;
		try {
			byte[] byteArray = StringTools.convertHexStringToByteArray(s);
			fail("bogus byte array: " + Arrays.toString(byteArray));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConvertHexStringToByteArray_illegalCharacter1() throws Exception {
		this.verifyConvertHexStringToByteArray_illegalCharacter("CAFEX0CAFEX0");
	}

	public void testConvertHexStringToByteArray_illegalCharacter2() throws Exception {
		this.verifyConvertHexStringToByteArray_illegalCharacter("CAFE0XCAFE0x");
	}

	private void verifyConvertHexStringToByteArray_illegalCharacter(String s) throws Exception {
		boolean exCaught = false;
		try {
			byte[] byteArray = StringTools.convertHexStringToByteArray(s);
			fail("bogus byte array: " + Arrays.toString(byteArray));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConvertHexStringToByteArray_ok() throws Exception {
		String s = "74657374"; // Unicode values
		assertEquals("test", new String(StringTools.convertHexStringToByteArray(s)));
	}

	public void testConvertHexStringToByteArray_negative() throws Exception {
		String s = "636166E9"; // Unicode values
		assertEquals("café", new String(StringTools.convertHexStringToByteArray(s)));
	}

	public void testConvertHexStringToByteArray_lowercase() throws Exception {
		String s = "636166e9"; // Unicode values
		assertEquals("café", new String(StringTools.convertHexStringToByteArray(s)));
	}

	// ********** convert camel-case to all-caps **********

	public void testConvertCamelCaseToAllCaps() {
		assertEquals("TEST", StringTools.convertCamelCaseToAllCaps("test"));
		assertEquals("TEST", StringTools.convertCamelCaseToAllCaps("TEST"));
		assertEquals("TEST_TEST", StringTools.convertCamelCaseToAllCaps("testTest"));
		assertEquals("TEST_TEST", StringTools.convertCamelCaseToAllCaps("TestTest"));
		assertEquals("TEST_TEST_TEST", StringTools.convertCamelCaseToAllCaps("testTESTTest"));
		assertEquals("TEST_TEST_TEST", StringTools.convertCamelCaseToAllCaps("TestTESTTest"));
		assertEquals("TEST_TEST_TEST_T", StringTools.convertCamelCaseToAllCaps("TestTESTTestT"));
	}

	public void testConvertCamelCaseToAllCapsMaxLength() {
		assertEquals("TEST", StringTools.convertCamelCaseToAllCaps("test", 44));
		assertEquals("TEST", StringTools.convertCamelCaseToAllCaps("test", 4));
		assertEquals("TES", StringTools.convertCamelCaseToAllCaps("test", 3));
		assertEquals("TEST", StringTools.convertCamelCaseToAllCaps("TEST", 5));
		assertEquals("TE", StringTools.convertCamelCaseToAllCaps("TEST", 2));
		assertEquals("TEST_TEST", StringTools.convertCamelCaseToAllCaps("testTest", 9));
		assertEquals("TEST_TES", StringTools.convertCamelCaseToAllCaps("testTest", 8));
		assertEquals("TEST_T", StringTools.convertCamelCaseToAllCaps("testTest", 6));
		assertEquals("TEST_", StringTools.convertCamelCaseToAllCaps("testTest", 5));
		assertEquals("TEST", StringTools.convertCamelCaseToAllCaps("testTest", 4));
		assertEquals("TEST_TEST", StringTools.convertCamelCaseToAllCaps("TestTest", 9));
		assertEquals("TEST_TEST", StringTools.convertCamelCaseToAllCaps("TestTest", 1100));
		assertEquals("TEST_TEST_", StringTools.convertCamelCaseToAllCaps("testTESTTest", 10));
		assertEquals("TEST_TEST_TEST", StringTools.convertCamelCaseToAllCaps("TestTESTTest", 14));
		assertEquals("TEST_TEST_TEST_T", StringTools.convertCamelCaseToAllCaps("TestTESTTestT", 16));
		assertEquals("TEST_TEST_TEST_", StringTools.convertCamelCaseToAllCaps("TestTESTTestT", 15));
	}

	// ********** convert all-caps to camel case **********

	public void testConvertAllCapsToCamelCase() {
		assertEquals("test", StringTools.convertAllCapsToCamelCase("TEST", false));
		assertEquals("test", StringTools.convertAllCapsToCamelCase("TEST_", false));
		assertEquals("test", StringTools.convertAllCapsToCamelCase("TEST____", false));
		assertEquals("Test", StringTools.convertAllCapsToCamelCase("TEST", true));
		assertEquals("test", StringTools.convertAllCapsToCamelCase("TeST", false));
		assertEquals("testTest", StringTools.convertAllCapsToCamelCase("TEST_TEST", false));
		assertEquals("testTest", StringTools.convertAllCapsToCamelCase("TEST___TEST", false));
		assertEquals("TestTest", StringTools.convertAllCapsToCamelCase("TEST_TEST", true));
		assertEquals("testTestTest", StringTools.convertAllCapsToCamelCase("TEST_TEST_TEST", false));
		assertEquals("TestTestTest", StringTools.convertAllCapsToCamelCase("TEST_TEST_TEST", true));
		assertEquals("testTestTestT", StringTools.convertAllCapsToCamelCase("TEST_TEST_TEST_T", false));
		assertEquals("testTestTestT", StringTools.convertAllCapsToCamelCase("_TEST_TEST_TEST_T", false));
		assertEquals("testTestTestT", StringTools.convertAllCapsToCamelCase("__TEST_TEST_TEST_T", false));
		assertEquals("TestTestTestT", StringTools.convertAllCapsToCamelCase("TEST_TEST_TEST_T", true));
		assertEquals("TestTestTestT", StringTools.convertAllCapsToCamelCase("_TEST_TEST_TEST_T", true));
		assertEquals("TestTestTestT", StringTools.convertAllCapsToCamelCase("__TEST_TEST_TEST_T", true));
	}

	public void testConvertAllCapsToCamelCaseLowercase() {
		assertEquals("test", StringTools.convertAllCapsToCamelCase("test", false));
		assertEquals("test", StringTools.convertAllCapsToCamelCase("test_", false));
		assertEquals("test", StringTools.convertAllCapsToCamelCase("test____", false));
		assertEquals("Test", StringTools.convertAllCapsToCamelCase("test", true));
		assertEquals("test", StringTools.convertAllCapsToCamelCase("test", false));
		assertEquals("testTest", StringTools.convertAllCapsToCamelCase("test_test", false));
		assertEquals("testTest", StringTools.convertAllCapsToCamelCase("test___test", false));
		assertEquals("TestTest", StringTools.convertAllCapsToCamelCase("test_test", true));
		assertEquals("testTestTest", StringTools.convertAllCapsToCamelCase("test_test_test", false));
		assertEquals("TestTestTest", StringTools.convertAllCapsToCamelCase("test_test_test", true));
		assertEquals("testTestTestT", StringTools.convertAllCapsToCamelCase("test_test_test_t", false));
		assertEquals("testTestTestT", StringTools.convertAllCapsToCamelCase("_test_test_test_t", false));
		assertEquals("testTestTestT", StringTools.convertAllCapsToCamelCase("__test_test_test_t", false));
		assertEquals("TestTestTestT", StringTools.convertAllCapsToCamelCase("test_test_test_t", true));
		assertEquals("TestTestTestT", StringTools.convertAllCapsToCamelCase("_test_test_test_t", true));
		assertEquals("TestTestTestT", StringTools.convertAllCapsToCamelCase("__test_test_test_t", true));
	}

	// ********** delimiting **********

	public void testIsQuoted() {
		this.denyIsQuoted("foo");
		this.verifyIsQuoted("\"foo\"");

		this.denyIsQuoted("");
		this.verifyIsQuoted("\"\"");

		this.denyIsQuoted("\"");
		this.denyIsQuoted(" ");
		this.denyIsQuoted("''");
		this.denyIsQuoted("'foo'");
	}

	private void verifyIsQuoted(String s) {
		assertTrue(StringTools.isQuoted(s));
	}

	private void denyIsQuoted(String s) {
		assertFalse(StringTools.isQuoted(s));
	}

	public void testIsParenthetical() {
		this.denyIsParenthetical("foo");
		this.verifyIsParenthetical("(foo)");

		this.denyIsParenthetical("");
		this.verifyIsParenthetical("()");

		this.denyIsParenthetical("(");
		this.denyIsParenthetical(" ");
		this.denyIsParenthetical("''");
		this.denyIsParenthetical("'foo'");
	}

	private void verifyIsParenthetical(String s) {
		assertTrue(StringTools.isParenthetical(s));
	}

	private void denyIsParenthetical(String s) {
		assertFalse(StringTools.isParenthetical(s));
	}

	public void testIsBracketed() {
		this.denyIsBracketed("foo");
		this.verifyIsBracketed("[foo]");

		this.denyIsBracketed("");
		this.verifyIsBracketed("[]");

		this.denyIsBracketed("[");
		this.denyIsBracketed(" ");
		this.denyIsBracketed("''");
		this.denyIsBracketed("'foo'");
	}

	private void verifyIsBracketed(String s) {
		assertTrue(StringTools.isBracketed(s));
	}

	private void denyIsBracketed(String s) {
		assertFalse(StringTools.isBracketed(s));
	}

	public void testIsBraced() {
		this.denyIsBraced("foo");
		this.verifyIsBraced("{foo}");

		this.denyIsBraced("");
		this.verifyIsBraced("{}");

		this.denyIsBraced("{");
		this.denyIsBraced(" ");
		this.denyIsBraced("''");
		this.denyIsBraced("'foo'");
	}

	private void verifyIsBraced(String s) {
		assertTrue(StringTools.isBraced(s));
	}

	private void denyIsBraced(String s) {
		assertFalse(StringTools.isBraced(s));
	}

	public void testIsChevroned() {
		this.denyIsChevroned("foo");
		this.verifyIsChevroned("<foo>");

		this.denyIsChevroned("");
		this.verifyIsChevroned("<>");

		this.denyIsChevroned("{");
		this.denyIsChevroned(" ");
		this.denyIsChevroned("''");
		this.denyIsChevroned("'foo'");
	}

	private void verifyIsChevroned(String s) {
		assertTrue(StringTools.isChevroned(s));
	}

	private void denyIsChevroned(String s) {
		assertFalse(StringTools.isChevroned(s));
	}

	public void testIsDelimited() {
		this.denyIsDelimited("foo", '?');
		this.verifyIsDelimited("?foo?", '?');

		this.denyIsDelimited("", '?');
		this.verifyIsDelimited("\"\"", '"');
		this.verifyIsDelimited("?xx?", '?');
		this.denyIsDelimited("?xx]", '?');

		this.denyIsDelimited("\"", '"');
		this.denyIsDelimited(" ", ' ');
		this.denyIsDelimited("''", '"');
		this.denyIsDelimited("'foo'", '?');
	}

	private void verifyIsDelimited(String s, char c) {
		assertTrue(StringTools.isDelimited(s, c));
	}

	private void denyIsDelimited(String s, char c) {
		assertFalse(StringTools.isDelimited(s, c));
	}

	public void testIsDelimited2() {
		this.denyIsDelimited2("foo", '[', ']');
		this.verifyIsDelimited2("{foo}", '{', '}');

		this.denyIsDelimited2("", '[', ']');
		this.verifyIsDelimited2("[]", '[', ']');
		this.verifyIsDelimited2("[xx]", '[', ']');
		this.denyIsDelimited2("?xx]", '[', ']');

		this.denyIsDelimited2("\"", '[', ']');
		this.denyIsDelimited2(" ", '[', ']');
		this.denyIsDelimited2("''", '[', ']');
		this.denyIsDelimited2("'foo'", '[', ']');
	}

	private void verifyIsDelimited2(String s, char start, char end) {
		assertTrue(StringTools.isDelimited(s, start, end));
	}

	private void denyIsDelimited2(String s, char start, char end) {
		assertFalse(StringTools.isDelimited(s, start, end));
	}

	// ********** undelimiting **********

	public void testUndelimit() {
		this.verifyUndelimit("\"foo\"", "foo");
		this.verifyUndelimit("\"\"", "");
		this.verifyUndelimit("'foo'", "foo");
		this.verifyUndelimit("\"fo\"\"o\"", "fo\"o");
		this.verifyUndelimit("\"foo\"\"\"", "foo\"");
		this.verifyUndelimit("\"\"\"foo\"", "\"foo");
		this.verifyUndelimit("[foo]", "foo");
		this.verifyUndelimit("\"\"\"", "\"");
		this.verifyUndelimit("\"foo\"bar\"", "foo\"");
		this.verifyUndelimit("\"foo\"\"", "foo\"");
	}

	private void verifyUndelimit(String s, String expected) {
		assertEquals(expected, StringTools.undelimit(s));
	}

	public void testUndelimitInt() {
		this.verifyUndelimitInt("\"foo\"", 2, "o");
		this.verifyUndelimitInt("\"\"foo\"\"", 2, "foo");
		this.verifyUndelimitInt("'foo'", 2, "o");
	}

	private void verifyUndelimitInt(String s, int count, String expected) {
		assertEquals(expected, StringTools.undelimit(s, count));
	}

	public void testUndelimitIntException() {
		this.denyUndelimitInt("\"\"", 2);
		this.denyUndelimitInt("'o'", 2);
	}

	private void denyUndelimitInt(String s, int count) {
		boolean exCaught = false;
		try {
			String bogus = StringTools.undelimit(s, count);
			fail("invalid string: " + bogus);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	// ********** converting to Java string literal **********

	public void testConvertToJavaStringLiteral() {
		this.verifyConvertToJavaStringLiteral("", "\"\"");
		this.verifyConvertToJavaStringLiteral("\"\"", "\"\\\"\\\"\"");
		this.verifyConvertToJavaStringLiteral("'foo'", "\"'foo'\"");
		this.verifyConvertToJavaStringLiteral("foo\bbar", "\"foo\\bbar\"");
		this.verifyConvertToJavaStringLiteral("foo\n\tbar", "\"foo\\n\\tbar\"");
		this.verifyConvertToJavaStringLiteral("foo\"bar", "\"foo\\\"bar\"");
		this.verifyConvertToJavaStringLiteral("foo\\bar", "\"foo\\\\bar\"");
	}

	private void verifyConvertToJavaStringLiteral(String s, String expected) {
		assertEquals(expected, StringTools.convertToJavaStringLiteral(s));
	}

	public void testConvertToJavaStringLiteralContent() {
		this.verifyConvertToJavaStringLiteralContent("", "");
		this.verifyConvertToJavaStringLiteralContent("\"\"", "\\\"\\\"");
		this.verifyConvertToJavaStringLiteralContent("'foo'", "'foo'");
		this.verifyConvertToJavaStringLiteralContent("foo\bbar", "foo\\bbar");
		this.verifyConvertToJavaStringLiteralContent("foo\n\tbar", "foo\\n\\tbar");
		this.verifyConvertToJavaStringLiteralContent("foo\"bar", "foo\\\"bar");
		this.verifyConvertToJavaStringLiteralContent("foo\\bar", "foo\\\\bar");
	}

	private void verifyConvertToJavaStringLiteralContent(String s, String expected) {
		assertEquals(expected, StringTools.convertToJavaStringLiteralContent(s));
	}

	// ********** converting to XML **********

	public void testConvertToXmlAttributeValue() {
		this.verifyConvertToXmlAttributeValue("", "\"\"");
		this.verifyConvertToXmlAttributeValue("\"", "'\"'");
		this.verifyConvertToXmlAttributeValue("\"\"", "'\"\"'");
		this.verifyConvertToXmlAttributeValue("'", "\"'\"");
		this.verifyConvertToXmlAttributeValue("''", "\"''\"");
		this.verifyConvertToXmlAttributeValue("\"'\"", "\"&quot;'&quot;\"");
		this.verifyConvertToXmlAttributeValue("\"''\"", "\"&quot;''&quot;\"");
		this.verifyConvertToXmlAttributeValue("'foo'", "\"'foo'\"");
		this.verifyConvertToXmlAttributeValue("\"foo\"", "'\"foo\"'");
		this.verifyConvertToXmlAttributeValue("\"foo\" 'bar'", "\"&quot;foo&quot; 'bar'\"");
		this.verifyConvertToXmlAttributeValue("foo & bar", "\"foo &amp; bar\"");
		this.verifyConvertToXmlAttributeValue("\"foo & bar\"", "'\"foo &amp; bar\"'");
		this.verifyConvertToXmlAttributeValue("foo <<< bar", "\"foo &lt;&lt;&lt; bar\"");
		this.verifyConvertToXmlAttributeValue("\"foo <<< bar\"", "'\"foo &lt;&lt;&lt; bar\"'");
	}

	private void verifyConvertToXmlAttributeValue(String s, String expected) {
		assertEquals(expected, StringTools.convertToXmlAttributeValue(s));
	}

	public void testConvertToXmlElementText() {
		this.verifyConvertToXmlElementText("", "");
		this.verifyConvertToXmlElementText("\"", "\"");
		this.verifyConvertToXmlElementText("\"\"", "\"\"");
		this.verifyConvertToXmlElementText("'", "'");
		this.verifyConvertToXmlElementText("''", "''");
		this.verifyConvertToXmlElementText("\"'\"", "\"'\"");
		this.verifyConvertToXmlElementText("\"''\"", "\"''\"");
		this.verifyConvertToXmlElementText("'foo'", "'foo'");
		this.verifyConvertToXmlElementText("foo & bar", "foo &amp; bar");
		this.verifyConvertToXmlElementText("foo &", "foo &amp;");
		this.verifyConvertToXmlElementText("& bar", "&amp; bar");
		this.verifyConvertToXmlElementText("\"foo & bar\"", "\"foo &amp; bar\"");
		this.verifyConvertToXmlElementText("foo <<< bar", "foo &lt;&lt;&lt; bar");
		this.verifyConvertToXmlElementText("\"foo <<< bar\"", "\"foo &lt;&lt;&lt; bar\"");
	}

	private void verifyConvertToXmlElementText(String s, String expected) {
		assertEquals(expected, StringTools.convertToXmlElementText(s));
	}

	public void testConvertToXmlElementCDATA() {
		String START = "<![CDATA[";
		String END = "]]>";
		this.verifyConvertToXmlElementCDATA("", START + END);
		this.verifyConvertToXmlElementCDATA("\"", START + "\"" + END);
		this.verifyConvertToXmlElementCDATA("\"\"", START + "\"\"" + END);
		this.verifyConvertToXmlElementCDATA("'", START + "'" + END);
		this.verifyConvertToXmlElementCDATA("''", START + "''" + END);
		this.verifyConvertToXmlElementCDATA("\"'\"", START + "\"'\"" + END);
		this.verifyConvertToXmlElementCDATA("\"''\"", START + "\"''\"" + END);
		this.verifyConvertToXmlElementCDATA("'foo'", START + "'foo'" + END);
		this.verifyConvertToXmlElementCDATA("foo & bar", START + "foo & bar" + END);
		this.verifyConvertToXmlElementCDATA("foo &", START + "foo &" + END);
		this.verifyConvertToXmlElementCDATA("& bar", START + "& bar" + END);
		this.verifyConvertToXmlElementCDATA("\"foo & bar\"", START + "\"foo & bar\"" + END);
		this.verifyConvertToXmlElementCDATA("foo <<< bar", START + "foo <<< bar" + END);
		this.verifyConvertToXmlElementCDATA("\"foo <<< bar\"", START + "\"foo <<< bar\"" + END);
		this.verifyConvertToXmlElementCDATA("\"foo <&< bar\"", START + "\"foo <&< bar\"" + END);
		this.verifyConvertToXmlElementCDATA("\"foo <]< bar\"", START + "\"foo <]< bar\"" + END);
		this.verifyConvertToXmlElementCDATA("\"foo <]]< bar\"", START + "\"foo <]]< bar\"" + END);
		this.verifyConvertToXmlElementCDATA("\"foo <]]>< bar\"", START + "\"foo <]]&gt;< bar\"" + END);
		this.verifyConvertToXmlElementCDATA("foo <]", START + "foo <]" + END);
		this.verifyConvertToXmlElementCDATA("foo <]]", START + "foo <]]" + END);
		this.verifyConvertToXmlElementCDATA("foo <]]>", START + "foo <]]&gt;" + END);
		this.verifyConvertToXmlElementCDATA("]foo", START + "]foo" + END);
		this.verifyConvertToXmlElementCDATA("]]foo", START + "]]foo" + END);
		this.verifyConvertToXmlElementCDATA("]]>foo", START + "]]&gt;foo" + END);
	}

	private void verifyConvertToXmlElementCDATA(String s, String expected) {
		assertEquals(expected, StringTools.convertToXmlElementCDATA(s));
	}
}
